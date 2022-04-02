package client.scenes.multi.question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Abstract class - parent of all question controllers for the multiplayer game mode.
 * Used for declaring current controller instance, that would be used to alter the background once
 * the transition state comes.
 */
public abstract class MultiQuestionScreen {

    private boolean reveal;
    private boolean halfTime;

    private boolean doublePoints;

    @FXML
    private Button twicePoints;

    @FXML
    private Button revealWrong;

    @FXML
    private Button shortenTime;

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

    public abstract void disableAnswerSubmission();

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
     * @param doublePoints the boolean value for the joker regarding the points.
     */
    public void setDoublePoints(boolean doublePoints) {
        this.doublePoints = doublePoints;
    }


    public boolean getDoublePoints() {
        return doublePoints;
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

        twicePoints.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        revealWrong.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        shortenTime.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));

        twicePoints.setDisable(false);
        revealWrong.setDisable(false);
        shortenTime.setDisable(false);

        setHalfTime(false);
        setReveal(false);
        setDoublePoints(false);

    }

    /**
     * Disable the double joker.
     */
    public void disableDoublePoint() {
        twicePoints.setDisable(true);
    }

    /**
     * Disables the reveal joker.
     */
    public void disableReveal() {
        revealWrong.setDisable(true);
    }

    /**
     * Disables the time joker.
     */
    public void disableShortenTime() {
        shortenTime.setDisable(true);
    }

}
