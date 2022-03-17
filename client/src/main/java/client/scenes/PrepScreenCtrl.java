package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.SinglePlayer;

public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private SinglePlayer singlePlayer;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        mainCtrl.showSoloGameQuestion(singlePlayer);
    }

    /**
     * Getter for the player residing in the preparation screen.
     *
     * @return SinglePlayer instance containing the username and the initial score (set to 0) of the player.
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
}
