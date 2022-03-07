package server.api;

import commons.MultiUserQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class LobbyControllerTest {

    private MockMultiUserRepository repo;
    private LobbyController userCtrl;

    private int nextId;

    @BeforeEach
    public void setup() {
        repo = new MockMultiUserRepository();
        userCtrl = new LobbyController(repo);
        nextId = 0;
    }

    private List<MultiUserQueue> addMockUsers() {
        List<MultiUserQueue> mockUser = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            mockUser.add(
                    new MultiUserQueue("p" + nextId, 0)
            );
            mockUser.get((int) i).id = nextId++;
        }
        repo.multiUserQueues.addAll(mockUser);
        return mockUser;
    }

    @Test
    public void testGetAllUsers() {
        var expected = addMockUsers();
        var result = userCtrl.getAllUsers();
        assertEquals(expected, result);
    }

    @Test
    public void testMethodCall() {
        addMockUsers();
        userCtrl.getAllUsers();
        assertEquals(List.of("findAll"), repo.calledMethods);
    }

    @Test
    public void databaseIsUsed() {
        userCtrl.add(getUser("username"));
        assertEquals(List.of("save"), repo.calledMethods);
    }

    @Test
    public void cannotAddNullPlayer() {
        var actual = userCtrl.add(getUser(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }



    private static MultiUserQueue getUser(String username) {
        return new MultiUserQueue(username, 0);
    }

    @Test
    public void testBadRequest() {
        var response = userCtrl.deleteUser(1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testNotFound() {
        MultiUserQueue user = new MultiUserQueue("ok" + -1, 0);
        repo.save(user);
        var response = userCtrl.deleteUser(-1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}