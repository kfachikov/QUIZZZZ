package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;

public class LeaderboardScreenCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * initializes IntermediateLeaderboardScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public LeaderboardScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }




}
