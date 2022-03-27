package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.question.MultiGameQuestionAScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionBScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionCScreenCtrl;
import client.scenes.multi.question.MultiGameQuestionDScreenCtrl;
import client.services.MultiplayerGameStatePollingService;
import client.utils.ServerUtils;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.question.*;
import javafx.beans.value.ChangeListener;
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

    private MultiGameMockScreenCtrl mockScreenCtrl;
    private Scene mockScreen;


    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private final MultiplayerGameStatePollingService pollingService;

    private long gameId;
    private String username;

    private final ChangeListener<MultiPlayerState> onPoll = (observable, oldValue, newValue) -> {
        // If state has changed, we probably have to switch scenes
        if (newValue != null && (oldValue == null || !newValue.getState().equals(oldValue.getState()))) {
            switchState(newValue);
        }
    };

    /**
     * Constructor for Multiplayer controller.
     *
     * @param mainCtrl       Main controller
     * @param serverUtils    Server utilities
     * @param pollingService Multiplayer game state polling service
     */
    @Inject
    public MultiplayerCtrl(MainCtrl mainCtrl,
                           ServerUtils serverUtils,
                           MultiplayerGameStatePollingService pollingService
    ) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.pollingService = pollingService;
    }

    /**
     * Initialization method for multiplayer controller.
     * <p>
     * Sets internal controller and scene references.
     * Sets up the associated polling service.
     * <p>
     * This method is called when application is starting up, similarly to MainCtrl:initialize.
     *
     * @param questionAScreen Mock question A screen pair
     * @param questionBScreen Mock question B screen pair
     * @param questionCScreen Mock question C screen pair
     * @param questionDScreen Mock question D screen pair
     * @param mockMulti       Mock miscellaneous screen pair
     */
    public void initialize(
            Pair<MultiGameQuestionAScreenCtrl, Parent> questionAScreen,
            Pair<MultiGameQuestionBScreenCtrl, Parent> questionBScreen,
            Pair<MultiGameQuestionCScreenCtrl, Parent> questionCScreen,
            Pair<MultiGameQuestionDScreenCtrl, Parent> questionDScreen,
            Pair<MultiGameMockScreenCtrl, Parent> mockMulti) {
        this.questionAScreenCtrl = questionAScreen.getKey();
        this.questionAScreen = new Scene(questionAScreen.getValue());

        this.questionBScreenCtrl = questionBScreen.getKey();
        this.questionBScreen = new Scene(questionBScreen.getValue());

        this.questionCScreenCtrl = questionCScreen.getKey();
        this.questionCScreen = new Scene(questionCScreen.getValue());

        this.questionDScreenCtrl = questionDScreen.getKey();
        this.questionDScreen = new Scene(questionDScreen.getValue());

        this.mockScreenCtrl = mockMulti.getKey();
        this.mockScreen = new Scene(mockMulti.getValue());

        pollingService.valueProperty().addListener(onPoll);
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

        pollingService.start(gameId);
        serverUtils.addMultiPlayer(gameId, new MultiPlayer(username, 0, true, true, true));

        switchState(pollingService.poll());
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

    /**
     * Switch state of the game.
     * <p>
     * Switches to appropriate scene/animation based on the new state of the game.
     * <p>
     * This method is called whenever a transition happens on the serverside.
     *
     * @param game Up-to-date state of the game.
     */
    private void switchState(MultiPlayerState game) {
        String state = game.getState();
        if (MultiPlayerState.QUESTION_STATE.equals(state)) {
            switchToQuestion(game);
        } else {
            switchToMock(game);
        }
    }

    /**
     * Switch to a mock scene.
     *
     * @param game Current state of the game.
     */
    private void switchToMock(MultiPlayerState game) {
        mockScreenCtrl.setGameStateLabelText(game.toString());
        mainCtrl.getPrimaryStage().setScene(mockScreen);
    }

    /**
     * Switch a question scene.
     * <p>
     * Uses the current round number of the game and its question list to
     * determine the current question, then calls the appropriate method
     * to switch to the relevant question scene.
     *
     * @param game Current state of the game.
     */
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

    /**
     * Show "Consumption" question screen.
     *
     * @param question "Consumption" question.
     */
    private void showConsumptionQuestion(ConsumptionQuestion question) {
        questionAScreenCtrl.setGameStateLabelText(question.debugString());
        mainCtrl.getPrimaryStage().setScene(questionAScreen);
    }

    /**
     * Show "Guess" question screen.
     *
     * @param question "Guess" question.
     */
    private void showGuessQuestion(GuessQuestion question) {
        questionBScreenCtrl.setGameStateLabelText(question.debugString());
        mainCtrl.getPrimaryStage().setScene(questionBScreen);
    }

    /**
     * Show "Instead of" question screen.
     *
     * @param question "Instead of" question.
     */
    private void showInsteadQuestion(InsteadQuestion question) {
        questionCScreenCtrl.setGameStateLabelText(question.debugString());
        mainCtrl.getPrimaryStage().setScene(questionCScreen);

    }

    /**
     * Show "More Expensive" question screen.
     *
     * @param question "More Expensive" question.
     */
    private void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        questionDScreenCtrl.setGameStateLabelText(question.debugString());
        mainCtrl.getPrimaryStage().setScene(questionDScreen);
    }
}
