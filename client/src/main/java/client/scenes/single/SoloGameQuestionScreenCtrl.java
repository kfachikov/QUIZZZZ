package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Response;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.Optional;

public class SoloGameQuestionScreenCtrl {

    public static final String QUESTION_STATE = "QUESTION";
    public static final String TRANSITION_STATE = "TRANSITION";
    public static final String GAME_OVER_STATE = "GAME_OVER";

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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
    private ImageView imageQuestion;

    @FXML
    private Text question;

    @FXML
    private ProgressBar time;

    @FXML
    private Button leaveButton;

    @FXML
    private Label questionTitle;

    /**
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server         is the server variable
     * @param mainCtrl       is the main controller variable
     * @param pollingService GameState polling service
     */
    @Inject
    public SoloGameQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pollingService = pollingService;
    }

    /**
     * Initializes the single-player game controller by:
     * <p>
     * Binding answer choices to a method submitting that answer.
     * Initializing a listener for the polling service property `state` which ensures the player is shown the right scene.
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    public void initialize() {
        firstAnswer.setOnAction((event -> submitAnswer(firstAnswer.getText())));
        secondAnswer.setOnAction((event -> submitAnswer(secondAnswer.getText())));
        thirdAnswer.setOnAction((event -> submitAnswer(thirdAnswer.getText())));

        pollingService.valueProperty().addListener(((observable, oldGameState, newGameState) -> {
            if (newGameState != null) {
                switch (newGameState.getState()) {
                    case QUESTION_STATE:
                        // load new question
                        break;
                    case TRANSITION_STATE:
                        setScore(singlePlayerState.getPlayer().getScore());
                        if (compareAnswer()) {
                            window.setStyle("-fx-background-color: #" +
                                    (Paint.valueOf("aedd94")).toString().substring(2));
                        } else {
                            window.setStyle("-fx-background-color: #" +
                                    (Paint.valueOf("ff8a84")).toString().substring(2));
                        }
                        break;
                    case GAME_OVER_STATE:
                        // goes to congrats screen
                        break;
                }
            }
        }));
    }

    /**
     * Sends a string to the server sa a chosen answer from the player.
     *
     * @param chosenAnswer String value of button clicked - answer chosen
     */
    public void submitAnswer(String chosenAnswer) {
        server.postAnswer(new Response(singlePlayerState.getId(),
                singlePlayerState.getNextPhase() - new Date().getTime(),
                singlePlayerState.getRoundNumber(), singlePlayer.getUsername(), chosenAnswer));
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
        return true;
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
     *
     * @param score is the current score of the player
     */
    public void setScore(long score) {
        currentScore.setText(String.valueOf(score));
    }

    /**
     * Sets the question to the chosen questionText.
     *
     * @param questionText the question text
     */
    public void setQuestion(Text questionText) {
        questionTitle.setText(String.valueOf(questionText));
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
        Thread thread = new Thread(new BeginThread());
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
