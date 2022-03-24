package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.question.MultiGameQuestionAScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionBScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionCScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionDScreenCtrl;
import client.utils.ServerUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Class responsible for managing the multiplayer game for the client.
 *
 * It keeps track of any logical changes on the server side, and updates the scenes and their controllers accordingly.
 */
public class MultiplayerCtrl {

    private MultiGameQuestionAScreenCtrl questionAScreenCtrl;
    private Scene questionAScreen;

    private MultiGameQuestionBScreenCtrl questionBScreenCtrl;
    private Scene questionBScreen;

    private MultiGameQuestionCScreenCtrl questionCScreenCtrl;
    private Scene questionCScreen;

    private MultiGameQuestionDScreenCtrl questionDScreenCtrl;
    private Scene questionDScreen;


    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    private long gameId;
    private String username;

    @Inject
    public MultiplayerCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    public void initialize(
            Pair<MultiGameQuestionAScreenCtrl, Parent> questionAScreen,
            Pair<MultiGameQuestionBScreenCtrl, Parent> questionBScreen,
            Pair<MultiGameQuestionCScreenCtrl, Parent> questionCScreen,
            Pair<MultiGameQuestionDScreenCtrl, Parent> questionDScreen
    ) {
        this.questionAScreenCtrl = questionAScreen.getKey();
        this.questionAScreen = new Scene(questionAScreen.getValue());

        this.questionBScreenCtrl = questionBScreen.getKey();
        this.questionBScreen = new Scene(questionBScreen.getValue());

        this.questionCScreenCtrl = questionCScreen.getKey();
        this.questionCScreen = new Scene(questionCScreen.getValue());

        this.questionDScreenCtrl = questionDScreen.getKey();
        this.questionDScreen = new Scene(questionDScreen.getValue());
    }

    public void start(long gameId, String username) {
        this.gameId = gameId;
        this.username = username;

    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    public void promptReturn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave the game?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == yesButton) {
            mainCtrl.showHome();
        }
    }
}
