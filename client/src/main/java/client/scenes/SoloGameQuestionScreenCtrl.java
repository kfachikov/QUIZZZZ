package client.scenes;

import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Response;
import commons.SinglePlayer;
import commons.SinglePlayerState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.Optional;

public class SoloGameQuestionScreenCtrl {

    public static final String QUESTION_STATE = "QUESTION";
    public static final String TRANSITION_STATE = "TRANSITION";
    public static final String GAME_OVER_STATE = "GAME_OVER";

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final GameStatePollingService pollingService;

    private SinglePlayer singlePlayer = new SinglePlayer("kaloyna", 156);
    private SinglePlayerState singlePlayerState = new SinglePlayerState(15, 51, 15, null, null, null, "hey", singlePlayer);

    /**
     * This event handler would be responsible for the "submit answer clicked" functionality.
     */
    private EventHandler<ActionEvent> handler;

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
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     * @param pollingService GameState polling service
     */
    @Inject
    public SoloGameQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pollingService = pollingService;
    }

//    public void initialize() {
//        // Overriding the `handle` functionality of the
//        handler = event -> {
//            Button chosenAnswer = (Button) event.getSource();
//            System.out.println("AnswerClicked");
//            server.postAnswer(new Response(singlePlayerState.getId(), singlePlayerState.getNextPhase() - new Date().getTime(), singlePlayerState.getRoundNumber(), singlePlayer.getUsername(), chosenAnswer.getText()));
//        };
//
//        pollingService.valueProperty().addListener(((observable, oldGameState, newGameState) -> {
//            if (newGameState != null) {
//                switch (newGameState.getState()) {
//                    case QUESTION_STATE:
//                        // load new question
//                        break;
//                    case TRANSITION_STATE:
//                        // changes background and sets score
//                        break;
//                    case GAME_OVER_STATE:
//                        // goes to congrats screen
//                        break;
//                }
//            }
//        }));
//    }

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
     * @param questionText the question text
     */
    public void setQuestion(Text questionText) {
        question.setText(String.valueOf(questionText));
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
}
