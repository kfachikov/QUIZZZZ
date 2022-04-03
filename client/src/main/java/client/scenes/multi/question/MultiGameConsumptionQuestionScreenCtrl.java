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

    private MultiplayerCtrl multiCtrl;
    private final ServerUtils server;
    private boolean reveal;
    private boolean halfTime;
    private boolean doublePoints;

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

    @FXML
    private ImageView doubleImage;

    @FXML
    private ImageView timeImage;

    @FXML
    private ImageView wrongImage;

    private ConsumptionQuestion question;

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
        multiCtrl.initializeEmojiButtons(emojiButton1, emojiButton2, emojiButton3, emojiButton4);


        twicePoints.setOnAction(e -> {

            twicePoints.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));

            setDoublePoints(true);
            //twicePoints.setDisable(true);


        });

        revealWrong.setOnAction(e -> {

            revealWrong.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            setReveal(true);
            if (!question.getAnswerChoices().get(0).toString().equals(question.getCorrectAnswer())) {
                firstAnswer.setDisable(true);
            } else if (!question.getAnswerChoices().get(1).toString().equals(question.getCorrectAnswer())) {
                secondAnswer.setDisable(true);
            } else if (!question.getAnswerChoices().get(2).toString().equals(question.getCorrectAnswer())) {
                thirdAnswer.setDisable(true);
            }
            //revealWrong.setDisable(true);
        });

        shortenTime.setOnAction(e -> {

            shortenTime.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            //sendJoker();
            setHalfTime(true);
            //shortenTime.setDisable(true);
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
        firstAnswer.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("b80000")).toString().substring(2));
        thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("b80000")).toString().substring(2));
    }

    /**
     * Set the text for all possible answer choices. These would be the one submitted after
     * an answer is clicked.
     *
     * @param question  Question to be used for the answer choices to be set.
     */
    public void setAnswers(ConsumptionQuestion question) {
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
     *
     * @param question  Question to be used for the description to be set.
     */
    public void setDescription(ConsumptionQuestion question) {
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Makes all answers non-clickable. To be used once an answer is clicked.
     */
    @Override
    public void disableAnswerSubmission() {
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
     * Getter for the game state field. Would represent the id of the current game.
     *
     * @return  The id of the current game.
     */
    public Label getGameStateLabel() {
        return gameStateLabel;
    }

    /**
     * question setter.
     *
     * @param question the question
     */
    public void setQuestion(ConsumptionQuestion question) {
        this.question = question;
    }
}
