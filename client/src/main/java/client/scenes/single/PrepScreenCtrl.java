
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import commons.single.SinglePlayerState;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PrepScreenCtrl {

    /**
     * Initialize class constant for the number of player's usernames visible on screen.
     */
    private static final int PLAYERSCOUNT = 20;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /*
    The following field is required to store the `SinglePlayer` instance,
    consisting of the username entered on the `Main Screen` and a default score - 0.
     */
    private SinglePlayer singlePlayer;

    private SinglePlayerUtils singlePlayerUtils;

    @FXML
    private FlowPane bubbles;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     * @param singlePlayerUtils is the shared single-player utility instance.
     */
    @Inject
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl, SinglePlayerUtils singlePlayerUtils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.singlePlayerUtils = singlePlayerUtils;
    }

    /**
     * sets the scene and title to home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * sets the scene and title to single-player game.
     */
    public void playSoloGame() {
        /*
        The single-player game logic utility should keep reference to the player and the corresponding game
        they are currently playing. Thus, they are passed as arguments here.
         */
        singlePlayerUtils.setSinglePlayerAttributes(server.startSinglePlayerGame(singlePlayer));
        mainCtrl.playSoloGame(singlePlayerUtils);
    }


    /**
     * Getter for the single-player instance create once the client has been redirected to the preparation screen.
     *
     * @return  SinglePlayer instance.
     */
    public SinglePlayer getSinglePlayer() {
        return singlePlayer;
    }

    /**
     * Setter for the player - would be used to store his username and a default score (0).
     *
     * @param singlePlayer a Player instance to set for the field singlePlayer.
     */
    public void setSinglePlayer(SinglePlayer singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /**
     * loads up the leaderboard on the prep screen.
     */
    public void initialize() {
        List<SinglePlayerLeaderboardScore> leaderboardScores = SinglePlayerState.getLeaderboardScores();

        int currentNodeIndex = 0;
        List<Node> presentPlayers = bubbles.getChildren();
        leaderboardScores.sort(null);
        Collections.reverse(leaderboardScores);

        for (SinglePlayerLeaderboardScore singleplayer : leaderboardScores) {
            Node currentNode = presentPlayers.get(currentNodeIndex);
            ((Label) currentNode).setText(singleplayer.getUsername() + " " + singleplayer.getScore());
            currentNode.setVisible(true);
            currentNodeIndex++;
            if (currentNodeIndex > PLAYERSCOUNT) {
                break;
            }
        }
        for (int i = currentNodeIndex; i <= PLAYERSCOUNT; i++) {
            Node currentNode = presentPlayers.get(currentNodeIndex);
            currentNode.setVisible(false);
        }

    }
}