package client.scenes.single;
import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Response;
import commons.question.MoreExpensiveQuestion;
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

public class MoreExpensiveQuestionScreenCtrl extends QuestionScreen {

    private MoreExpensiveQuestion question;
    private SinglePlayer singlePlayer;

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
    private ProgressBar time;

    @FXML
    private Button leaveButton;

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

    /**
<<<<<<< HEAD
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
=======
     * initializes MoreExpensiveQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
>>>>>>> 100-integrate-the-solo-game
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public MoreExpensiveQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        super(server, mainCtrl, pollingService);
    }

    /**
     * Initializes the single-player game controller by:
     *
     * Binding answer choices to a method submitting that answer.
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    @SuppressWarnings("checkstyle:Indentation")
    public void initialize() {

        firstAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(firstAnswer.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });

        image1.setOnMouseClicked(e -> {
            submitAnswer(firstAnswer.getText());
            firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

        description1.setOnMouseClicked(e -> {
            submitAnswer(firstAnswer.getText());
            firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(secondAnswer.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });

        image2.setOnMouseClicked(e -> {
            submitAnswer(secondAnswer.getText());
            secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

        description2.setOnMouseClicked(e -> {
            submitAnswer(secondAnswer.getText());
            secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(thirdAnswer.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });

        image3.setOnMouseClicked(e -> {
            submitAnswer(thirdAnswer.getText());
            thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

        description3.setOnMouseClicked(e -> {
            submitAnswer(thirdAnswer.getText());
            thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
            firstAnswer.setDisable(true);
            secondAnswer.setDisable(true);
            thirdAnswer.setDisable(true);
        });

    }

    /**
     * Sends a string to the server sa a chosen answer from the player.
     *
     * @param chosenAnswer String value of button clicked - answer chosen
     */
    public void submitAnswer(String chosenAnswer) {}

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
        return true;
        //return singlePlayerState.getSubmittedAnswers().get(singlePlayerState.getRoundNumber()).equals(String.valueOf(question.getCorrectAnswer()));
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
     * Sets the question to the chosen questionText.
     */
    public void setQuestionPrompt() {
        questionTitle.setText(question.toString());
    }


    /**
     * Sets the question and the corresponding fields with proper information.
     * @param question Question to be visualized on the particular scene.
     */
    public void setQuestion(MoreExpensiveQuestion question) {
        firstAnswer.setDisable(false);
        secondAnswer.setDisable(false);
        thirdAnswer.setDisable(false);

        //setting the button colors back to default(unselected)
        firstAnswer.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));
        secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));
        thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));


        this.question = question;
        setQuestionPrompt();
        /*
        The following setup was made purely for testing purposes.
        Should be optimized - extracted as functionality (eventually).
         */
        firstAnswer.setText(question.getAnswerChoices().get(0).getTitle());
        secondAnswer.setText(question.getAnswerChoices().get(1).getTitle());
        thirdAnswer.setText(question.getAnswerChoices().get(2).getTitle());
    }


    public MoreExpensiveQuestion getQuestion() {
        return question;
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
