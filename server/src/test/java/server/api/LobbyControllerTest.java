package server.api;

import commons.MultiUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LobbyControllerTest {

    private MockMultiUserRepository repo;
    private LobbyController userCtrl;

    @BeforeEach
    public void setup() {
        repo = new MockMultiUserRepository();
        userCtrl = new LobbyController(repo);
    }

    @Test
    public void databaseIsUsed() {
        userCtrl.add(getUser("username"));
        repo.calledMethods.contains("save");
    }

    private static MultiUser getUser(String username) {
        return new MultiUser(username, 0);
    }


}