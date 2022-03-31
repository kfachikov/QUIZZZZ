package client.scenes.multi.question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Abstract class - parent of all question controllers for the multiplayer game mode.
 * Used for declaring current controller instance, that would be used to alter the background once
 * the transition state comes.
 */
public abstract class MultiQuestionScreen {



    @FXML
    protected Button emojiButton1;

    @FXML
    protected Button emojiButton2;

    @FXML
    protected Button emojiButton3;

    @FXML
    protected Button emojiButton4;

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
     * @return  ProgressBar reference to the particular instance.
     */
    public abstract ProgressBar getTime();

    /**
     * Abstract method "contract" to disable the answer submission process.
     */
    public abstract void disableAnswerSubmission();
}
