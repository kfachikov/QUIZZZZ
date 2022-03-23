package client.scenes;

import client.utils.MockServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AdministratorScreenCtrlTest {
    private AdministratorScreenCtrl administratorScreenCtrl;
    private MockServerUtils server;
    private MockMainCtrl mainCtrl;

    @BeforeEach
    void setUp() {
        server = new MockServerUtils();
        mainCtrl = new MockMainCtrl();
        administratorScreenCtrl = new AdministratorScreenCtrl(server, mainCtrl);
    }

    @Test
    void returnHome() {
        administratorScreenCtrl.returnHome();
        assertEquals(List.of("showHome"), mainCtrl.calledMethods);
    }
}