package client.scenes.single.question;

import client.scenes.misc.MainCtrl;
import client.scenes.single.QuestionScreen;
import client.services.SingleplayerGameStatePollingService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.misc.GameResponse;
import commons.question.InsteadQuestion;
import commons.single.SinglePlayerState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Date;

/**
 * Controller for the instead question scene.
 */
public class InsteadQuestionScreenCtrl extends QuestionScreen {

    private InsteadQuestion question;

    @FXML
    private AnchorPane window;

    @FXML
    private Label currentScore;

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;

    @FXML
    private ImageView image;

    @FXML
    private ProgressBar time;

    @FXML
    private Button leaveButton;

    @FXML
    private Label questionTitle;

    @FXML
    private Text description;

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
    public InsteadQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl,
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
    public void initialize() {
        firstAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                submitAnswer(firstAnswer.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });
        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                submitAnswer(secondAnswer.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });
        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                submitAnswer(thirdAnswer.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
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
     * Sets the question to the chosen questionText.
     */
    public void setQuestionPrompt() {
        questionTitle.setText(question.toString());
    }

    /**
     * Sets the description of the image.
     */
    public void setDescription() {
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Getter for the question instance.
     *
     * @return this question.
     */
    public InsteadQuestion getQuestion() {
        return question;
    }

    /**
     * Sets the question and the corresponding fields with proper information.
     *
     * @param question Question to be visualized on the particular scene.
     */
    public void setQuestion(InsteadQuestion question) {

        image.setImage(getActivityImage(question.getActivity()));

        firstAnswer.setDisable(false);
        secondAnswer.setDisable(false);
        thirdAnswer.setDisable(false);

        //setting the button colors back to default(unselected)
        firstAnswer.setStyle("-fx-background-color: #" + (Color.valueOf("b80000")).toString().substring(2));
        secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("b80000")).toString().substring(2));
        thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("b80000")).toString().substring(2));

        this.question = question;
        setQuestionPrompt();
        setDescription();
        /*
        The following setup was made purely for testing purposes.
        Should be optimized - extracted as functionality (eventually).
         */
        firstAnswer.setText(question.getAnswerChoices().get(0).getTitle());
        secondAnswer.setText(question.getAnswerChoices().get(1).getTitle());
        thirdAnswer.setText(question.getAnswerChoices().get(2).getTitle());
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
     * Change the color of the button that has the correct answer into green.
     * The user will be able in this way to gain information during this game.
     */
    public void showCorrectAnswer() {
        if (firstAnswer.getText().equals(question.getCorrectAnswer())) {
            firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }
        if (secondAnswer.getText().equals(question.getCorrectAnswer())) {
            secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }
        if (thirdAnswer.getText().equals(question.getCorrectAnswer())) {
            thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("32cd32")).toString().substring(2));
        }

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

}
