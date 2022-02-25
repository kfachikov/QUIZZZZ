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

    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void playSolo() {
        mainCtrl.showPrep();
    }

    public void playMulti() {
        //mainCtrl.showMultiLobby();
    }

    public void helpMe() {
        //mainCtrl.showHelp();
    }



}
