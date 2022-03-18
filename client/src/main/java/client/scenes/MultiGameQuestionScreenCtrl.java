package client.scenes;

import client.utils.ServerUtils;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Controller responsible for the multiplayer game question screen.
 * <p>
 * Currently, this is just a mock scene, containing a leave button and a label
 * showing the current game ID.
 */
public class MultiGameQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final LongProperty gameId;

    @FXML
    private Label gameIdLabel;

    @FXML
    private Text question;

    /**
     * Constructor for the multiplayer game question screen.
     * <p>
     * Initializes the associated gameId.
     *
     * @param server   Injected instance of ServerUtils
     * @param mainCtrl Injected instance of MainCtrl
     */
    @Inject
    public MultiGameQuestionScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl
    ) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        gameId = new SimpleLongProperty(-1);
    }

    /**
     * Adds a listener to the game ID property, so its value is clearly reflected
     * in the label present in the scene.
     * <p>
     * This method is automatically run after the scene is initialized, and
     * relevant fields of the class are set automatically to correct values.
     */
    public void initialize() {
        gameId.addListener(((observable, oldValue, newValue) -> {
            gameIdLabel.setText("Game ID: " + newValue);
        }));
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
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
        if (confirmation.isPresent() && confirmation.get() == yesButton) {
            mainCtrl.showHome();
        }
    }

    /**
     * Setter for the associated game ID.
     *
     * @param id New game ID to set
     */
    public void setGameId(long id) {
        gameId.set(id);
    }

    /**
     * Sets the question to the chosen questionText.
     *
     * @param questionText the question text
     */
    public void setQuestion(Text questionText) {
        question.setText(String.valueOf(questionText));
    }

}
