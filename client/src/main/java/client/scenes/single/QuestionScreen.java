package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

/*
Parent class used for the shared functionality of the different question screen
controllers.
 */
public abstract class QuestionScreen {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final GameStatePollingService pollingService;

    @FXML
    ProgressBar time = new ProgressBar();

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;


    /**
     * initializes MoreExpensiveQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public QuestionScreen(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        this.pollingService = pollingService;
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets the score of the current scene to the actual player's score.
     *
     * @param score Actual player's score calculated after answer submitted
     *              is compared.
     */
    public abstract void setScore(long score);

    /**
     * Getter for the window objects - used for changing the background.
     *
     * @return A reference to the particular AnchorPane object.
     */
    public abstract AnchorPane getWindow();

    /**
     * Compares the submitted answer with the correct one - both stored in the GameState object.
     *
     * @return Corresponding boolean value.
     */
    public abstract boolean compareAnswer();


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
            firstAnswer.setDisable(false);
            secondAnswer.setDisable(false);
            thirdAnswer.setDisable(false);
        }

    }

}
