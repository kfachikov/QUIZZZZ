package server.api;

import commons.QueueUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

class LobbyControllerTest {

    private QueueUserRepository repo;
    private LobbyController lobbyCtrl;

    private int nextId;

    @BeforeEach
    public void setup() {
        repo = new QueueUserRepository();
        lobbyCtrl = new LobbyController(repo);
        nextId = 0;
    }

    private List<QueueUser> addMockUsers() {
        List<QueueUser> mockUser = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            mockUser.add(
                    new QueueUser("p" + nextId, 0)
            );
            mockUser.get((int) i).id = nextId++;
        }
        repo.queueUsers.addAll(mockUser);
        return mockUser;
    }

    private static QueueUser getUser(String username) {
        return new QueueUser(username, 0);
    }

    @Test
    public void testGetAllUsers() {
        var expected = addMockUsers();
        var result = lobbyCtrl.getAllUsers();
        assertEquals(expected, result);
    }

    @Test
    public void testMethodCall() {
        addMockUsers();
        lobbyCtrl.getAllUsers();
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
        assertEquals(NOT_FOUND, actual.getStatusCode());
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
        QueueUser user = new QueueUser("ok" + -1, 0);
        repo.save(user);
        var response = lobbyCtrl.deleteUser(-1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}