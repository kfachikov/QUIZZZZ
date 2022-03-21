package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.GameStatePollingService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Response;
import commons.question.ConsumptionQuestion;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.Date;
import java.util.Optional;

import static commons.single.SinglePlayerState.*;

public class ConsumptionQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ConsumptionQuestion question;

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
    private ImageView image;

    @FXML
    private Label questionTitle;

    @FXML
    private ProgressBar time;

    @FXML
    private Button leaveButton;

    /**
     * initializes ConsumptionQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public ConsumptionQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl, GameStatePollingService pollingService, ConsumptionQuestion question) {
        this.pollingService = pollingService;
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.question = question;
    }

    /**
     * Initializes the single-player game controller by:
     *
     * Binding answer choices to a method submitting that answer.
     * Initializing a listener for the polling service property `state` which ensures the player is shown the right scene.
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    @SuppressWarnings("checkstyle:Indentation")
    public void initialize() {
        firstAnswer.setDisable(false);
        secondAnswer.setDisable(false);
        thirdAnswer.setDisable(false);
        firstAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(firstAnswer.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                secondAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });
        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(secondAnswer.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                thirdAnswer.setDisable(true);
            }
        });
        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                submitAnswer(thirdAnswer.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                firstAnswer.setDisable(true);
                secondAnswer.setDisable(true);
            }
        });
        pollingService.valueProperty().addListener(((observable, oldGameState, newGameState) -> {
            if (newGameState != null) {
                switch (newGameState.getState()) {
                case QUESTION_STATE:
                    // load new question
                    break;
                case TRANSITION_STATE:
                    setScore(singlePlayerState.getPlayer().getScore());
                    if (compareAnswer()) {
                        window.setStyle("-fx-background-color: #" + (Paint.valueOf("aedd94")).toString().substring(2));
                    } else {
                        window.setStyle("-fx-background-color: #" + (Paint.valueOf("ff8a84")).toString().substring(2));
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
        server.postAnswer(new Response(singlePlayerState.getId(), singlePlayerState.getNextPhase() - new Date().getTime(), singlePlayerState.getRoundNumber(), singlePlayer.getUsername(), chosenAnswer));
        singlePlayerState.addSubmittedAnswer(new Response(singlePlayerState.getId(), singlePlayerState.getNextPhase() - new Date().getTime(), singlePlayerState.getRoundNumber(), singlePlayer.getUsername(), chosenAnswer));
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
        return singlePlayerState.getSubmittedAnswers().get(singlePlayerState.getRoundNumber()).equals(String.valueOf(question.getCorrectAnswer()));
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

    public ConsumptionQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ConsumptionQuestion question) {
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
        Thread thread = new Thread(new ConsumptionQuestionScreenCtrl.BeginThread());
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
