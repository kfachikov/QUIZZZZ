package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.ServerUtils;
import commons.question.GuessQuestion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javax.inject.Inject;
import java.util.List;

/**
 * Controller responsible for the multiplayer game guess question screen.
 */
public class MultiGameGuessQuestionScreenCtrl extends MultiQuestionScreen {

    private final MultiplayerCtrl multiCtrl;
    private final ServerUtils server;

    @FXML
    private Label gameStateLabel;

    @FXML
    private AnchorPane window;

    @FXML
    private ProgressBar time;

    @FXML
    private Label currentScore;

    @FXML
    private Text description;

    @FXML
    private ImageView image;

    @FXML
    private TextField input;

    @FXML
    private ImageView doubleImage;

    @FXML
    private ImageView timeImage;

    @FXML
    private ImageView wrongImage;

    @FXML
    private Label messageCorrectAnswer;

    /**
     * Constructor for the multiplayer game question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     * @param server is the server variable
     */
    @Inject
    public MultiGameGuessQuestionScreenCtrl(MultiplayerCtrl multiCtrl, ServerUtils server) {
        this.multiCtrl = multiCtrl;

        this.server = server;
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    public void returnHome() {
        multiCtrl.promptLeave();
    }

    /**
     * Initializes the single-player game controller by:
     * <p>
     * Binding answer choices to a method submitting that answer.
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    @SuppressWarnings("checkstyle:Indentation")
    public void initialize() {
        input.setDisable(false);
        input.setStyle("-fx-background-color: #" + (Color.valueOf("ffffff")).toString().substring(2));

        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(input.getText() + "Wh");
                input.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            }
        });

        multiCtrl.initializeEmojiButtons(emojiButton1, emojiButton2, emojiButton3, emojiButton4);
        multiCtrl.initializeJokerButtons(doublePoints, removeIncorrect, timeAttack);
    }

    /**
     * Sets the current score.
     *
     * @param score is the current score of the player
     */
    public void setScore(long score) {
        currentScore.setText(String.valueOf(score));
    }

    /**
     * Reveal the correct answer.
     * The user will be able in this way to gain information during this game.
     *
     * @param question the current question.
     */
    public void setMessageCorrectAnswer(GuessQuestion question) {
        messageCorrectAnswer.setText("The correct answer is " + question.getCorrectAnswer());
        messageCorrectAnswer.setVisible(true);
    }

    /**
     * Sets the "attributes" of the input field to the default ones.
     */
    public void inputFieldDefault() {
        input.setDisable(false);
        input.setStyle("-fx-background-color: #" + (Color.valueOf("ffffff")).toString().substring(2));
        input.clear();
    }

    /**
     * Setter fot the description of the activity which consumption should be guessed.
     *
     * @param question  Question to be used for the description to be set.
     */
    public void setDescription(GuessQuestion question) {
        messageCorrectAnswer.setVisible(false);
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Setter fot the image of the activity which consumptions should be guessed.
     *
     * @param image Image instance to be set.
     */
    public void setImage(Image image) {
        this.image.setImage(image);
    }

    /**
     * Getter for the window object - used to change the background in MainCtrl.
     *
     * @return AnchorPane object with reference to the particular window of this scene.
     */
    public AnchorPane getWindow() {
        return window;
    }

    /**
     * Getter for the ImageField field.
     *
     * @return  Reference to the ImageView instance of this controller.
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * Getter for the progress bar field of this articular controller.
     *
     * @return  ProgressBar reference.
     */
    @Override
    public ProgressBar getTime() {
        return time;
    }

    /**
     * Makes user input field non-clickable. Thus, answers cannot be submitted anymore.
     */
    @Override
    public void disableAnswerSubmission() {
        input.setDisable(true);
    }

    /**
     * Getter for the game state field. Would represent the id of the current game.
     *
     * @return  The id of the current game.
     */
    public Label getGameStateLabel() {
        return gameStateLabel;
    }

    /**
     * This method exists purely because of the Java inheritance principle.
     *
     * @return  null - this method is actually never being called on any instance
     *          of this class.
     */
    public List<Button> getAnswerButtons() {
        return null;
    }
}
