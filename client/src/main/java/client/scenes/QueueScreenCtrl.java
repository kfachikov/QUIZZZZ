package client.scenes;

import client.utils.ServerUtils;

import javax.inject.Inject;

public class QueueScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public QueueScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Returns from the queue screen back to the home screen.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }
}
