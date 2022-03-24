package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javax.inject.Inject;

/**
 * Controller responsible for the multiplayer game question screen.
 * <p>
 * Currently, this is just a mock scene, containing a leave button and a label
 * showing the current game ID.
 */
public class MultiGameQuestionDScreenCtrl {
    private final MultiplayerCtrl multiCtrl;

    @FXML
    private Label gameIdLabel;

    @FXML
    private Text question;

    /**
     * Constructor for the multiplayer game question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     */
    @Inject
    public MultiGameQuestionDScreenCtrl(MultiplayerCtrl multiCtrl) {
        this.multiCtrl = multiCtrl;
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    public void returnHome() {
        multiCtrl.promptReturn();
    }

    /**
     * Setter for the associated game ID label.
     *
     * @param labelText New value of the label
     */
    public void setGameIdLabelText(String labelText) {
        gameIdLabel.setText(labelText);
    }

}
