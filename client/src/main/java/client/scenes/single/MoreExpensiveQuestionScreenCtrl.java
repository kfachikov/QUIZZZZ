package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.question.MoreExpensiveQuestion;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Optional;

public class MoreExpensiveQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private MoreExpensiveQuestion question;


    /*
    Would be used for constant polling of the current game state.
     */
    private final GameStatePollingService pollingService;

    private SinglePlayer singlePlayer;
    private SinglePlayerState singlePlayerState;


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
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public MoreExpensiveQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        this.pollingService = pollingService;
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * sets the scene and title to home if the yes button is clicked.
     */
    public void returnHome() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave the game?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.get() == yesButton) {
            mainCtrl.showHome();
        }

    }

    /**
     * Sets the current score.
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

    public MoreExpensiveQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MoreExpensiveQuestion question) {
        this.question = question;
    }

    class BeginThread implements Runnable {

        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public synchronized void run() {
            time.setStyle("-fx-accent: #006e8c");
            for (int i = 0; i < 100; i++) {
                if (i > 70) {
                    time.setStyle("-fx-accent: red");
                }
                time.setProgress(i / 100.0);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * The method starts the timer thread.
     */
    @FXML
    public synchronized void startTimer() {
        time.setProgress(0.0);
        Thread thread = new Thread(new MoreExpensiveQuestionScreenCtrl.BeginThread());
        thread.start();
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
}
