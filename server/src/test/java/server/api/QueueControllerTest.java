package server.api;

import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

class QueueControllerTest {

    private QueueUserRepository repo;
    private QueueController lobbyCtrl;

    private int nextId;

    @BeforeEach
    public void setup() {
        repo = new QueueUserRepository();
        lobbyCtrl = new QueueController(repo);
        nextId = 0;
    }

    private List<QueueUser> addMockUsers() {
        List<QueueUser> users = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            users.add(
                    new QueueUser("p" + nextId)
            );
            users.get((int) i).setId(nextId++);
        }
        repo.queueUsers.addAll(users);
        return users;
    }

    private static QueueUser getUser(String username) {
        return new QueueUser(username);
    }

    @Test
    public void testGetQueueState() {
        var expected = new QueueState(addMockUsers());
        var response = lobbyCtrl.getQueueState();
        var result = response.getBody();
        assertEquals(expected.getUsers(), result.getUsers());
        assertEquals(expected.isGameStarting(), result.isGameStarting());
    }

    @Test
    public void testMethodCall() {
        addMockUsers();
        lobbyCtrl.getQueueState();
        assertEquals(List.of("findAll"), repo.calledMethods);
    }

    @Test
    public void databaseIsUsed() {
        lobbyCtrl.add(getUser("username"));
        assertEquals(List.of("save"), repo.calledMethods);
    }

    @Test
    public void cannotAddNullPlayer() {
        var actual = lobbyCtrl.add(getUser(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void testNotUniqueUsername() {
        addMockUsers();
        var actual = lobbyCtrl.add(getUser("p0"));
        assertEquals(FORBIDDEN, actual.getStatusCode());
    }

    @Test
    public void testBadRequest() {
        var response = lobbyCtrl.deleteUser(1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testNotFound() {
        QueueUser user = new QueueUser("ok" + -1);
        repo.save(user);
        var response = lobbyCtrl.deleteUser(-1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testStartGame() {
        QueueState queueState = new QueueState(addMockUsers(), true, 3000, 0L);
        var response = lobbyCtrl.startGame();
        QueueState result = response.getBody();
        assertEquals(queueState, result);
    }

    @Test
    public void testStartGameTwice() {
        addMockUsers();
        lobbyCtrl.startGame();
        var response = lobbyCtrl.startGame();
        assertEquals(FORBIDDEN, response.getStatusCode());
    }
}