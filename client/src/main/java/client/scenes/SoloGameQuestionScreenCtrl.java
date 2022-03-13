package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;



public class SoloGameQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label currentScore;

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;

    @FXML
    private ImageView imageQuestion;

    @FXML
    private Text question;

    @FXML
    private ProgressBar time;

    @FXML
    private Button leaveButton;

    @FXML
    private Label questionTitle;



    /**
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public SoloGameQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * sets the scene and title to home if the yes button is clicked.
     */
    public void returnHome() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave the game?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.get() == yesButton) {
            mainCtrl.showHome();
        } else {
            mainCtrl.showSoloGameQuestion();
        }
    }

    /**
     * sets the current score.
     * @param score is the current score of the player
     */
    public void setScore(int score) {
        currentScore.setText(String.valueOf(score));
    }
}
