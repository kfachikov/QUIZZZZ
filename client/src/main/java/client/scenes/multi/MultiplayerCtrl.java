package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.question.MultiGameQuestionAScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionBScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionCScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionDScreenCtrl;
import client.services.MultiplayerGameStatePollingService;
import client.utils.ServerUtils;
import commons.multi.MultiPlayerState;
import commons.question.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * <p>
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
    private final MultiplayerGameStatePollingService pollingService;

    private long gameId;
    private String username;

    private final ChangeListener<MultiPlayerState> onPoll = new ChangeListener<MultiPlayerState>() {
        @Override
        public void changed(
                ObservableValue<? extends MultiPlayerState> observable,
                MultiPlayerState oldValue,
                MultiPlayerState newValue) {
            System.out.println("Poll!");
            // If state has changed, we probably have to switch scenes
            if (!oldValue.getState().equals(newValue.getState())) {
                switchState(newValue);
            }
        }
    };

    @Inject
    public MultiplayerCtrl(MainCtrl mainCtrl,
                           ServerUtils serverUtils,
                           MultiplayerGameStatePollingService pollingService
    ) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.pollingService = pollingService;
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

    /**
     * Start a multiplayer session.
     * <p>
     * Will automatically switch scenes to multiplayer question screens, leaderboard etc.
     * <p>
     * Can be stopped using `stop()`
     *
     * @param gameId   Id of the multiplayer game
     * @param username Name of the player in the game
     */
    public void start(long gameId, String username) {
        this.gameId = gameId;
        this.username = username;

        pollingService.valueProperty().addListener(onPoll);

        pollingService.start(gameId);
    }

    /**
     * Stop the multiplayer session.
     * <p>
     * Resets the controller to a state where another multiplayer game can be played later.
     */
    public void stop() {
        pollingService.stop();
    }

    /**
     * Confirms if the user really wants to leave the game and allows them to
     * return to the home screen.
     */
    public void promptLeave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave the game?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == yesButton) {
            stop();
            mainCtrl.showHome();
        }
    }

    private void switchState(MultiPlayerState game) {
        String state = game.getState();
        if (MultiPlayerState.QUESTION_STATE.equals(state)) {
            switchToQuestion(game);
        }
    }

    private void switchToQuestion(MultiPlayerState game) {
        int roundNumber = game.getRoundNumber();
        if (roundNumber < 0 || roundNumber >= 20) {
            System.err.println("Tried to switch to a question scene with invalid round number");
            return;
        }

        AbstractQuestion question = game.getQuestionList().get(roundNumber);

        if (question instanceof ConsumptionQuestion) {
            ConsumptionQuestion consumptionQuestion = (ConsumptionQuestion) question;
            showConsumptionQuestion(consumptionQuestion);
        } else if (question instanceof GuessQuestion) {
            GuessQuestion guessQuestion = (GuessQuestion) question;
            showGuessQuestion(guessQuestion);
        } else if (question instanceof InsteadQuestion) {
            InsteadQuestion insteadQuestion = (InsteadQuestion) question;
            showInsteadQuestion(insteadQuestion);
        } else if (question instanceof MoreExpensiveQuestion) {
            MoreExpensiveQuestion moreExpensiveQuestion = (MoreExpensiveQuestion) question;
            showMoreExpensiveQuestion(moreExpensiveQuestion);
        }
    }

    private void showConsumptionQuestion(ConsumptionQuestion question) {
        questionAScreenCtrl.setGameStateLabelText(question.toString());
        mainCtrl.getPrimaryStage().setScene(questionAScreen);
    }

    private void showGuessQuestion(GuessQuestion question) {
        questionBScreenCtrl.setGameStateLabelText(question.toString());
        mainCtrl.getPrimaryStage().setScene(questionBScreen);
    }

    private void showInsteadQuestion(InsteadQuestion question) {
        questionCScreenCtrl.setGameStateLabelText(question.toString());
        mainCtrl.getPrimaryStage().setScene(questionCScreen);

    }

    private void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        questionDScreenCtrl.setGameStateLabelText(question.toString());
        mainCtrl.getPrimaryStage().setScene(questionDScreen);
    }
}
