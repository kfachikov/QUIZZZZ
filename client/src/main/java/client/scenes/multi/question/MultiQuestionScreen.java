package client.scenes.multi.question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class - parent of all question controllers for the multiplayer game mode.
 * Used for declaring current controller instance, that would be used to alter the background once
 * the transition state comes.
 */
public abstract class MultiQuestionScreen {

    /*
    Shared references to the emoji buttons.
     */
    @FXML
    protected Button emojiButton1;

    @FXML
    protected Button emojiButton2;

    @FXML
    protected Button emojiButton3;

    @FXML
    protected Button emojiButton4;

    @FXML
    protected ImageView surprised;

    @FXML
    protected ImageView laughing;

    @FXML
    protected ImageView angry;

    @FXML
    protected ImageView crying;

    /*
    Shared references to the joker buttons.
     */
    @FXML
    protected Button doublePoints;

    @FXML
    protected Button removeIncorrect;

    @FXML
    protected Button timeAttack;

    /*
    Shared references to the reaction section lines.
     */
    @FXML
    protected GridPane chatMessages;

    @FXML
    private ImageView doubleImage;

    @FXML
    private ImageView timeImage;

    @FXML
    private ImageView wrongImage;


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

    /**
     * Getter for the whole "chat".
     *
     * @return  FlowPane reference to the reactions sections.
     */
    public GridPane getChatMessages() {
        return chatMessages;
    }

    /**
     * Setter for the game jokers.
     */
    public void setJokers() {

        Image shortenTimeImage = new Image(Objects
                .requireNonNull(getClass()
                        .getResourceAsStream("/joker/shortentime.png")));
        Image twicePointsImage = new Image(Objects
                .requireNonNull(getClass()
                        .getResourceAsStream("/joker/twicepoints.png")));
        Image revealWrongImage = new Image(Objects
                .requireNonNull(getClass()
                        .getResourceAsStream("/joker/revealwronganswer.png")));

        doubleImage.setImage(twicePointsImage);
        wrongImage.setImage(revealWrongImage);
        timeImage.setImage(shortenTimeImage);

        doublePoints.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        removeIncorrect.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        timeAttack.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));

        doublePoints.setDisable(false);
        removeIncorrect.setDisable(false);
        timeAttack.setDisable(false);
    }

    /**
     * Disable the double joker.
     */
    public void disableDoublePoints() {
        doublePoints.setDisable(true);
        doublePoints.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
    }

    /**
     * Disables the reveal joker.
     */
    public void disableRemoveIncorrect() {
        removeIncorrect.setDisable(true);
        removeIncorrect.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
    }

    /**
     * Enables the "Remove Incorrect" joker. To be used to enable it after it has been
     * disabled because of player submitting an answer - aim to prevent mistakes.
     */
    public void enableRemoveIncorrect() {
        removeIncorrect.setDisable(false);
        removeIncorrect.setStyle("-fx-background-color: #" + (Paint.valueOf("b80000")).toString().substring(2));
    }

    /**
     * Disables the time joker.
     */
    public void disableTimeAttack() {
        timeAttack.setDisable(true);
        timeAttack.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
    }

    /**
     * Sets the jokers style.
     */
    public void setJokersStyle() {
        doublePoints.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        removeIncorrect.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        timeAttack.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
    }

    /**
     * Getter for the Button instances of corresponding to the answer choices for
     * any of the child classes (controllers) extending this abstract class.
     * *Defines contract.*
     *
     * @return  List of Button instances.
     */
    public abstract List<Button> getAnswerButtons();

    public void setEmojiImages(Image surprised, Image laughing, Image angry, Image crying) {
        this.surprised.setImage(surprised);
        this.laughing.setImage(laughing);
        this.angry.setImage(angry);
        this.crying.setImage(crying);
    }
}
