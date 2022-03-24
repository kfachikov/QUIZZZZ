package server.api;

import org.junit.jupiter.api.BeforeEach;
import server.utils.QueueUtils;

class QueueControllerTest {

    private QueueController lobbyCtrl;
    private QueueUtils queueUtils;


    @BeforeEach
    public void setup() {
        queueUtils = new QueueUtils();
        lobbyCtrl = new QueueController(queueUtils);
    }
}