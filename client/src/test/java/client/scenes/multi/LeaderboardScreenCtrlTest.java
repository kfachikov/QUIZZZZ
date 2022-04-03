package client.scenes.multi;

import client.scenes.MockMainCtrl;
import client.scenes.MockMultiplayerCtrl;
import client.services.MockMultiplayerGameStatePollingService;
import client.utils.MockServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderboardScreenCtrlTest {
    private LeaderboardScreenCtrl leaderboardScreenCtrl;
    private MockServerUtils server;
    private MockMultiplayerCtrl multiCtrl;
    private MockMainCtrl mainCtrl;
    private MockMultiplayerGameStatePollingService pollingService;

    @BeforeEach
    void setUp() {
        server = new MockServerUtils();
        mainCtrl = new MockMainCtrl();
        multiCtrl = new MockMultiplayerCtrl(mainCtrl, server, pollingService);
        leaderboardScreenCtrl = new LeaderboardScreenCtrl(server, multiCtrl);
    }

    @Test
    void leave() {
        multiCtrl.returnValue = true;
        leaderboardScreenCtrl.leave();
        assertEquals(List.of("promptLeave"), multiCtrl.calledMethods);
    }

    @Test
    void returnHome() {
        leaderboardScreenCtrl.playAgain();
        assertEquals(List.of("showQueue"), mainCtrl.calledMethods);
    }
}