package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField username;

    @FXML
    private TextField serverURL;

    /**
     *
     * @param server
     * @param mainCtrl
     * initializes HomeScreenCtrl by connecting it to backend and frontend mainCtrl
     */
    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * runs showHelp() function
     */
    public void helpMe() {
        mainCtrl.showHelp();
    }

    public void playSolo() {
        //mainCtrl.showSoloIntermediate();
    }

    public void playMulti() {
        //mainCtrl.showMultiLobby();
    }

}
