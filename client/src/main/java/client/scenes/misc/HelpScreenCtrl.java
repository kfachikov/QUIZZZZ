package client.scenes.misc;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 */
public class HelpScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private Button next;

    @FXML
    private Button goBack;

    @FXML
    private Text title;

    /**
     * initializes HelpScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public HelpScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Sets the help screen to tab-2.
     */
    public void helpOne() {
        //button actions
        goBack.setOnAction(event -> mainCtrl.showHome());
        next.setOnAction(event -> helpTwo());
        //circle colors
        //circle2.setStyle();
        //title+text
    }

    /**
     * Sets the help screen to tab-2.
     */
    public void helpTwo() {
        //set screen 2
        title.setText("MULTIPLAYER");
        goBack.setOnAction(event -> helpOne());
        next.setOnAction(event -> helpThree());
        //circle colors
        //title+text
    }

    /**
     * Sets the help screen to tab-3.
     */
    public void helpThree() {
        title.setText("JOKER CARDS");
        goBack.setOnAction(event -> helpTwo());
        next.setText("Return Home");
        next.setOnAction(event -> mainCtrl.showHome());
        helpOne();
        //circle colors
        //title+text
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }
}
