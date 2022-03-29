package client.scenes.multi.question;

import javafx.scene.layout.AnchorPane;

public abstract class MultiQuestionScreen {

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
}
