package client.scenes.multi.question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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

    private boolean reveal;
    private boolean halfTime;

    private boolean doublePointsPlayer;

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
     * Getter for the reveal joker checker.
     *
     * @return true/false.
     */
    public boolean getReveal() {
        return this.reveal;
    }

    /**
     * Setter for the reveal joker checker.
     *
     * @param reveal true/false.
     */
    public void setReveal(boolean reveal) {
        this.reveal = reveal;
    }

    /**
     * getter for the halfTime.
     *
     * @return true/false.
     */
    public boolean getHalfTime() {
        return this.halfTime;
    }

    /**
     * sets the halfTime.
     *
     * @param halfTime true/false.
     */
    public void setHalfTime(boolean halfTime) {
        this.halfTime = halfTime;
    }

    /**
     * Setter for the doublePoints joker.
     *
     * @param doublePointsPlayer the boolean value for the joker regarding the points.
     */
    public void setDoublePointsPlayer(boolean doublePointsPlayer) {
        this.doublePointsPlayer = doublePointsPlayer;
    }


    /**
     * getter for the doublePoints.
     *
     * @return true/false
     */
    public boolean getDoublePointsPlayer() {
        return doublePointsPlayer;
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

        setHalfTime(false);
        setReveal(false);
        setDoublePointsPlayer(false);

    }

    /**
     * Disable the double joker.
     */
    public void disableDoublePoint() {
        doublePoints.setDisable(true);
    }

    /**
     * Disables the reveal joker.
     */
    public void disableReveal() {
        removeIncorrect.setDisable(true);
    }

    /**
     * Disables the time joker.
     */
    public void disableShortenTime() {
        timeAttack.setDisable(true);
    }

    /**
     * Sets the jokers style.
     */
    public void setJokersStyle() {

        doublePoints.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        removeIncorrect.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        timeAttack.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));

    }
}
