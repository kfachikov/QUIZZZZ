package client.scenes;

import client.scenes.misc.AdministratorScreenCtrl;
import client.utils.MockServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdministratorScreenCtrlTest {
    private AdministratorScreenCtrl administratorScreenCtrl;
    private MockServerUtils server;
    private MockMainCtrl mainCtrl;

    @BeforeEach
    void setUp() {
        server = new MockServerUtils();
        mainCtrl = new MockMainCtrl();

        // TODO: create mock class for ActivityImageUtils
        administratorScreenCtrl = new AdministratorScreenCtrl(server, mainCtrl, null, null);
    }

    @Test
    void returnHome() {
        administratorScreenCtrl.returnHome();
        assertEquals(List.of("showHome"), mainCtrl.calledMethods);
    }
}