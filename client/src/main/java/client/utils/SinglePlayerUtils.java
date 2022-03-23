package client.utils;

import client.services.GameStatePollingService;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javax.inject.Inject;

public class SinglePlayerUtils {

    /*
    Instances used for the single-player mode to extract polling service functionality.
    SinglePlayer and SinglePlayerState instances of the current pair Player-Game on the client-side.
     */
    private GameStatePollingService pollingService;
    private SinglePlayer singlePlayer;
    private SinglePlayerState singlePlayerState;

    @Inject
    public SinglePlayerUtils(GameStatePollingService pollingService) {
        this.pollingService = pollingService;
    }
}
