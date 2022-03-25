package server.api;

import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import server.utils.MockCurrentTimeUtils;
import server.utils.MockQueueUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueControllerTest {

    private QueueController queueCtrl;

    private MockCurrentTimeUtils currentTime;
    private MockQueueUtils queueUtils;

    private QueueState queueState;
    private QueueUser queueUser;

    @BeforeEach
    void setUp() {
        currentTime = new MockCurrentTimeUtils();
        queueUtils = new MockQueueUtils(currentTime);

        queueCtrl = new QueueController(queueUtils);

        queueUser = new QueueUser("Username");

        queueState = new QueueState(
                List.of(queueUser),
                false,
                Long.MAX_VALUE,
                12345
        );

    }

    @Test
    void getQueueState() {
        queueUtils.returnValues.add(queueState);

        var result = queueCtrl.getQueueState().getBody();

        assertEquals(queueState, result);
        assertEquals(List.of("getQueue"), queueUtils.calledMethods);
    }

    @Test
    void addInvalid() {
        // Set isInvalid to true
        queueUtils.returnValues.add(true);

        var emptyUser = new QueueUser("");

        var result = queueCtrl.add(emptyUser);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(List.of("isInvalid"), queueUtils.calledMethods);
        assertEquals(List.of(emptyUser), queueUtils.params);
    }

    @Test
    void addContains() {
        // Set isInvalid to false
        queueUtils.returnValues.add(false);
        // Set containsUser to true
        queueUtils.returnValues.add(true);

        var result = queueCtrl.add(queueUser);

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals(List.of("isInvalid", "containsUser"), queueUtils.calledMethods);
        assertEquals(List.of(queueUser, queueUser), queueUtils.params);
    }

    @Test
    void add() {
        // Set isInvalid to false
        queueUtils.returnValues.add(false);
        // Set containsUser to false
        queueUtils.returnValues.add(false);
        // Set return value to a dummy value
        var dummyUser = new QueueUser("dummy");
        queueUtils.returnValues.add(dummyUser);

        var result = queueCtrl.add(queueUser);

        assertEquals(dummyUser, result.getBody());
        assertEquals(List.of("isInvalid", "containsUser", "addUser"), queueUtils.calledMethods);
        assertEquals(List.of(queueUser, queueUser, queueUser), queueUtils.params);
    }

    @Test
    void startGameGood() {
        // Set startCountdown to true
        queueUtils.returnValues.add(true);
        // Set getQueue to given queue
        queueUtils.returnValues.add(queueState);

        var result = queueCtrl.startGame();

        assertEquals(queueState, result.getBody());
        assertEquals(List.of("startCountdown", "getQueue"), queueUtils.calledMethods);
    }

    @Test
    void startGameBad() {
        // Set startCountdown to true
        queueUtils.returnValues.add(false);

        var result = queueCtrl.startGame();

        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals(List.of("startCountdown"), queueUtils.calledMethods);
    }

    @Test
    void deleteUser() {
        // Set getByUsername to dummy value
        var dummy1 = new QueueUser("dummy1");
        queueUtils.returnValues.add(dummy1);
        // set removeUser to dummy value
        var dummy2 = new QueueUser("dummy2");
        queueUtils.returnValues.add(dummy2);

        var result = queueCtrl.deleteUser("Test username");
        assertEquals(dummy2, result.getBody());
        assertEquals(List.of("getByUsername", "removeUser"), queueUtils.calledMethods);
        assertEquals(List.of("Test username", dummy1), queueUtils.params);
    }

    @Test
    void deleteUserBad() {
        // Set getByUsername to dummy value
        QueueUser dummy1 = null;
        queueUtils.returnValues.add(dummy1);
        // set removeUser to dummy value
        QueueUser dummy2 = null;
        queueUtils.returnValues.add(dummy2);

        var result = queueCtrl.deleteUser("Test username");
        assertEquals(dummy2, result.getBody());
        assertEquals(List.of("getByUsername", "removeUser"), queueUtils.calledMethods);
        assertEquals(Arrays.asList("Test username", null), queueUtils.params);
    }
}