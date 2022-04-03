package server.utils;

import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Mock class for testing other classes which use MultiPlayerStateUtils.
 */
public class MockMultiPlayerStateUtils extends MultiPlayerStateUtils {

    /**
     * List of methods that were called on this instance.
     */
    public ArrayList<String> calledMethods = new ArrayList<>();
    /**
     * Return value of any called method.
     */
    public Queue<Object> returnValues = new LinkedList<>();

    public ArrayList<Object> params = new ArrayList<>();

    /**
     * Constructor for multiplayer server-side utility class.
     */
    public MockMultiPlayerStateUtils() {
        super(new MockGenerateQuestionUtils(new MockActivityRepository(), new Random()),
                new QueueUtils(new MockCurrentTimeUtils()), new MockCurrentTimeUtils(),
                new ScoreCountingUtils());
    }

    @Override
    protected void initialize() {

    }

    /**
     * Getter for the multiplayer game state.
     * <p>
     * Internally updates the state to be up-to-date.
     *
     * @param id Id of the multiplayer game.
     * @return Multiplayer game state with that id.
     */
    @Override
    public MultiPlayerState getGameState(long id) {
        calledMethods.add("getGameState");
        params.add(id);
        return (MultiPlayerState) returnValues.poll();
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
    @Override
    public MultiPlayer addPlayer(long id, MultiPlayer player) {
        calledMethods.add("addPlayer");
        params.add(id);
        params.add(player);
        return (MultiPlayer) returnValues.poll();
    }

    /**
     * Check and update (if needed) the state of the game.
     *
     * @param game Multiplayer game whose state is to be checked and updated.
     */
    @Override
    public void updateState(MultiPlayerState game) {
        calledMethods.add("updateState");
        params.add(game);
    }

    /**
     * Check if state of the game has to be updated.
     *
     * @param game Game to be checked
     * @return true iff current time is beyond time of update for the state of the game.
     */
    @Override
    public boolean checkIfUpdate(MultiPlayerState game) {
        calledMethods.add("checkIfUpdate");
        params.add(game);
        return (boolean) returnValues.poll();
    }

    /**
     * Switch the state of the game to the next appropriate state.
     * <p>
     * Beware of calling this method if the state should not be updated yet.
     * It will prematurely switch the state of the game, and leave the next phase longer.
     *
     * @param game Game whose state is to be switched to the next one.
     */
    @Override
    public void switchState(MultiPlayerState game) {
        calledMethods.add("switchState");
        params.add(game);
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
    @Override
    public void switchToQuestion(MultiPlayerState game) {
        calledMethods.add("switchToQuestion");
        params.add(game);
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
    @Override
    public void switchToLeaderboard(MultiPlayerState game) {
        calledMethods.add("switchToLeaderboard");
        params.add(game);
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
    @Override
    public void switchToGameOver(MultiPlayerState game) {
        calledMethods.add("switchToGameOver");
        params.add(game);
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
    @Override
    public void switchToTransition(MultiPlayerState game) {
        calledMethods.add("switchToTransition");
        params.add(game);
    }

    /**
     * Update scores of all the players.
     *
     * @param game Game whose players' scores is updated.
     */
    public void updateScores(MultiPlayerState game) {
        calledMethods.add("updateScores");
        params.add(game);
    }

    /**
     * Start a new game.
     * <p>
     * This method is called whenever someone clicks "Go!" in the queue.
     *
     * @return id of the game that is starting.
     */
    @Override
    public long startNewGame() {
        calledMethods.add("startNewGame");
        return (long) returnValues.poll();
    }

    /**
     * Initializes a new GameState object, for the next game.
     *
     * @return A template MultiPlayerState instance.
     */
    @Override
    public MultiPlayerState createNextGame() {
        calledMethods.add("createNextGame");
        return (MultiPlayerState) returnValues.poll();
    }

    /**
     * Generate id of the next game.
     * <p>
     * Ensures that the id of the next game is not already being played.
     *
     * @return Id for the next game.
     */
    @Override
    public long generateNextGameId() {
        calledMethods.add("generateNextGameId");
        return (long) returnValues.poll();
    }
}
