
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;

/**
 *
 */
public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /*
    The following field is required to store the `SinglePlayer` instance,
    consisting of the username entered on the `Main Screen` and a default score - 0.
     */
    private SinglePlayer singlePlayer;

    private SinglePlayerUtils singlePlayerUtils;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
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
        singlePlayerUtils.setSinglePlayerAttributes(singlePlayer, server.startSinglePlayerGame(singlePlayer));
        mainCtrl.playSoloGame(singlePlayerUtils);
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