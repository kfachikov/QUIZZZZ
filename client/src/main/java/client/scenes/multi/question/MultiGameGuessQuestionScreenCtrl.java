package client.scenes.multi.question;

import client.scenes.multi.MultiplayerCtrl;
import client.utils.ServerUtils;
import commons.misc.GameResponse;
import commons.question.GuessQuestion;
import jakarta.ws.rs.core.Response;
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
import javax.inject.Inject;
import java.util.Date;

/**
 * Controller responsible for the multiplayer game guess question screen.
 */
public class MultiGameGuessQuestionScreenCtrl {

    private final MultiplayerCtrl multiCtrl;
    private final ServerUtils server;

    private GuessQuestion question;

    @FXML
    private Label gameStateLabel;

    @FXML
    private AnchorPane window;

    @FXML
    private ProgressBar time;

    @FXML
    private Label currentScore;

    @FXML
    private Label questionTitle;

    @FXML
    private Text description;

    @FXML
    private ImageView image;

    @FXML
    private TextField input;

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
    public MultiGameGuessQuestionScreenCtrl(MultiplayerCtrl multiCtrl, ServerUtils server) {
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
     * Setter for a mock label.
     *
     * @param labelText New value of the label
     */
    public void setGameStateLabelText(String labelText) {
        gameStateLabel.setText(labelText);
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
        input.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));

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
        server.postAnswer(new GameResponse(multiCtrl.getId(),
                new Date().getTime(),
                multiCtrl.getNumber(),
                multiCtrl.getUsername(),
                chosenAnswer.substring(0, chosenAnswer.length() - 2)
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

    public void setQuestionPrompt() {
        questionTitle.setText(question.toString());
    }

    /**
     * Sets the current question.
     *
     * @param question GuessQuestion instance to be used.
     */
    public void setQuestion(GuessQuestion question) {
        this.question = question;
        inputFieldDefault();
        description.setText(question.getActivity().getTitle());
    }

    /**
     * Sets the "attributes" of the input field to the default ones.
     */
    public void inputFieldDefault() {
        input.setDisable(false);
        input.setStyle("-fx-background-color: #" + (Color.valueOf("c9f1fd")).toString().substring(2));
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

}
