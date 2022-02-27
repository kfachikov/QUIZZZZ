package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.SingleUser;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

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
        try {
            server.addUser(getUser());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        mainCtrl.showPrep();
    }

    public SingleUser getUser() {
        String user = username.getText();
        return new SingleUser(user, 0);
    }

    public void playMulti() {
        //mainCtrl.showMultiLobby();
    }

}
