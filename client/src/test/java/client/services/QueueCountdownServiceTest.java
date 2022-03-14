package client.services;

import client.utils.MockServerUtils;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueCountdownServiceTest {

    private MockServerUtils server;
    private QueueCountdownService qcs;

    @BeforeEach
    void setup() {
        server = new MockServerUtils();
        qcs = new QueueCountdownService(server);
    }

    @Test
    void testConstructor() {
        assertNotNull(qcs.getTimeline());
        assertEquals(1, qcs.getTimeline().getKeyFrames().size());
        assertEquals(1, qcs.getTimeline().getCycleCount());
        assertEquals(Duration.seconds(3), qcs.getTimeline().getCycleDuration());
    }
}