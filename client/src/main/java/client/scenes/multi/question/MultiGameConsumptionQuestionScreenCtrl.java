package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.ServerUtils;
import commons.question.ConsumptionQuestion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javax.inject.Inject;

/**
 * Controller responsible for the multiplayer game consumption question screen.
 */
public class MultiGameConsumptionQuestionScreenCtrl extends MultiQuestionScreen {

    private final MultiplayerCtrl multiCtrl;
    private final ServerUtils server;

    private ConsumptionQuestion question;

    @FXML
    private Label gameStateLabel;

    @FXML
    private AnchorPane window;

    @FXML
    private ProgressBar time;

    @FXML
    private Label currentScore;

    @FXML
    private ImageView image;

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;

    @FXML
    private Text description;

    @FXML
    private Button twicePoints;

    @FXML
    private Button revealWrong;

    @FXML
    private Button shortenTime;

    @FXML
    private Button emojiButton1;

    @FXML
    private Button emojiButton2;

    @FXML
    private Button emojiButton3;

    @FXML
    private Button emojiButton4;

    /**
     * Constructor for the multiplayer game question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     * @param server is the server variable
     */
    @Inject
    public MultiGameConsumptionQuestionScreenCtrl(MultiplayerCtrl multiCtrl, ServerUtils server) {
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
     * Initializes the multi-player game controller by:
     * <p>
     * Binding answer choices to a method submitting that answer.
     * In addition, proper method is bound to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    public void initialize() {
        firstAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(firstAnswer.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(secondAnswer.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(thirdAnswer.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
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
     * Getter for the current question.
     *
     * @return  ConsumptionQuestion instance.
     */
    public ConsumptionQuestion getQuestion() {
        return question;
    }

    /**
     * Sets the current question. Styles the buttons and enables their clicking.
     * Sets answer choices.
     *
     * @param question  Question to be used - the one that should be asked on this round.
     */
    public void setQuestion(ConsumptionQuestion question) {
        this.question = question;
    }

    /**
     * Prepare the answer field by making them clickable and setting their color to the default one.
     */
    public void prepareAnswerButton() {
        /*
        Enable buttons to be clicked.
         */
        firstAnswer.setDisable(false);
        secondAnswer.setDisable(false);
        thirdAnswer.setDisable(false);

        /*
        Set the buttons colors back to default(unselected).
         */
        firstAnswer.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));
        secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));
        thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));
    }

    /**
     * Set the text for all possible answer choices. These would be the one submitted after
     * an answer is clicked.
     */
    public void setAnswers() {
        firstAnswer.setText(question.getAnswerChoices().get(0) + "Wh");
        secondAnswer.setText(question.getAnswerChoices().get(1) + "Wh");
        thirdAnswer.setText(question.getAnswerChoices().get(2) + "Wh");
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
     * Setter fot the description of the activity which consumption should be guessed.
     */
    public void setDescription() {
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Makes all answers non-clickable. To be used once an answer is clicked.
     */
    private void disableAnswerSubmission() {
        firstAnswer.setDisable(true);
        secondAnswer.setDisable(true);
        thirdAnswer.setDisable(true);
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
}
