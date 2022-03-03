package server.api;

import commons.SingleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    private MockUserRepository repo;
    private UserController userCtrl;

    @BeforeEach
    public void setup() {
        repo = new MockUserRepository();
        userCtrl = new UserController(repo);
    }

    @Test
    public void databaseIsUsed() {
        userCtrl.add(getUser("username"));
        repo.calledMethods.contains("save");
    }

    private static SingleUser getUser(String username) {
        return new SingleUser(username, 0);
    }

}