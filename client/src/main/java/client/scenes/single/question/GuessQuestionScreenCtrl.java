package client.scenes.single.question;

import client.scenes.misc.MainCtrl;
import client.scenes.single.QuestionScreen;
import client.services.SingleplayerGameStatePollingService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.misc.GameResponse;
import commons.question.GuessQuestion;
import commons.single.SinglePlayerState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Date;

/**
 * Controller for the guess question scene.
 */
public class GuessQuestionScreenCtrl extends QuestionScreen {

    private GuessQuestion question;

    @FXML
    private AnchorPane window;

    @FXML
    private Label currentScore;

    @FXML
    private ImageView image;

    @FXML
    private Label questionTitle;

    @FXML
    private ProgressBar time;

    @FXML
    private Button leaveButton;

    @FXML
    private Text description;

    @FXML
    private TextField input;

    @FXML
    private Label messageCorrectAnswer;

    /**
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server             is the server variable
     * @param mainCtrl           is the main controller variable
     * @param pollingService     is the injected polling service to be used to poll the game state.
     * @param activityImageUtils is the utilities class responsible for fetching an image of an activity.
     * @param singlePlayerUtils  is the injected singleplayer utils for managing logic
     */
    @Inject
    public GuessQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl,
                                   SingleplayerGameStatePollingService pollingService,
                                   ActivityImageUtils activityImageUtils,
                                   SinglePlayerUtils singlePlayerUtils) {
        super(server, mainCtrl, pollingService, activityImageUtils, singlePlayerUtils);
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
                submitAnswer(input.getText());
                input.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                input.setDisable(true);
            }
        });
    }

    /**
     * Sends a string to the server sa a chosen answer from the player.
     *
     * @param chosenAnswer String value of button clicked - answer chosen
     */
    public void submitAnswer(String chosenAnswer) {
        SinglePlayerState singlePlayerState = singlePlayerUtils.getSinglePlayerState();
        server.postAnswer(new GameResponse(
                singlePlayerState.getId(),
                new Date().getTime(),
                singlePlayerState.getRoundNumber(),
                singlePlayerState.getPlayer().getUsername(),
                chosenAnswer
        ));
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
     * Sets the current score.
     *
     * @param score is the current score of the player
     */
    public void setScore(int score) {
        currentScore.setText(String.valueOf(score));
    }

    /**
     * Setter for the question title.
     */
    public void setQuestionPrompt() {
        questionTitle.setText(question.toString());
    }

    /**
     * Reveal the correct answer.
     * The user will be able in this way to gain information during this game.
     */
    public void setMessageCorrectAnswer() {
        messageCorrectAnswer.setText("The correct answer is " + question.getCorrectAnswer());
        messageCorrectAnswer.setVisible(true);
    }

    /**
     * Sets the current question.
     * Initialises the image, description and input field.
     *
     * @param question GuessQuestion instance to be used.
     */
    public void setQuestion(GuessQuestion question) {
        messageCorrectAnswer.setVisible(false);
        var activityImage = getActivityImage(question.getActivity());
        image.setImage(activityImage);

        this.question = question;
        inputFieldDefault();
        description.setText(question.getActivity().getTitle());
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
     * The method saves the input of the user.
     *
     * @return Input of the user
     */
    public String userInput() {
        String userAnswer = input.getText();
        return userAnswer;
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
     * Overridden getTime() methods. Used to access the private time field.
     *
     * @return Reference to the JavaFX node in the scene corresponding to this controller.
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

}
