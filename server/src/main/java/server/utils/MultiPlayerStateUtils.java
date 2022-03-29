package server.utils;

import commons.misc.GameResponse;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.multi.Reaction;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;

import javax.swing.plaf.IconUIResource;
import java.util.*;

/**
 * Utility class providing functionality for the multiplayer game mode.
 */
public class MultiPlayerStateUtils {

    private final Map<Long, MultiPlayerState> games;
    private MultiPlayerState nextGame;

    private final GenerateQuestionUtils generateQuestionUtils;
    private final QueueUtils queueUtils;
    private final CurrentTimeUtils currentTime;

    /**
     * Constructor for multiplayer server-side utility class.
     *
     * @param generateQuestionUtils is the "generate questions" utility bean injected by Spring.
     * @param queueUtils            is the class responsible for managing the queue.
     * @param currentTime           CurrentTimeUtils instance for getting the current time.
     */
    public MultiPlayerStateUtils(GenerateQuestionUtils generateQuestionUtils,
                                 QueueUtils queueUtils,
                                 CurrentTimeUtils currentTime
    ) {
        this.generateQuestionUtils = generateQuestionUtils;
        this.queueUtils = queueUtils;
        this.currentTime = currentTime;

        this.games = new HashMap<>();

        this.initialize();
    }

    /**
     * Initialization method, called in the constructor.
     */
    protected void initialize() {
        nextGame = createNextGame();

        queueUtils.setOnStart(this::startNewGame);
    }

    /**
     * Getter for the multiplayer game state.
     * <p>
     * Internally updates the state to be up-to-date.
     *
     * @param id Id of the multiplayer game.
     * @return Multiplayer game state with that id.
     */
    public MultiPlayerState getGameState(long id) {
        if (games.containsKey(id)) {
            MultiPlayerState game = games.get(id);
            updateState(game);
            return game;
        } else {
            return null;
        }
    }

    /**
     * Add a player to a multiplayer game.
     * <p>
     * Verifies that the player is valid and can be added to the game, and
     * then adds the player.
     *
     * @param id     Id of the multiplayer game.
     * @param player MultiPlayer to add.
     * @return Player that was added iff they were added.
     */
    public MultiPlayer addPlayer(long id, MultiPlayer player) {
        MultiPlayerState game = getGameState(id);
        if (game == null) {
            return null;
        } else if (player == null) {
            return null;
        } else if (player.getUsername() == null || player.getUsername().isEmpty()) {
            return null;
        } else if (containsPlayer(player, game)) {
            return null;
        } else {
            game.getPlayers().add(player);
            return player;
        }
    }

    /**
     * Check if the given game contains the given player.
     *
     * @param player Player to be checked for uniqueness.
     * @param game   Game to be checked in.
     * @return True iff the given game contains the given player.
     */
    public boolean containsPlayer(MultiPlayer player, MultiPlayerState game) {
        return game.getPlayers().stream().anyMatch(player1 -> player.getUsername().equals(player1.getUsername()));
    }

    /**
     * Check and update (if needed) the state of the game.
     *
     * @param game Multiplayer game whose state is to be checked and updated.
     */
    public void updateState(MultiPlayerState game) {
        if (checkIfUpdate(game)) {
            switchState(game);
        }
    }

    /**
     * Check if state of the game has to be updated.
     *
     * @param game Game to be checked
     * @return true iff current time is beyond time of update for the state of the game.
     */
    public boolean checkIfUpdate(MultiPlayerState game) {
        return game.getNextPhase() <= currentTime.getTime();
    }

    /**
     * Switch the state of the game to the next appropriate state.
     * <p>
     * Beware of calling this method if the state should not be updated yet.
     * It will prematurely switch the state of the game, and leave the next phase longer.
     *
     * @param game Game whose state is to be switched to the next one.
     */
    public void switchState(MultiPlayerState game) {
        // Only update state if needed
        String state = game.getState();
        if (MultiPlayerState.STARTING_STATE.equals(state)) {
            // We should definitely switch to a question phase.
            switchToQuestion(game);
        } else if (MultiPlayerState.QUESTION_STATE.equals(state)) {
            // Always show the correct answer after a question
            switchToTransition(game);
        } else if (MultiPlayerState.TRANSITION_STATE.equals(state)) {
            if (game.getRoundNumber() >= 19) {
                switchToGameOver(game);
            } else if (game.getRoundNumber() < 19 & game.getRoundNumber() % 5 == 4) {
                // Show leaderboard every 5 rounds:
                // After rounds 4, 9 and 14
                switchToLeaderboard(game);
            } else {
                switchToQuestion(game);
            }
        } else if (MultiPlayerState.LEADERBOARD_STATE.equals(state)) {
            // Always switch to question after leaderboard
            switchToQuestion(game);
        }
        // We do not switch in other cases
    }

    /**
     * Start the next question round of the game.
     * <p>
     * This is one of the state transitions of the game:
     * - STARTING -> QUESTION
     * - TRANSITION -> QUESTION
     * - LEADERBOARD -> QUESTION
     * <p>
     * The next state after calling this method is guaranteed to be QUESTION.
     * <p>
     * The question phase takes 8 seconds.
     *
     * @param game Game whose state is updated to QUESTION.
     */
    public void switchToQuestion(MultiPlayerState game) {
        int currentRound = game.getRoundNumber();
        long nextPhase = game.getNextPhase();

        game.setRoundNumber(currentRound + 1);
        game.setState(MultiPlayerState.QUESTION_STATE);
        // 8 seconds for questions
        game.setNextPhase(nextPhase + 8000);
    }

    /**
     * Show the intermittent leaderboard.
     * <p>
     * This is one of the state transitions of the game:
     * - TRANSITION -> LEADERBOARD
     * <p>
     * The next state after calling this method is guaranteed to be LEADERBOARD.
     * <p>
     * The leaderboard phase takes 5 seconds.
     *
     * @param game Game whose state is updated to LEADERBOARD.
     */
    public void switchToLeaderboard(MultiPlayerState game) {
        long nextPhase = game.getNextPhase();

        game.setState(MultiPlayerState.LEADERBOARD_STATE);
        // 5 seconds for leaderboard screen between rounds
        game.setNextPhase(nextPhase + 5000);
    }

    /**
     * Show the game over screen, and stop the game.
     * <p>
     * This is one of the state transitions of the game:
     * - TRANSITION -> GAME_OVER
     * <p>
     * The next state after calling this method is guaranteed to be GAME_OVER.
     * <p>
     * The game over phase is indefinite, and will never switch to anything else.
     *
     * @param game Game whose state is updated to GAME_OVER.
     */
    public void switchToGameOver(MultiPlayerState game) {
        game.setState(MultiPlayerState.GAME_OVER_STATE);
        // Make sure the game does not progress anymore.
        game.setNextPhase(Long.MAX_VALUE);
    }

    /**
     * Update scores and show transition screen.
     * <p>
     * Scores are also updated for all players.
     * <p>
     * This is one of the state transitions of the game:
     * - QUESTION -> TRANSITION
     * <p>
     * The next state after calling this method is guaranteed to be TRANSITION.
     * <p>
     * The transition phase takes 3 seconds.
     *
     * @param game Game whose state is updated to TRANSITION.
     */
    public void switchToTransition(MultiPlayerState game) {
        long nextPhase = game.getNextPhase();

        game.setState(MultiPlayerState.TRANSITION_STATE);
        updateScore(game);
        // 3 seconds for transition phase
        game.setNextPhase(nextPhase + 3000);
    }

    /**
     * Update the score of the player and clear responses.
     * <p>
     * This method runs only in the transition state.
     * <p>
     * All responses from the player are aggregated (to determine the true response)
     * and then cleared.
     * <p>
     * Finally, the player's score is updated based on their response (the timing,
     * whether it is correct etc)
     *
     * @param game Game whose players' scores is updated (in the transition state).
     */
    private void updateScore(MultiPlayerState game) {
        if (game.getState().equals(MultiPlayerState.TRANSITION_STATE)) {
            for (int i = 0; i < game.getPlayers().size(); i++) {
                MultiPlayer currentPlayer = game.getPlayers().get(i);
                List<GameResponse> playerAnswers = new ArrayList<>();
                for (int j = 0; j < game.getSubmittedAnswers().size(); i++) {
                    GameResponse currentResponse = game.getSubmittedAnswers().get(j);
                    if (currentResponse.getPlayerUsername()
                            .equals(currentPlayer.getUsername())) {
                        playerAnswers.add(currentResponse);
                    }

                }
                GameResponse finalAnswer = computeFinalAnswer(playerAnswers);
                System.out.println(finalAnswer.toString());
                if (finalAnswer == null) {
                    finalAnswer = new GameResponse(
                            game.getId(),
                            Long.MAX_VALUE,
                            game.getRoundNumber(),
                            game.getPlayers().get(i).getUsername(),
                            "wrong answer"
                    );
                }
                /*
                Use shared comparing functionality implemented in the class
               MultiPlayerState.
                 */
                if (game.compareAnswer(finalAnswer)) {
                    /*
                    If the answer submitted is the same, then the score is updated accordingly.
                    */
                    MultiPlayer player = game.getPlayers().get(i);
                    player.setScore(player.getScore() + computeScore(finalAnswer));

                }
            }
        }
    }

    /**
     * Compute the score of a response.
     * If question's type is GuessQuestion then the score is computed based on how fast the answer was submitted and how close the player was to the actual answer.
     * If question's type is not GuessQuestion then the score is computed based only on how fast the answer was submitted
     * <p>
     *
     * @param response GameResponse of the player with a correct answer.
     * @return Number of points to add to the player's score
     */
    private int computeScore(GameResponse response) {
        int points = 0;
        AbstractQuestion currentQuestion = games.get(response.getGameId()).getQuestionList()
                .get(games.get(response.getGameId()).getRoundNumber());
        if (currentQuestion instanceof GuessQuestion) {
            String correctAnswer = currentQuestion.getCorrectAnswer();
            String submittedAnswer = response.getAnswerChoice();
            if (submittedAnswer.equals(correctAnswer)) {
                points = (int) (100 + (1.0 - response.getTimeSubmitted()) * 50.0);
            }
            if (Integer.parseInt(correctAnswer) < Integer.parseInt(submittedAnswer)
                    && Integer.parseInt(submittedAnswer) - Integer.parseInt(correctAnswer) <= 500) {
                points = (int) (100 + (1.0 - response.getTimeSubmitted()) * 50.0 - 0.1 *
                        (Integer.parseInt(submittedAnswer) - Integer.parseInt(correctAnswer)));
            } else {
                points = (int) (100 + (1.0 - response.getTimeSubmitted()) * 50.0
                        - 0.1 * (Integer.parseInt(correctAnswer)
                        - Integer.parseInt(submittedAnswer)));
            }
        } else {
            points = (int) (100 + (1.0 - response.getTimeSubmitted()) * 50.0);
        }
        return points;
    }

    /**
     * Compute the final answer that the player chose.
     * <p>
     * The set of responses is iterated over, with the last answer choice being
     * returned. If the last answer choice is selected multiple times, the first
     * instance in the suffix of the answer choices is returned.
     *
     * @param responses The list of responses submitted by a player.
     * @return The true response of the player.
     */
    public GameResponse computeFinalAnswer(List<GameResponse> responses) {
        if (responses.size() == 0) {
            return null;
        }
        Collections.sort(responses, ((o1, o2) -> (int) (o1.getTimeSubmitted() - o2.getTimeSubmitted())));
        GameResponse playerGameResponse = responses.get(0);
        for (GameResponse GameResponse : responses) {
            // Only update the GameResponse if it differs
            // This is done to avoid punishing the player from clicking
            // the same GameResponse multiple times
            if (!playerGameResponse.getAnswerChoice().equals(GameResponse.getAnswerChoice())) {
                playerGameResponse = GameResponse;
            }
        }
        return playerGameResponse;
    }

    /**
     * Start a new game.
     * <p>
     * This method is called whenever someone clicks "Go!" in the queue.
     *
     * @return id of the game that is starting.
     */
    public long startNewGame() {
        long upcomingGameId = nextGame.getId();
        // We set the time of the next phase to +3s, since this method is called
        // whenever anyone in the queue clicks "Go!"
        nextGame.setNextPhase(currentTime.getTime() + 3000);
        nextGame.setState(MultiPlayerState.STARTING_STATE);

        games.put(nextGame.getId(), nextGame);
        nextGame = createNextGame();

        return upcomingGameId;
    }

    /**
     * Initializes a new GameState object, for the next game.
     *
     * @return A template MultiPlayerState instance.
     */
    public MultiPlayerState createNextGame() {
        long id = generateNextGameId();
        long nextPhase = Long.MAX_VALUE;
        // Round number is incremented each time, so initial round number is -1
        int roundNumber = -1;
        List<AbstractQuestion> questionList = generateQuestionUtils.generate20Questions();
        List<GameResponse> submittedAnswers = new ArrayList<>();
        List<GameResponse> finalAnswers = new ArrayList<>();
        String state = MultiPlayerState.NOT_STARTED_STATE;
        List<MultiPlayer> players = new ArrayList<>();
        // Comment: what does Reaction mean here?
        // Somehow, it stores a list of strings, which are emojis?
        // But that makes very little sense.
        // It would make a lot more sense to store a List<Reaction>, where each
        // Reaction would mean a single reaction by a single user.
        //
        // Right now, it just makes very little sense.
        //
        // Whoever is planning to work on reactions will almost definitely refactor this
        Reaction reaction = null;
        return new MultiPlayerState(id, nextPhase, roundNumber, questionList,
                submittedAnswers, state, players, reaction);
    }

    /**
     * Generate id of the next game.
     * <p>
     * Ensures that the id of the next game is not already being played.
     *
     * @return Id for the next game.
     */
    public long generateNextGameId() {
        long max = -1;
        for (Long key : games.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        return max + 1;
    }

    /**
     * Posts an answer in the game a client is "bounded" with - particular instance found by gameId stored in the
     * GameResponse object sent.
     *
     * @param gameResponse GameResponse sent from client.
     * @return GameResponse instance in case the gameId is valid, or null otherwise.
     */
    public GameResponse postAnswer(GameResponse gameResponse) {
        long gameId = gameResponse.getGameId();
        if (!games.containsKey(gameId)) {
            return null;
        }
        games.get(gameId).getSubmittedAnswers().add(gameResponse);
        return gameResponse;
    }
}
