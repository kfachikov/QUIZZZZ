package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
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
}
