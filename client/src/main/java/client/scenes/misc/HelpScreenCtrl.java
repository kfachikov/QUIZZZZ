package client.scenes.misc;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class HelpScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * initializes HelpScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public HelpScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }
}
