package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Optional;

public class GuessQuestionScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    /**
     * initializes SoloGameQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     * @param server is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public GuessQuestionScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        Thread thread = new Thread(new GuessQuestionScreenCtrl.BeginThread());
        thread.start();
    }

    /**
     * Turns the first circle black.
     */
    public void turnBlack1() {
        circle1.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("#000000")).toString().substring(2));
    }

    /**
     * Turns the second circle black.
     */
    public void turnBlack2() {
        circle2.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("#000000")).toString().substring(2));
    }

    /**
     * Turns the third circle black.
     */
    public void turnBlack3() {
        circle3.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("#000000")).toString().substring(2));
    }
}
