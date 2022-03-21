
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;

public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /*
    The following field is required to store the `SinglePlayer` instance,
    consisting of the username entered on the `Main Screen` and a default score - 0.
     */
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
        /*
        Our single-player game controller should keep reference to the player and the corresponding game
        they are currently playing. Thus, they are passed as arguments here.
         */
        mainCtrl.playSoloGame(singlePlayer, server.startSinglePlayerGame(singlePlayer));
    }

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