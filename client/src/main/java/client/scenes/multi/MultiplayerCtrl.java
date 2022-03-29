package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.question.MultiGameConsumptionQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameGuessQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameInsteadQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameMoreExpensiveQuestionScreenCtrl;
import client.services.MultiplayerGameStatePollingService;
import client.utils.ServerUtils;
import commons.misc.GameResponse;
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
import java.util.*;

/**
 * Class responsible for managing the multiplayer game for the client.
 * <p>
 * It keeps track of any logical changes on the server side, and updates the scenes and their controllers accordingly.
 */
public class MultiplayerCtrl {

    private MultiGameConsumptionQuestionScreenCtrl consumptionQuestionScreenCtrl;
    private Scene consumptionQuestionScreen;

    private MultiGameGuessQuestionScreenCtrl guessQuestionScreenCtrl;
    private Scene guessQuestionScreen;

    private MultiGameInsteadQuestionScreenCtrl insteadQuestionScreenCtrl;
    private Scene insteadQuestionScreen;

    private MultiGameMoreExpensiveQuestionScreenCtrl moreExpensiveQuestionScreenCtrl;
    private Scene moreExpensiveQuestionScreen;

    private MultiGameMockScreenCtrl mockScreenCtrl;
    private Scene mockScreen;

    private LeaderboardScreenCtrl leaderboardCtrl;
    private Scene leaderboard;

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
     * @param consumptionQuestionScreen     Mock question A screen pair
     * @param guessQuestionScreen           Mock question B screen pair
     * @param insteadQuestionScreen         Mock question C screen pair
     * @param moreExpensiveQuestionScreen   Mock question D screen pair
     * @param mockMulti                     Mock miscellaneous screen pair
     * @param leaderboard                   Final/intermediate leaderboard screen pair
     */
    public void initialize(
            Pair<MultiGameConsumptionQuestionScreenCtrl, Parent> consumptionQuestionScreen,
            Pair<MultiGameGuessQuestionScreenCtrl, Parent> guessQuestionScreen,
            Pair<MultiGameInsteadQuestionScreenCtrl, Parent> insteadQuestionScreen,
            Pair<MultiGameMoreExpensiveQuestionScreenCtrl, Parent> moreExpensiveQuestionScreen,
            Pair<MultiGameMockScreenCtrl, Parent> mockMulti,
            Pair<LeaderboardScreenCtrl, Parent> leaderboard) {
        this.consumptionQuestionScreenCtrl = consumptionQuestionScreen.getKey();
        this.consumptionQuestionScreen = new Scene(consumptionQuestionScreen.getValue());

        this.guessQuestionScreenCtrl = guessQuestionScreen.getKey();
        this.guessQuestionScreen = new Scene(guessQuestionScreen.getValue());

        this.insteadQuestionScreenCtrl = insteadQuestionScreen.getKey();
        this.insteadQuestionScreen = new Scene(insteadQuestionScreen.getValue());

        this.moreExpensiveQuestionScreenCtrl = moreExpensiveQuestionScreen.getKey();
        this.moreExpensiveQuestionScreen = new Scene(moreExpensiveQuestionScreen.getValue());

        this.mockScreenCtrl = mockMulti.getKey();
        this.mockScreen = new Scene(mockMulti.getValue());

        this.leaderboardCtrl = leaderboard.getKey();
        this.leaderboard = new Scene(leaderboard.getValue());

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
        switch (game.getState()) {
            case MultiPlayerState.QUESTION_STATE:
                switchToQuestion(game);
                break;
            case MultiPlayerState.LEADERBOARD_STATE:
                showIntermediateGameOver(sortList(game), game.getState());
                break;
            case MultiPlayerState.GAME_OVER_STATE:
                showIntermediateGameOver(sortList(game), game.getState());
                break;
            default:
                switchToMock(game);
                break;
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
        consumptionQuestionScreenCtrl.setGameStateLabelText(question.debugString());
        consumptionQuestionScreenCtrl.setQuestion(question);
        mainCtrl.getPrimaryStage().setScene(consumptionQuestionScreen);
    }

    /**
     * Show "Guess" question screen.
     *
     * @param question "Guess" question.
     */
    private void showGuessQuestion(GuessQuestion question) {
        guessQuestionScreenCtrl.setGameStateLabelText(question.debugString());
        guessQuestionScreenCtrl.setQuestion(question);
        mainCtrl.getPrimaryStage().setScene(guessQuestionScreen);
    }

    /**
     * Show "Instead of" question screen.
     *
     * @param question "Instead of" question.
     */
    private void showInsteadQuestion(InsteadQuestion question) {
        insteadQuestionScreenCtrl.setGameStateLabelText(question.debugString());
        insteadQuestionScreenCtrl.setQuestion(question);
        mainCtrl.getPrimaryStage().setScene(insteadQuestionScreen);

    }

    /**
     * Show "More Expensive" question screen.
     *
     * @param question "More Expensive" question.
     */
    private void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        moreExpensiveQuestionScreenCtrl.setGameStateLabelText(question.debugString());
        moreExpensiveQuestionScreenCtrl.setQuestion(question);
        mainCtrl.getPrimaryStage().setScene(moreExpensiveQuestionScreen);
    }

    /**
     * Sets the scene as leaderboard/gameOver screen.
     * Calls LeaderBoardScreenCtrl.setScene() and passes
     * the sorted list of players and the state of the game, either LEADERBOARD or GAME_OVER
     *
     * @param players   the list of players in the game, in descending score order.
     * @param gameState the state of the game, is either LEADERBOARD or GAME_OVER.
     */
    public void showIntermediateGameOver(List<MultiPlayer> players, String gameState) {
        leaderboardCtrl.setScene(players, gameState);
        mainCtrl.getPrimaryStage().setScene(leaderboard);
    }

    /**
     * Sends a string to the server sa a chosen answer from the player.
     * The last two symbols from the string should be removed, as they
     * denote the "Wh" in the button text field.
     *
     * @param chosenAnswer String value of button clicked - answer chosen
     */
    public void submitAnswer(String chosenAnswer) {
        serverUtils.postAnswerMultiplayer(new GameResponse(
                gameId,
                new Date().getTime(),
                (int) getRoundNumber(serverUtils.getMultiGameState(gameId)),
                username,
                chosenAnswer.substring(0, chosenAnswer.length() - 2)
        ));
    }

    /**
     * Returns to home screen.
     * Is assigned to the Return Home button in Game Over screen.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * Sorts the list of players in the game in descending score order.
     *
     * @param game The ongoing game, from which the <strong>unsorted</strong> List is retrieved
     * @return the retrieved List players, <strong>sorted</strong>.
     */
    public List<MultiPlayer> sortList(MultiPlayerState game) {
        List<MultiPlayer> players = game.getPlayers();
        Collections.sort(players, Comparator.comparing(player1 -> player1.getScore()));
        Collections.reverse(players);
        return players;
    }

    /**
     * Getter for the user username.
     *
     * @return the username of the user that joined the queue.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the game id.
     *
     * @return the id of the game.
     */
    public long getId() {
        return gameId;
    }

    /**
     * Getter for the game id.
     *
     * @return the id of the game.
     * @param game is a MultiPlayerState
     */
    public long getRoundNumber(MultiPlayerState game) {
        return game.getRoundNumber();
    }

    /**
     * activates when a player presses angry emoji.
     */
    public void angryEmoji() {
    }

    /**
     * activates when a player presses crying emoji.
     */
    public void cryingEmoji() {
    }

    /**
     * activates when a player presses laughing emoji.
     */
    public void laughingEmoji() {
    }

    /**
     * activates when a player presses surprised emoji.
     */
    public void surprisedEmoji() {
    }
}
