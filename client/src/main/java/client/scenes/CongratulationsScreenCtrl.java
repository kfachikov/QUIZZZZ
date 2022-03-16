package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class CongratulationsScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * initializes CongratulationsScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public CongratulationsScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * sets the scene and title to single-player game.
     */
    public void playSoloGame() {
        mainCtrl.showSoloGameQuestion();
    }
}
