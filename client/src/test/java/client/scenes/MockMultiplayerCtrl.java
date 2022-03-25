package client.scenes;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.MultiGameMockScreenCtrl;
import client.scenes.multi.MultiplayerCtrl;
import client.scenes.multi.question.MultiGameQuestionAScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionBScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionCScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionDScreenCtrl;
import client.services.MultiplayerGameStatePollingService;
import client.utils.ServerUtils;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock method for testing other classes that use MultiplayerCtrl.
 */
public class MockMultiplayerCtrl extends MultiplayerCtrl {

    public List<String> calledMethods;
    public Object returnValue;
    public Object param1;
    public Object param2;

    /**
     * Constructor for mock class.
     * <p>
     * Initializes private fields and initializes calledMethods.
     *
     * @param mainCtrl       Main controller
     * @param serverUtils    Server utilities
     * @param pollingService Multiplayer game state polling service
     */
    public MockMultiplayerCtrl(MainCtrl mainCtrl,
                               ServerUtils serverUtils,
                               MultiplayerGameStatePollingService pollingService) {
        super(mainCtrl, serverUtils, pollingService);
        this.calledMethods = new ArrayList<>();
    }

    private void call(String method) {
        calledMethods.add(method);
    }


    @Override
    public void initialize(
            Pair<MultiGameQuestionAScreenCtrl, Parent> questionAScreen,
            Pair<MultiGameQuestionBScreenCtrl, Parent> questionBScreen,
            Pair<MultiGameQuestionCScreenCtrl, Parent> questionCScreen,
            Pair<MultiGameQuestionDScreenCtrl, Parent> questionDScreen,
            Pair<MultiGameMockScreenCtrl, Parent> mockMulti) {
        call("initialize");
    }

    /**
     * Start a multiplayer session.
     * <p>
     * Will automatically switch scenes to multiplayer question screens, leaderboard etc.
     * <p>
     * Can be stopped using `stop()`
     *
     * @param gameId   Id of the multiplayer game
     * @param username Name of the player in the game
     */
    @Override
    public void start(long gameId, String username) {
        param1 = gameId;
        param2 = username;
        call("start");
    }

    /**
     * Stop the multiplayer session.
     * <p>
     * Resets the controller to a state where another multiplayer game can be played later.
     */
    @Override
    public void stop() {
        call("stop");
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    @Override
    public void promptLeave() {
        call("promptLeave");
    }
}
