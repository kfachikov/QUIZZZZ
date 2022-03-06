package server.api;

import commons.MultiUser;
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

    private void addMockUsers() {
        List<MultiUser> mockUser = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            mockUser.add(
                    new MultiUser("p" + nextId, 0)
            );
            mockUser.get((int) i).id = nextId++;
        }
        repo.multiUsers.addAll(mockUser);
    }

    @Test
    public void testGetAllUsers() {
        addMockUsers();
        var result = userCtrl.getAllUsers();
        assertEquals(List.of("findAll"), repo.calledMethods);

        for (int i = 0; i < result.size(); i++) {
            MultiUser pers = result.get(i);
            String id = String.valueOf(i);
            MultiUser expected = new MultiUser("p" + id, 0);
            expected.id = i;
            assertEquals(expected, pers);
        }
    }

    @Test
    public void databaseIsUsed() {
        userCtrl.add(getUser("username"));
        repo.calledMethods.contains("save");
    }

    @Test
    public void cannotAddNullPlayer() {
        var actual = userCtrl.add(getUser(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }



    private static MultiUser getUser(String username) {
        return new MultiUser(username, 0);
    }


}