package client.scenes;

import client.scenes.multi.QueueScreenCtrl;
import client.services.MockQueueCountdownService;
import client.services.MockQueuePollingService;
import client.utils.MockServerUtils;
import commons.queue.QueueUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QueueScreenCtrlTest {

    private QueueScreenCtrl queueScreenCtrl;
    private MockServerUtils server;
    private MockMainCtrl mainCtrl;
    private MockMultiplayerCtrl multiCtrl;
    private MockQueuePollingService pollingService;
    private MockQueueCountdownService countdownService;

    @BeforeEach
    void setUp() {
        server = new MockServerUtils();
        mainCtrl = new MockMainCtrl();

        multiCtrl = new MockMultiplayerCtrl(mainCtrl, server, null);
        pollingService = new MockQueuePollingService(server);
        countdownService = new MockQueueCountdownService(server);
        queueScreenCtrl = new QueueScreenCtrl(server, mainCtrl, multiCtrl, pollingService, countdownService);
    }

    @Test
    void returnHome() {
        QueueUser user = new QueueUser("test");
        queueScreenCtrl.setUser(user);
        pollingService.returnValue = true;

        queueScreenCtrl.returnHome();

        assertEquals(
                List.of("stop"),
                pollingService.calledMethods
        );
        assertEquals(
                List.of("deleteQueueUser"),
                server.calledMethods
        );
        assertEquals(
                user,
                server.param
        );
        assertEquals(
                List.of("showHome"),
                mainCtrl.calledMethods
        );
    }

    @Test
    void getPollingService() {
        assertEquals(
                pollingService,
                queueScreenCtrl.getPollingService()
        );
    }

    @Test
    void getUserNull() {
        assertNull(queueScreenCtrl.getUser());
    }

    @Test
    void setUser() {
        QueueUser user = new QueueUser("test");
        queueScreenCtrl.setUser(user);
        assertEquals(user, queueScreenCtrl.getUser());
    }

    @Test
    void leaveQueue() {
        QueueUser expectedQueueUser = new QueueUser("TestUser");
        server.returnValue = expectedQueueUser;
        pollingService.returnValue = false;

        assertEquals(expectedQueueUser, queueScreenCtrl.leaveQueue());

        assertEquals(List.of("stop"), pollingService.calledMethods);
        assertEquals(List.of("stop"), countdownService.calledMethods);
        assertEquals(
                List.of("deleteQueueUser"),
                server.calledMethods
        );
    }
}