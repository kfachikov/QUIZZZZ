package client.scenes.single;

import javafx.scene.layout.AnchorPane;

/*
Interface used for the shared functionality of the different question screen
controllers.
 */
public interface QuestionScreen {

    /**
     * Sets the score of the current scene to the actual player's score.
     *
     * @param score Actual player's score calculated after answer submitted
     *              is compared.
     */
    public void setScore(long score);

    /**
     * Getter for the window objects - used for changing the background.
     *
     * @return A reference to the particular AnchorPane object.
     */
    public AnchorPane getWindow();

    /**
     * Compares the submitted answer with the correct one - both stored in the GameState object.
     *
     * @return Corresponding boolean value.
     */
    public boolean compareAnswer();
}
