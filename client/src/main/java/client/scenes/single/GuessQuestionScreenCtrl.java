package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Response;
import commons.question.GuessQuestion;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Date;

public class GuessQuestionScreenCtrl extends QuestionScreen {

    private GuessQuestion question;
    private SinglePlayer singlePlayer;
    private SinglePlayerState singlePlayerState;

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

    /**
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public GuessQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        super(server, mainCtrl, pollingService);
    }


    /*
    The following method should be re-written once the questions are generated and
    decision on how to control the different scenes is taken.
     */
    /**
     * Comparison of submitted answer and actual correct one.
     * Both could be accessed through the singlePlayerState instance
     *
     * @return Boolean value whether the answer is correct or not.
     */
    public boolean compareAnswer() {
        //        if(singlePlayerState.getSubmittedAnswers().get(singlePlayerState.getRoundNumber()).equals(String.valueOf(question.getCorrectAnswer())))
        //            return true;
        //        else
        //            return false;
        return true;
    }

    /**
     * Initializes the single-player game controller by:
     *
     * Binding answer choices to a method submitting that answer.
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    @SuppressWarnings("checkstyle:Indentation")
    public void initialize() {
        input.setDisable(false);
        input.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));

        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
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
        server.postAnswer(new Response(singlePlayerState.getId(), singlePlayerState.getNextPhase() - new Date().getTime(), singlePlayerState.getRoundNumber(), singlePlayer.getUsername(), chosenAnswer));
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

    public void setQuestionPrompt() {
        questionTitle.setText(question.toString());
    }

    public void setQuestion(GuessQuestion question) {
        this.question = question;
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
     * Getter for polling service which keeps the state of the current game up to date
     * by "constantly" polling it from the server.
     *
     * @return GameState polling service
     */
    public GameStatePollingService getPollingService() {
        return pollingService;
    }

    /**
     * Getter for the player current player instance.
     *
     * @return SinglePlayer instance containing the username and the score of the current client.
     */
    public SinglePlayer getSinglePlayer() {
        return singlePlayer;
    }

    /**
     * Setter for single-player field - stores the username and the score of our client.
     *
     * @param singlePlayer a SinglePlayer instance containing the above-mentioned information.
     */
    public void setSinglePlayer(SinglePlayer singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /**
     * Getter fot the current state of the game.
     *
     * @return SinglePlayerState instance containing information about the current game.
     */
    public SinglePlayerState getSinglePlayerState() {
        return singlePlayerState;
    }

    /**
     * Setter for the game state field. Would be used later to allow the client submit answers, to check correctness,
     * and to fetch new questions.
     *
     * @param singlePlayerState SinglePlayerState instance - would be returned from the server
     *                          on the initial initialization of the game
     */
    public void setSinglePlayerState(SinglePlayerState singlePlayerState) {
        this.singlePlayerState = singlePlayerState;
    }

    /**
     * Getter for the window object - used to change the background in MainCtrl.
     * @return AnchorPane object with reference to the particular window of this scene.
     */
    public AnchorPane getWindow() {
        return window;
    }

    /**
     * Overridden getTime() methods. Used to access the private time field.
     *
     * @return  Reference to the JavaFX node in the scene corresponding to this controller.
     */
    @Override
    public ProgressBar getTime() {
        return time;
    }
}
