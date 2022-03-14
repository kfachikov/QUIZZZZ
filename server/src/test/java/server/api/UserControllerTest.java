package server.api;

import commons.SingleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class UserControllerTest {

    private MockUserRepository repo;
    private UserController userCtrl;

    private int nextId;

    @BeforeEach
    public void setup() {
        repo = new MockUserRepository();
        userCtrl = new UserController(repo);
        nextId = 0;
    }

    private List<SingleUser> addMockUsers() {
        List<SingleUser> mockUser = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            mockUser.add(
                    new SingleUser("p" + nextId, 0)
            );
            mockUser.get((int) i).id = nextId++;
        }
        repo.singleUsers.addAll(mockUser);
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
    public void cannotAddNullPlayer() {
        var actual = userCtrl.add(getUser(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        userCtrl.add(getUser("username"));
        assertEquals(List.of("save"), repo.calledMethods);
    }

    private static SingleUser getUser(String username) {
        return new SingleUser(username, 0);
    }

}