package client.scenes;

import client.utils.ServerUtils;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.Optional;

public class MultiGameQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private LongProperty gameId;

    @FXML
    private Label gameIdLabel;

    @Inject
    public MultiGameQuestionScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl
    ) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        gameId = new SimpleLongProperty(-1);
    }

    public void initialize() {
        gameId.addListener(((observable, oldValue, newValue) -> {
            gameIdLabel.setText("Game ID: " + newValue);
        }));
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
        if (confirmation.isPresent() && confirmation.get() == yesButton) {
            mainCtrl.showHome();
        }
    }

    public void setGameId(long id) {
        gameId.set(id);
    }
}
