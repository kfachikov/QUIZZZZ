package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.PromptLeaveScreen;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Abstract class - parent of all question controllers for the multiplayer game mode.
 * Used for declaring current controller instance, that would be used to alter the background once
 * the transition state comes.
 */
public abstract class MultiQuestionScreen implements PromptLeaveScreen {

    /**
     * Package protected MultiplayerCtrl, to give access only to all children of this class.
     */
    final MultiplayerCtrl multiCtrl;

    /**
     * Constructor for the multiplayer game question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     */
    public MultiQuestionScreen(MultiplayerCtrl multiCtrl) {
        this.multiCtrl = multiCtrl;
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
     * Getter for th progress barr instance in each separate controller.
     *
     * @return ProgressBar reference to the particular instance.
     */
    public abstract ProgressBar getTime();

    public abstract void disableAnswerSubmission();

    public void onLeave() {
        multiCtrl.onLeave();
    }
}
