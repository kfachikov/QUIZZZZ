package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class HelpScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public HelpScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void returnHome() {
        mainCtrl.showHome();
    }
}
