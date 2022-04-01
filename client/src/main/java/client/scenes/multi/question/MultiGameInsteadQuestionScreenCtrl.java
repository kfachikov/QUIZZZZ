package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.ServerUtils;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.question.InsteadQuestion;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller responsible for the multiplayer game instead question screen.
 */
public class MultiGameInsteadQuestionScreenCtrl extends MultiQuestionScreen {

    private final MultiplayerCtrl multiCtrl;
    private final ServerUtils server;

    private InsteadQuestion question;

    @FXML
    private Label gameStateLabel;

    @FXML
    private AnchorPane window;

    @FXML
    private ProgressBar time;

    @FXML
    private Label currentScore;

    @FXML
    private ImageView image;

    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;

    @FXML
    private Text description;

    @FXML
    private Button twicePoints;

    @FXML
    private Button revealWrong;

    @FXML
    private Button shortenTime;

    @FXML
    private Button emojiButton1;

    @FXML
    private Button emojiButton2;

    @FXML
    private Button emojiButton3;

    @FXML
    private Button emojiButton4;

    /**
     * Constructor for the multiplayer game question screen.
     *
     * @param multiCtrl Injected instance of MultiplayerCtrl
     * @param server is the server variable
     */
    @Inject
    public MultiGameInsteadQuestionScreenCtrl(MultiplayerCtrl multiCtrl, ServerUtils server) {
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
     * In addition, proper method is binded to the buttons, so that when clicked, they submit the answer chosen to the server.
     */
    public void initialize() {
        firstAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(firstAnswer.getText());
                firstAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        secondAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(secondAnswer.getText());
                secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
        thirdAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                multiCtrl.submitAnswer(thirdAnswer.getText());
                thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("ffb70b")).toString().substring(2));
                disableAnswerSubmission();
            }
        });
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
     * Getter for the current question to be asked.
     *
     * @return  InsteadQuestion instance.
     */
    public InsteadQuestion getQuestion() {
        return question;
    }

    /**
     * Sets the current question. Sets the answer choices.
     *
     * @param question  Question to be asked is passed as argument.
     */
    public void setQuestion(InsteadQuestion question) {
        this.question = question;
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
        firstAnswer.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));
        secondAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));
        thirdAnswer.setStyle("-fx-background-color: #" + (Paint.valueOf("c9f1fd")).toString().substring(2));
    }


    /**
     * Set the text for all possible answer choices. These would be the one submitted after
     * an answer is clicked.
     */
    public void setAnswers() {
        /*
        As the `submitAsnwer` method is extracted in multiplayerCtrl, and it concatenates the last two
        characters of our String answer submitted, we would add two space characters at the end,
        so the comparing method works as intended.
         */
        firstAnswer.setText(question.getAnswerChoices().get(0).getTitle() + "  ");
        secondAnswer.setText(question.getAnswerChoices().get(1).getTitle() + "  ");
        thirdAnswer.setText(question.getAnswerChoices().get(2).getTitle() + "  ");
    }

    /**
     * Setter fot the image of the activity which consumptions should be guessed.
     *
     * @param image Image instance to be set.
     */
    public void setImage(Image image) {
        this.image.setImage(image);
    }


    /**
     * Setter fot the description of the activity which consumption should be guessed.
     */
    public void setDescription() {
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Makes all answers non-clickable. To be used once an answer is clicked.
     */
    private void disableAnswerSubmission() {
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
     * Getter for the ImageField field.
     *
     * @return  Reference to the ImageView instance of this controller.
     */
    public ImageView getImage() {
        return image;
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

    public void startJokerPolling() {
        Timer pollingService = new Timer();
        pollingService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MultiPlayer player = server.getTimeJokerPlayer(multiCtrl.getId());
                if (player != null) {
                    if (!getPlayer().equals(player)) {
                        reduceTime();
                    }
                    disableTimeJoker();
                    pollingService.cancel();
                }
            }
        }, 0, 250);
    }

    public MultiPlayer getPlayer() {
        MultiPlayerState multiPlayerState = ServerUtils.getMultiGameState(multiCtrl.getId());
        return multiPlayerState.getPlayerByUsername(multiCtrl.getUsername());
    }

    public void sendJoker() {
        if (getPlayer().getTimeJoker()) {
            server.postTimeJokerPlayer(getPlayer(), multiCtrl.getId());
            disableTimeJoker();
        }
    }

    public void reduceTime() {
        //method that will be implemented in the front-end
    }

    public void disableTimeJoker() {
        //method that will be implemented in the front-end
    }

}
