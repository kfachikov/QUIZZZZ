package server.utils;

import commons.misc.Activity;
import commons.misc.GameResponse;
import commons.question.AbstractQuestion;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.*;

/**
 * Utility class providing functionality for the single-player game mode.
 */
@Component
@ComponentScan(basePackageClasses = GenerateQuestionUtils.class)
public class SinglePlayerStateUtils {

    private final Map<Long, SinglePlayerState> games;
    private final GenerateQuestionUtils generateQuestionUtils;

    public SinglePlayerStateUtils(GenerateQuestionUtils generateQuestionUtils) {
        this.generateQuestionUtils = generateQuestionUtils;
        games = new HashMap<>();
    }

    /**
     * Get particular game state instance by its key in the games map.
     * Update its state if required.
     *
     * @param id Key value to search for.
     * @return SinglePlayerState instance in case it exists, null otherwise.
     */
    public SinglePlayerState getGameStateById(long id) {
        if (games.containsKey(id)) {
            SinglePlayerState game = games.get(id);
            updateState(game);
            return game;
        } else {
            return null;
        }
    }

    /**
     * Posts an answer in the current game - particular instance found by gameId stored in the
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

    /**
     * Updates the state of the single-player game state based on the time of the
     * next phase.
     *
     * @param game SinglePlayer game state to update
     */
    public void updateState(SinglePlayerState game) {
        long time = new Date().getTime();

        // Check if we should be changing the state of the game
        if (time >= game.getNextPhase()) {
            if (game.getState().equals(SinglePlayerState.QUESTION_STATE)) {
                game.setState(SinglePlayerState.TRANSITION_STATE);
                game.setNextPhase(game.getNextPhase() + 3000);
                updateScore(game);
            } else if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
                /*
                Should be 19, as that would mean that the 19th round is just over.
                 */
                if (game.getRoundNumber() >= 19) {
                    game.setState(SinglePlayerState.GAME_OVER_STATE);
                } else {
                    game.setState(SinglePlayerState.QUESTION_STATE);
                    game.setNextPhase(game.getNextPhase() + 8000);
                    game.setRoundNumber(game.getRoundNumber() + 1);
                }
            }
        }
    }

    /**
     * Update the score of the player and clear GameResponses.
     * <p>
     * This method runs only in the transition state.
     * <p>
     * All GameResponses from the player are aggregated (to determine the true GameResponse)
     * and then cleared.
     * <p>
     * Finally, the player's score is updated based on their GameResponse (the timing,
     * whether it is correct etc)
     *
     * @param game Singleplayer game state (in the transition state)
     */
    private void updateScore(SinglePlayerState game) {
        if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
            GameResponse playerGameResponse = computeFinalAnswer(game);
            /*
            Saves the latest GameResponse of the player in the list of answers submitted as final.
             */
            game.getFinalAnswers().add(playerGameResponse);

            /*
            Clear the game from any previous answers.
             */
            game.getSubmittedAnswers().clear();

            /*
            Use shared comparing functionality implemented in the class
            SinglePlayerState.
             */
            if (game.compareAnswer()) {
                /*
                If the answer submitted is the same, then the score is updated accordingly.
                 */
                SinglePlayer player = game.getPlayer();
                player.setScore(player.getScore() + computeScore(playerGameResponse));
            }
        }
    }

    /**
     * Compute the score of a GameResponse.
     * <p>
     * TODO: this method is just a mock method, and returns 100.
     *
     * @param gameResponse GameResponse of the player with a correct answer. TODO: The time submitted to be used for computations.
     * @return Number of points to add to the player's score
     */
    private int computeScore(GameResponse gameResponse) {
        return 100;
    }


    /**
     * Compute the final answer that the player chose.
     * <p>
     * The set of GameResponses is iterated over, with the last answer choice being
     * returned. If the last answer choice is selected multiple times, the first
     * instance in the suffix of the answer choices is returned.
     *
     * @param game Singleplayer game for which the answer is computed.
     * @return The true GameResponse of the player.
     */
    public GameResponse computeFinalAnswer(SinglePlayerState game) {
        // Dummy GameResponse, if the player did not choose anything
        GameResponse playerGameResponse = new GameResponse(
                game.getId(),
                Long.MAX_VALUE,
                game.getRoundNumber(),
                game.getPlayer().getUsername(),
                "wrong answer"
        );
        // GameResponses are sorted by the submission time.
        Comparator<GameResponse> comp =
                (a, b) -> (int) (a.getTimeSubmitted() - b.getTimeSubmitted());
        game.getSubmittedAnswers().sort(comp);
        for (GameResponse GameResponse : game.getSubmittedAnswers()) {
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
     * Constructs a new single-player game state.
     *
     * @param player Player who is playing in the game
     * @param repo   ActivityRepository instance.
     * @return The newly constructed SinglePlayer game state
     */
    public SinglePlayerState createSingleGame(SinglePlayer player,
                                              ActivityRepository repo) {
        Set<Long> keys = games.keySet();
        long maxKey;
        if (keys.isEmpty()) {
            maxKey = -1;
        } else {
            maxKey = Collections.max(keys);
        }
        long id = maxKey + 1;
        long nextTransition = new Date().getTime() + 8000;
        int roundNumber = 0;
        List<AbstractQuestion> questionList = generateQuestionUtils.generate20Questions();
        List<GameResponse> submittedAnswers = new ArrayList<>();
        List<GameResponse> finalAnswers = new ArrayList<>();
        List<Activity> activityList = new ArrayList<>();
        String state = SinglePlayerState.QUESTION_STATE;

        SinglePlayerState newGame = new SinglePlayerState(
                id,
                nextTransition,
                roundNumber,
                questionList,
                finalAnswers,
                submittedAnswers,
                state,
                player
        );
        games.put(newGame.getId(), newGame);
        return newGame;
    }
}
