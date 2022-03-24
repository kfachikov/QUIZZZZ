
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.QueuePollingService;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import javax.swing.table.TableColumn;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PrepScreenCtrl {

    /**
     * Initialize class constant for the number of player's usernames visible on screen.
     */
    private static final int PLAYERSCOUNT = 16;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final QueuePollingService pollingService;

    private SinglePlayerLeaderboardScore leaderboardscore;

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
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl, SinglePlayerUtils singlePlayerUtils,
                          QueuePollingService pollingService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.singlePlayerUtils = singlePlayerUtils;
        this.pollingService = pollingService;
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
     * Getter for the queue polling service.
     *
     * @return Queue polling service
     */
    public QueuePollingService getPollingService() {
        return pollingService;
    }


    /**
     * Initializes the queue screen controller by binding the queue label the queue "bubbles" to the
     * results of the polling service.
     * <p>
     * The queue polling service will repeatedly poll the server, and update its
     * own value.
     * When such update occurs, any attached event listeners are called.
     * This method attaches such an event listener to the value of the polling
     * service. Thus, whenever the polling service updates its value, this event
     * listener is called.
     * <p>
     * The actual callback of the event listener simply sets the queue label to
     * the appropriate value.
     */
    public void initialize() {

        /*
        Create an event listener for short-polling
         */
        pollingService.valueProperty().addListener((observable, oldValue, newSinglePlayerState) -> {
            if (newSinglePlayerState != null) {
                List<SinglePlayerLeaderboardScore> leaderboardScores = newSinglePlayerState.getLeaderboardScores();

                int currentNodeIndex = 0;
                List<Node> presentPlayers = bubbles.getChildren();
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
        });

    }
}