package client.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Interface for handing prompting and leaving of the game for screens.
 * <p>
 * Screen controllers which want to prompt the user when leaving should implement this interface.
 * <p>
 * Additionally, the method promptLeave can usually be directly connected to the "Leave" button given proper
 * implementation of this interface.
 */
public interface PromptLeaveScreen {
    /**
     * Clean up when leaving a screen.
     * <p>
     * Should handle the switching of the screen too.
     */
    void onLeave();

    /**
     * Prompt the user if they really want to leave.
     * <p>
     * If the user selects no, nothing happens.
     * <p>
     * If the user selects yes, onLeave is called.
     */
    default void promptLeave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == yesButton) {
            onLeave();
        }
    }
}
