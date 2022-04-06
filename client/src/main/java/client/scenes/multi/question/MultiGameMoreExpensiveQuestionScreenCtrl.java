package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.ServerUtils;
import commons.question.MoreExpensiveQuestion;
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
import java.util.List;

/**
 * Controller responsible for the multiplayer game more expensive question screen.
 */
public class MultiGameMoreExpensiveQuestionScreenCtrl extends MultiQuestionScreen {

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
    private Label questionTitle;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private Text description1;

    @FXML
    private Text description2;

    @FXML
    private Text description3;

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;

    @FXML
    private ImageView doubleImage;

    @FXML
    private ImageView timeImage;

    @FXML
    private ImageView wrongImage;

    private MoreExpensiveQuestion question;

    /**
     * Constructor for the multiplayer game more expensive question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     * @param server is the server variable
     */
    @Inject
    public MultiGameMoreExpensiveQuestionScreenCtrl(MultiplayerCtrl multiCtrl, ServerUtils server) {
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
                multiCtrl.submitAnswer(description1.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(description2.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(description3.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
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
     * Set the description for all possible answer choices. These would be the one submitted after
     * an answer is clicked.
     *
     * @param question  Question to be used for the answer choices' descriptions to be set.
     */
    public void setAnswerDescriptions(MoreExpensiveQuestion question) {
        /*
        As the `submitAsnwer` method is extracted in multiplayerCtrl, and it concatenates the last two
        characters of our String answer submitted, we would add two space characters at the end,
        so the comparing method works as intended.
         */
        description1.setText(question.getAnswerChoices().get(0).getTitle() + "  ");
        description2.setText(question.getAnswerChoices().get(1).getTitle() + "  ");
        description3.setText(question.getAnswerChoices().get(2).getTitle() + "  ");
    }

    /**
     * Sets all images corresponding to the answer choices.
     *
     * @param image1    First image to be shown.
     * @param image2    Second image to be shown.
     * @param image3    Third image to be shown.
     */
    public void setAnswerImages(Image image1, Image image2, Image image3) {
        this.image1.setImage(image1);
        this.image2.setImage(image2);
        this.image3.setImage(image3);
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
    public void setQuestion(MoreExpensiveQuestion question) {
        this.question = question;
    }

    /**
     * Getter for the Button instances of corresponding to the answer choices for this controller.
     *
     * @return  List of Button instances.
     */
    public List<Button> getAnswerButtons() {
        return List.of(firstAnswer, secondAnswer, thirdAnswer);
    }

    /**
     * Getter for the descriptions defining the answers.
     *
     * @return  List of String instances.
     */
    public List<String> getDescriptions() {
        return List.of(description1.getText(), description2.getText(), description3.getText());
    }

    /**
     * Change the color of the button that has the correct answer into green.
     * The user will be able in this way to gain information during this game.
     */
    public void showCorrectAnswer() {
        if (description1.getText().equals(question.getCorrectAnswer() + "  ")) {
            firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }
        if (description2.getText().equals(question.getCorrectAnswer() + "  ")) {
            secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }
        if (description3.getText().equals(question.getCorrectAnswer() + "  ")) {
            thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }

    }
}
