package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void returnHome() {
        mainCtrl.showHome();
    }
}
