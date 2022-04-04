package server.utils;

import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.misc.Player;

import java.util.ArrayList;

/**
 * Mock class for testing other classes that use ScoreCountingUtils.
 */
public class MockScoreCountingUtils extends ScoreCountingUtils {

    /**
     * Methods that were called on this instance.
     */
    public ArrayList<String> calledMethods = new ArrayList<>();

    /**
     * Overridden method to note the "usage" of the method.
     *
     * @param game      Multiplayer game instance to get the questions from.
     * @param player    Player to be passed as argument - to be used in case
     *                  of double joker usage.
     * @param response  GameResponse of the player with a correct answer.
     * @return          Number of points allocated to the answer submitted.
     */
    @Override
    public int computeScore(GameState game, Player player, GameResponse response) {
        calledMethods.add("computeScore");
        return super.computeScore(game, player, response);
    }
}
