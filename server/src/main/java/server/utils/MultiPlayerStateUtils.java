package server.utils;

import commons.misc.Response;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.multi.Reaction;
import commons.question.AbstractQuestion;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Utility class providing functionality for the multiplayer game mode.
 */
@Component
@ComponentScan(basePackageClasses = GenerateQuestionUtils.class)
public class MultiPlayerStateUtils {

    private final Map<Long, MultiPlayerState> games;
    private MultiPlayerState nextGame;

    private final GenerateQuestionUtils generateQuestionUtils;
    private final QueueUtils queueUtils;

    /**
     * Constructor for multiplayer server-side utility class.
     *
     * @param generateQuestionUtils is the "generate questions" utility bean injected by Spring.
     * @param queueUtils            is the class responsible for managing the queue.
     */
    public MultiPlayerStateUtils(GenerateQuestionUtils generateQuestionUtils,
                                 QueueUtils queueUtils) {
        this.generateQuestionUtils = generateQuestionUtils;
        this.queueUtils = queueUtils;
        this.games = new HashMap<>();

        nextGame = createNextGame();

        queueUtils.setOnStart(this::startNewGame);
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
        return game.getNextPhase() <= new Date().getTime();
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
        if (MultiPlayerState.NOT_STARTED_STATE.equals(state)) {
            // All game states are started when put into `games`, so
            // if a state somehow has not started yet, an error has occurred.
            //
            // However, there is not really a good way to resolve the error,
            // so we just log it.
            System.err.println("Attempted to update state of game that hasn't started yet.");
        } else if (MultiPlayerState.STARTING_STATE.equals(state)) {
            // We should definitely switch to a question phase.
            startNextRound(game);
        } else if (MultiPlayerState.QUESTION_STATE.equals(state)) {

        }
    }

    /**
     * Start the next round of the game.
     * <p>
     * This is one of the state transitions of the game:
     * - STARTING -> QUESTION
     * - TRANSITION -> QUESTION
     * - LEADERBOARD -> QUESTION
     * <p>
     * The next state after calling this method is guaranteed to be QUESTION.
     *
     * @param game Game whose state is updated to QUESTION.
     */
    private void startNextRound(MultiPlayerState game) {
        int currentRound = game.getRoundNumber();
        long nextPhase = game.getNextPhase();

        game.setRoundNumber(currentRound + 1);
        game.setState(MultiPlayerState.QUESTION_STATE);
        // 8 seconds for questions
        game.setNextPhase(nextPhase + 8000);
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
        nextGame.setNextPhase(new Date().getTime() + 3000);
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
    private MultiPlayerState createNextGame() {
        long id = generateNextGameId();
        long nextPhase = Long.MAX_VALUE;
        // Round number is incremented each time, so initial round number is -1
        int roundNumber = -1;
        List<AbstractQuestion> questionList = generateQuestionUtils.generate20Questions();
        List<Response> submittedAnswers = new ArrayList<>();
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
    private long generateNextGameId() {
        long max = -1;
        for (Long key : games.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        return max + 1;
    }
}
