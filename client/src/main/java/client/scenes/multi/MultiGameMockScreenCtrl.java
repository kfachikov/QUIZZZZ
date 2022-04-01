package client.scenes.multi;

import client.utils.PromptLeaveScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

/**
 * Controller responsible for the multiplayer game question screen.
 * <p>
 * Currently, this is just a mock scene, containing a leave button and a label
 * showing the current game ID.
 */
public class MultiGameMockScreenCtrl implements PromptLeaveScreen {

    private final MultiplayerCtrl multiCtrl;

    @FXML
    private Label gameStateLabel;

    /**
     * Constructor for the multiplayer game screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     */
    @Inject
    public MultiGameMockScreenCtrl(MultiplayerCtrl multiCtrl) {
        this.multiCtrl = multiCtrl;
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    public void returnHome() {
        promptLeave();
    }

    /**
     * Clean up when leaving a screen.
     * <p>
     * Should handle the switching of the screen too.
     */
    @Override
    public void onLeave() {
        multiCtrl.onLeave();
    }

    /**
     * Setter for a mock label.
     *
     * @param labelText New value of the label
     */
    public void setGameStateLabelText(String labelText) {
        gameStateLabel.setText(labelText);
    }
}
