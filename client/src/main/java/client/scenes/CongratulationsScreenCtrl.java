package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CongratulationsScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Text position;

    @FXML
    private Text points;

    @FXML
    private Circle question1;

    @FXML
    private Circle question2;

    @FXML
    private Circle question3;

    @FXML
    private Circle question4;

    @FXML
    private Circle question5;

    @FXML
    private Circle question6;

    @FXML
    private Circle question7;

    @FXML
    private Circle question8;

    @FXML
    private Circle question9;

    @FXML
    private Circle question10;

    @FXML
    private Circle question11;
    @FXML
    private Circle question12;
    @FXML
    private Circle question13;

    @FXML
    private Circle question14;

    @FXML
    private Circle question15;

    @FXML
    private Circle question16;

    @FXML
    private Circle question17;

    @FXML
    private Circle question18;

    @FXML
    private Circle question19;

    @FXML
    private Circle question20;

    /**
     * initializes CongratulationsScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public CongratulationsScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * sets the scene and title to single-player game.
     */
    public void playSoloGame() {
        mainCtrl.showSoloGameQuestion();
    }

    /**
     * sets the title and scene to answer reveal.
     */
    public void revealAnswer() {
        var id = -1;
        for (int i = 0; i <= 20; i++) {
            if (i == id) {
                mainCtrl.showSoloGameQuestion();
            }
        }
    }
}
