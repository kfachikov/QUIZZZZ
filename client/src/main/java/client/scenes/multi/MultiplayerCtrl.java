package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.scenes.multi.question.*;
import client.services.MultiplayerGameStatePollingService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import commons.misc.Activity;
import commons.misc.GameResponse;
import commons.multi.ChatMessage;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.question.*;
import commons.queue.QueueUser;
import jakarta.ws.rs.WebApplicationException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.*;

/**
 * Class responsible for managing the multiplayer game for the client.
 * <p>
 * It keeps track of any logical changes on the server side, and updates the scenes and their controllers accordingly.
 */
public class MultiplayerCtrl {

    private final int forbidden = 403;
    private final int messagesQuestion = 3;
    private final int messagesLeaderboard = 6;
    private final double timeAttackFactor = 2;

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

    /*
    Abstract class instance. To be used for the answer-revealing process.
     */
    private MultiQuestionScreen currentScreenCtrl;


    /*
    Field to be used for the answer-correctness check. Even if a player clicks on one and
    the same answer, the field variable wouldn't change.
     */
    private String lastSubmittedAnswer;

    /*
    TimeLine instance to handle the visual effects of the progress bar used to "time" the rounds.
     */
    private Timeline timeline;

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private final MultiplayerGameStatePollingService pollingService;
    private final ActivityImageUtils activityImageUtils;

    private long gameId;
    private String username;
    /*
    Field to be used to compute the negative effect of "Time Attack" joker.
     */
    private long negativeTimeAccumulated;

    private Image surprised;
    private Image laughing;
    private Image angry;
    private Image crying;

    private final ChangeListener<MultiPlayerState> onPoll = (observable, oldValue, newValue) -> {
        // If state has changed, we probably have to switch scenes
        if (newValue != null && (oldValue == null || !newValue.getState().equals(oldValue.getState()))) {
            switchState(newValue);
        }

        // If the state have changed, perhaps some people have used "Time Attack" jokers.
        if (newValue != null && (oldValue != null && newValue.getTimeAttacksUsed() != oldValue.getTimeAttacksUsed())) {
            alterTimer(oldValue, newValue);
        }
        // If state has changed, perhaps some new messages have been "registered".
        if (newValue != null) {
            List<ChatMessage> chatMessages = newValue.getChatMessageList();
            if (newValue.getState().equals(MultiPlayerState.QUESTION_STATE)) {
                updateMessagesQuestion(chatMessages);
            } else if (newValue.getState().equals(MultiPlayerState.LEADERBOARD_STATE) ||
                newValue.getState().equals(MultiPlayerState.GAME_OVER_STATE)) {
                updateMessagesLeaderboard(chatMessages);
            }
        }
    };

    /**
     * Constructor for Multiplayer controller.
     *
     * @param mainCtrl       Main controller
     * @param serverUtils    Server utilities
     * @param pollingService Multiplayer game state polling service
     * @param activityImageUtils    Activity Image utility
     */
    @Inject
    public MultiplayerCtrl(MainCtrl mainCtrl,
                           ServerUtils serverUtils,
                           MultiplayerGameStatePollingService pollingService,
                           ActivityImageUtils activityImageUtils
    ) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.pollingService = pollingService;
        this.activityImageUtils = activityImageUtils;
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

        setStylesheets();
        initializeImages();
    }

    /**
     * sets the stylesheets.
     */
    public void setStylesheets() {
        String CSSPath = "styling/GameStyle.css";

        consumptionQuestionScreen.getStylesheets().add(CSSPath);
        guessQuestionScreen.getStylesheets().add(CSSPath);
        insteadQuestionScreen.getStylesheets().add(CSSPath);
        moreExpensiveQuestionScreen.getStylesheets().add(CSSPath);
        mockScreen.getStylesheets().add(CSSPath);
        leaderboard.getStylesheets().add(CSSPath);
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

        serverUtils.addMultiPlayer(gameId, new MultiPlayer(username, 0, true, true, true));
        pollingService.start(gameId);

        insteadQuestionScreenCtrl.setJokers();
        guessQuestionScreenCtrl.setJokers();
        moreExpensiveQuestionScreenCtrl.setJokers();
        consumptionQuestionScreenCtrl.setJokers();

        initializeEmojiButtonImages();

        switchState(pollingService.poll());
        mainCtrl.setOnCloseRequest(this::promptLeave);
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
     *
     * @return whether the user selected "yes".
     */
    public boolean promptLeave() {
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
            return true;
        }
        return false;
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
            case MultiPlayerState.TRANSITION_STATE:
                revealAnswerCorrectness(game);
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
     * Updates the background of the current scene according to the correctness of the answer given.
     *
     * @param game  Multiplayer game state - to be used for answer comparison clieny-side.
     *              Used for changing the background during the transition phase.
     */
    public void revealAnswerCorrectness(MultiPlayerState game) {
        currentScreenCtrl.setScore(game.getPlayerByUsername(username).getScore());
        if (game.compareAnswerClient(lastSubmittedAnswer)) {
            currentScreenCtrl.getWindow()
                    .setStyle("-fx-background-color: #" + (Paint.valueOf("aedd94")).toString().substring(2));
        } else {
            currentScreenCtrl.getWindow()
                    .setStyle("-fx-background-color: #" + (Paint.valueOf("ff8a84")).toString().substring(2));
            /*
            The correct answer is revealed for each type of question.
            */
            if (currentScreenCtrl instanceof MultiGameConsumptionQuestionScreenCtrl) {
                ((MultiGameConsumptionQuestionScreenCtrl) currentScreenCtrl).showCorrectAnswer();
            }
            if (currentScreenCtrl instanceof MultiGameInsteadQuestionScreenCtrl) {
                ((MultiGameInsteadQuestionScreenCtrl) currentScreenCtrl).showCorrectAnswer();
            }
            if (currentScreenCtrl instanceof MultiGameMoreExpensiveQuestionScreenCtrl) {
                ((MultiGameMoreExpensiveQuestionScreenCtrl) currentScreenCtrl).showCorrectAnswer();
            }
            if (currentScreenCtrl instanceof MultiGameGuessQuestionScreenCtrl) {
                ((MultiGameGuessQuestionScreenCtrl) currentScreenCtrl).setMessageCorrectAnswer((GuessQuestion)
                        game.getQuestionList().get(game.getRoundNumber()));
            }
        }

    }

    /**
     * Used to change the color of the background of the current question scene to the initial blue color.
     * Also, updates the score the player have accumulated so far.
     *
     * @param game  Multiplayer game state to fetch the client's score from.
     */
    private void setDefault(MultiPlayerState game) {
        currentScreenCtrl.getWindow()
                .setStyle("-fx-background-color: #" + (Paint.valueOf("00236f")).toString().substring(2));
        currentScreenCtrl.setScore(game.getPlayerByUsername(username).getScore());
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

        /*
        Setting the lastSubmittedAnswer field to something "default", so no errors occur if
        it is compared with the actual one, but the client hadn't submitted anything
        (and thus, not called `submitAnswer` method),
         */
        lastSubmittedAnswer = "";

        /*
        Sets the negative accumulated time to be 0, as no "Time Attack" jokers have been yet used.
         */
        negativeTimeAccumulated = 0;

        /*
        In case a player hadn't used his "Remove Incorrect" joker, but it was disables because of answer
        submission, the button is enabled again.
         */
        if (game.getPlayerByUsername(username).getIncorrectAnswerJoker()) {
            enableRemoveIncorrect();
        }

        AbstractQuestion question = game.getQuestionList().get(roundNumber);

        if (question instanceof ConsumptionQuestion) {
            ConsumptionQuestion consumptionQuestion = (ConsumptionQuestion) question;
            showConsumptionQuestion(game, consumptionQuestion);
        } else if (question instanceof GuessQuestion) {
            GuessQuestion guessQuestion = (GuessQuestion) question;
            showGuessQuestion(game, guessQuestion);
        } else if (question instanceof InsteadQuestion) {
            InsteadQuestion insteadQuestion = (InsteadQuestion) question;
            showInsteadQuestion(game, insteadQuestion);
        } else if (question instanceof MoreExpensiveQuestion) {
            MoreExpensiveQuestion moreExpensiveQuestion = (MoreExpensiveQuestion) question;
            showMoreExpensiveQuestion(game, moreExpensiveQuestion);
        }
    }

    /**
     * Show "Consumption" question screen.
     *
     * @param game      Multiplayer game to call setDefault with.
     * @param question "Consumption" question.
     */
    private void showConsumptionQuestion(MultiPlayerState game, ConsumptionQuestion question) {
        currentScreenCtrl = consumptionQuestionScreenCtrl;
        setDefault(game);
        consumptionQuestionScreenCtrl.setJokersStyle();
        consumptionQuestionScreenCtrl.setQuestion(question);
        consumptionQuestionScreenCtrl.getGameStateLabel().setText("Game ID: " + game.getId());
        consumptionQuestionScreenCtrl.prepareAnswerButton();
        consumptionQuestionScreenCtrl.setAnswers(question);
        consumptionQuestionScreenCtrl.setDescription(question);
        consumptionQuestionScreenCtrl.setImage(getActivityImage(question.getActivity()));
        centerImage(consumptionQuestionScreenCtrl.getImage());
        mainCtrl.getPrimaryStage().setScene(consumptionQuestionScreen);
        startTimer(game, consumptionQuestionScreenCtrl);
    }

    /**
     * Show "Guess" question screen.
     *
     * @param game      Multiplayer game to call setDefault with.
     * @param question "Guess" question.
     */
    private void showGuessQuestion(MultiPlayerState game, GuessQuestion question) {
        currentScreenCtrl = guessQuestionScreenCtrl;
        setDefault(game);
        guessQuestionScreenCtrl.setJokersStyle();
        guessQuestionScreenCtrl.getGameStateLabel().setText("Game ID: " + game.getId());
        guessQuestionScreenCtrl.inputFieldDefault();
        guessQuestionScreenCtrl.setDescription(question);
        guessQuestionScreenCtrl.setImage(getActivityImage(question.getActivity()));
        centerImage(guessQuestionScreenCtrl.getImage());
        /*
        Disables the "Remove Incorrect" joker for the guess question types.
         */
        disableUsedRemoveIncorrect();
        mainCtrl.getPrimaryStage().setScene(guessQuestionScreen);
        startTimer(game, guessQuestionScreenCtrl);
    }

    /**
     * Show "Instead of" question screen.
     *
     * @param game      Multiplayer game to call setDefault with.
     * @param question "Instead of" question.
     */
    private void showInsteadQuestion(MultiPlayerState game, InsteadQuestion question) {
        currentScreenCtrl = insteadQuestionScreenCtrl;
        setDefault(game);
        insteadQuestionScreenCtrl.setJokersStyle();
        insteadQuestionScreenCtrl.setQuestion(question);
        insteadQuestionScreenCtrl.getGameStateLabel().setText("Game ID: " + game.getId());
        insteadQuestionScreenCtrl.prepareAnswerButton();
        insteadQuestionScreenCtrl.setDescription(question);
        insteadQuestionScreenCtrl.setAnswers(question);
        insteadQuestionScreenCtrl.setImage(getActivityImage(question.getActivity()));
        centerImage(insteadQuestionScreenCtrl.getImage());
        mainCtrl.getPrimaryStage().setScene(insteadQuestionScreen);
        startTimer(game, insteadQuestionScreenCtrl);

    }

    /**
     * Show "More Expensive" question screen.
     *
     * @param game      Multiplayer game to call setDefault with.
     * @param question "More Expensive" question.
     */
    private void showMoreExpensiveQuestion(MultiPlayerState game, MoreExpensiveQuestion question) {
        currentScreenCtrl = moreExpensiveQuestionScreenCtrl;
        setDefault(game);
        moreExpensiveQuestionScreenCtrl.setJokersStyle();
        moreExpensiveQuestionScreenCtrl.setQuestion(question);
        moreExpensiveQuestionScreenCtrl.prepareAnswerButton();
        moreExpensiveQuestionScreenCtrl.getGameStateLabel().setText("Game ID: " + game.getId());
        moreExpensiveQuestionScreenCtrl.setAnswerDescriptions(question);
        moreExpensiveQuestionScreenCtrl.setAnswerImages(getActivityImage(question.getAnswerChoices().get(0)),
                getActivityImage(question.getAnswerChoices().get(1)),
                getActivityImage(question.getAnswerChoices().get(2)));
        mainCtrl.getPrimaryStage().setScene(moreExpensiveQuestionScreen);
        startTimer(game, moreExpensiveQuestionScreenCtrl);
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
        lastSubmittedAnswer = chosenAnswer.substring(0, chosenAnswer.length() - 2);
        serverUtils.postAnswerMultiplayer(new GameResponse(
                gameId,
                new Date().getTime() + negativeTimeAccumulated,
                (int) getRoundNumber(serverUtils.getMultiGameState(gameId)),
                username,
                lastSubmittedAnswer
        ));
        /*
        Disables the "Remove Incorrect" joker so that the user cannot click on it unintentionally.
         */
        disableUsedRemoveIncorrect();
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
     * Getter method for getting the image of an activity.
     *
     * @param activity Activity to get an image from.
     * @return JavaFX image of the activity.
     */
    public Image getActivityImage(Activity activity) {
        long key = activity.getKey();
        return activityImageUtils.getActivityImage(key);
    }

    /**
     * Centers an image inside any imageView passed as argument.
     *
     * @param imageView ImageView instance, which image should be centered.
     */
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reductionCoef = Math.min(ratioX, ratioY);

            double w = img.getWidth() * reductionCoef;
            double h = img.getHeight() * reductionCoef;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);
        }
    }

    /**
     * Initializes a new instance of TimeLine and starts it.
     * Used at the beginning of each "scene-showing" process.
     *
     * @param game                  Multiplayer game state instance to work with - needed for
     *                              next phase "fetching". The timer would go from now till
     *                              the next phase (Date).
     * @param multiQuestionScreen   Controller for the corresponding scene to be visualized.
     */
    private void startTimer(MultiPlayerState game, MultiQuestionScreen multiQuestionScreen) {
        ProgressBar time = multiQuestionScreen.getTime();
        time.setStyle("-fx-accent: #006e8c");

        long nextPhase = game.getNextPhase();
        long roundTime = nextPhase - new Date().getTime();

        if (timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING)) {
            timeline.stop();
        }

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(time.progressProperty(), 1)),
                new KeyFrame(Duration.millis(roundTime * 7 / 10), e -> {
                    time.setStyle("-fx-accent: red");
                }),
                new KeyFrame(Duration.millis(roundTime), e -> {
                    multiQuestionScreen.disableAnswerSubmission();
                }, new KeyValue(time.progressProperty(), 0))
        );
        timeline.play();
    }

    /**
     * Method for re-entering a queue. Bound with "Play Again" button on final
     * leaderboard.
     *
     * In case a player with such username already exists, the player is redirected to
     * the home screen.
     *
     * TODO Alert of non-unique username.
     */
    public void enterNewQueue() {
        try {
            QueueUser user = serverUtils.addQueueUser(new QueueUser(username));
            mainCtrl.showQueue(user, serverUtils.getCurrentServer());
        } catch (WebApplicationException e) {
            switch (e.getResponse().getStatus()) {
                case forbidden:
                    mainCtrl.showHome();
                    mainCtrl.getHomeCtrl().playMulti();
                    break;
            }
        }
    }

    /**
     * Disables the used "Double Points" joker for all controllers.
     */
    public void disableUsedDoublePoints() {
        consumptionQuestionScreenCtrl.disableDoublePoints();
        guessQuestionScreenCtrl.disableDoublePoints();
        moreExpensiveQuestionScreenCtrl.disableDoublePoints();
        insteadQuestionScreenCtrl.disableDoublePoints();
    }

    /**
     * Disables the used "Remove Incorrect" joker for all controllers.
     */
    public void disableUsedRemoveIncorrect() {
        consumptionQuestionScreenCtrl.disableRemoveIncorrect();
        guessQuestionScreenCtrl.disableRemoveIncorrect();
        moreExpensiveQuestionScreenCtrl.disableRemoveIncorrect();
        insteadQuestionScreenCtrl.disableRemoveIncorrect();
    }

    /**
     * Enables the unused "Remove Incorrect" joker for all controllers.
     */
    public void enableRemoveIncorrect() {
        consumptionQuestionScreenCtrl.enableRemoveIncorrect();
        guessQuestionScreenCtrl.enableRemoveIncorrect();
        moreExpensiveQuestionScreenCtrl.enableRemoveIncorrect();
        insteadQuestionScreenCtrl.enableRemoveIncorrect();
    }

    /**
     * Disables the used "Time Attack" joker for all controllers.
     */
    public void disableUsedTimeAttack() {
        consumptionQuestionScreenCtrl.disableTimeAttack();
        guessQuestionScreenCtrl.disableTimeAttack();
        moreExpensiveQuestionScreenCtrl.disableTimeAttack();
        insteadQuestionScreenCtrl.disableTimeAttack();
    }


    /**
     * Initialized the actions happening once an emoji button is clicked.
     *
     * @param button1    Button to be bound with particular action.
     * @param button2    Button to be bound with particular action.
     * @param button3    Button to be bound with particular action.
     * @param button4    Button to be bound with particular action.
     */
    public void initializeEmojiButtons(Button button1, Button button2, Button button3, Button button4) {
        button1.setOnAction(e -> {
            postReaction("surprised");
        });
        button2.setOnAction(e -> {
            postReaction("laughing");
        });
        button3.setOnAction(e -> {
            postReaction("angry");
        });
        button4.setOnAction(e -> {
            postReaction("crying");
        });
    }

    /**
     * Posts a chatMessage object to the server.
     *
     * @param emoji     Emoji String to be used for "defining" the particular
     *                  emoji submitted.
     */
    private void postReaction(String emoji) {
        serverUtils.addReaction(gameId,
                new ChatMessage(username, emoji));
    }

    /**
     * Initialized the actions happening once joker is being used.
     *
     * @param button1    Button to be bound with particular action.
     * @param button2    Button to be bound with particular action.
     * @param button3    Button to be bound with particular action.
     */
    public void initializeJokerButtons(Button button1, Button button2, Button button3) {
        button1.setOnAction(e -> {
            postJoker("doublePoints");
            disableUsedDoublePoints();
        });
        button2.setOnAction(e -> {
            postJoker("removeIncorrect");
        });
        button3.setOnAction(e -> {
            postJoker("timeAttack");
            disableUsedTimeAttack();
        });
    }

    /**
     * Posts a chatMessage object to the server.
     *
     * @param joker     Joker String to be used for "defining" the particular
     *                  joker being used.
     */
    private void postJoker(String joker) {
        if (!"removeIncorrect".equals(joker) || !(currentScreenCtrl instanceof MultiGameGuessQuestionScreenCtrl)) {
            serverUtils.addJoker(gameId,
                    new ChatMessage(username, joker));
            if ("removeIncorrect".equals(joker)) {
                removeIncorrect();
                disableUsedRemoveIncorrect();
            }
        }
    }



    /**
     * Method to be called when a change in the messages is registered during QUESTION_STATE.
     *
     * @param chatMessageList  List of ChatMessage instances to be used for the "chat".
     */
    private void updateMessagesQuestion(List<ChatMessage> chatMessageList) {
        List<Node> messagesParts = currentScreenCtrl.getChatMessages().getChildren();
        updateChat(chatMessageList, messagesParts, messagesQuestion);
    }

    /**
     * Method to be called when a change in the messages is registered during QUESTION_STATE.
     *
     * @param chatMessageList  List of ChatMessage instances to be used for the "chat".
     */
    private void updateMessagesLeaderboard(List<ChatMessage> chatMessageList) {
        List<Node> messagesParts = leaderboardCtrl.getChatMessages().getChildren();
        updateChat(chatMessageList, messagesParts, messagesLeaderboard);
    }

    /**
     * In case a change occur in the game state, which is constantly being pulled,
     * the reaction section is being updated.
     *
     * @param chatMessageList       List of ChatMessage instances to be used for the "chat".
     * @param messagesParts         List of Node instances. Correspond to the particular list
     *                              of nodes of the desired screen.
     * @param messagesNumber        The number of messages to be shown. To be different between
     *                              question and leaderboard screen.
     */
    private void updateChat(List<ChatMessage> chatMessageList, List<Node> messagesParts, int messagesNumber) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>(chatMessageList);
        Collections.reverse(chatMessages);

        /*
        In the GridPane `reaction`, Labels and ImageViews are taking turns.
        Thus, the Labels would have even indices within the children of the pane.
        The ImageViews would have odd indices.
         */
        int currentMessageLabelIndex = 0;
        int currentMessageImageIndex = 1;
        for (ChatMessage chatMessage : chatMessages) {
            Label currentReactionLabel = (Label) messagesParts.get(currentMessageLabelIndex);
            ImageView currentReactionImage = (ImageView) messagesParts.get(currentMessageImageIndex);
            String username = chatMessage.getUsername();

            switch (chatMessage.getMessage()) {
                case "angry" ->
                        visualizeReactionMessage(currentReactionLabel, currentReactionImage, username, angry);
                case "crying" ->
                        visualizeReactionMessage(currentReactionLabel, currentReactionImage, username, crying);
                case "laughing" ->
                        visualizeReactionMessage(currentReactionLabel, currentReactionImage, username, laughing);
                case "surprised" ->
                        visualizeReactionMessage(currentReactionLabel, currentReactionImage, username, surprised);
                default -> jokerUsed(currentReactionLabel, currentReactionImage, username, chatMessage.getMessage());
            }

            currentReactionLabel.setVisible(true);
            currentReactionImage.setVisible(true);

            currentMessageLabelIndex = currentMessageLabelIndex + 2;
            currentMessageImageIndex = currentMessageImageIndex + 2;
            if (currentMessageLabelIndex >= 2 * messagesNumber) {
                break;
            }
        }

        /*
        In case the total number of chatMessages is less than 3 - the size of our "chat",
        the later "lines", consisting of username and emoji submitted are made not visible.
         */
        for (int i = currentMessageLabelIndex; i < 2 * messagesNumber; i++) {
            Node currentNode = messagesParts.get(i);
            currentNode.setVisible(false);
        }
    }

    /**
     * Method to visualize a particular reaction chat message.
     * Extracts functionality out and avoid code duplication.
     *
     * @param label     Label instance to show text.
     * @param imageView An ImageView instance to show image.
     * @param username  Username of player who sent particular message.
     * @param image     Image to be visualized.
     */
    private void visualizeReactionMessage(Label label, ImageView imageView, String username, Image image) {
        label.setText(username + " reacts with ");
        imageView.setImage(image);
    }

    /**
     * Method to decide which joker have been used.
     *
     * @param label     Label to be used for message text.
     * @param imageView ImageView to be used for image - nothing in case joker usage
     *                  is recorded.
     * @param username  Username of player "sending" message.
     * @param joker     Joker information.
     */
    private void jokerUsed(Label label, ImageView imageView, String username, String joker) {
        switch (joker) {
            case "doublePoints" -> visualizeJokeUsage(label, username, "Double Points", imageView);
            case "removeIncorrect" -> visualizeJokeUsage(label, username, "Remove Incorrect", imageView);
            case "timeAttack" -> visualizeJokeUsage(label, username, "Time Attack", imageView);
        }
    }

    /**
     * Method to visualize joker activity in chat.
     *
     * @param label     Label to be used for text.
     * @param username  Username of player "sending" message.
     * @param joker     Joker information.
     * @param imageView ImageView to be set to `null`.
     */
    private void visualizeJokeUsage(Label label, String username, String joker, ImageView imageView) {
        label.setText(username + " used " + joker + "!");
        imageView.setImage(null);
    }

    /**
     * In case a "Time Attack" joker is submitted, the timer for all
     * players except fot the "attacker" should be altered.
     *
     * This is achieved by changing the rate at which the timeline is progressing.
     *
     * @param oldState OldState of Game to be used for check whether this particular
     *                 client should be affected by joker.
     * @param newState NewState of the Game.
     */
    private void alterTimer(MultiPlayerState oldState, MultiPlayerState newState) {
        if (newState.getTimeAttacksUsed() == 0) {
            timeline.setRate(1);
        } else if (oldState.getPlayerByUsername(username).getTimerRate() !=
                newState.getPlayerByUsername(username).getTimerRate()) {
            /*
            Instead of altering the remaining time, we would speed up the client-side timer,
            and accumulate the "lost" time.

            Note the increase in speed is reversely proportional to the time that should be
            "deducted" - in our case, added to the "negativeTimeAccumulated" counter.
             */
            timeline.setRate(timeline.getRate() * timeAttackFactor);
            negativeTimeAccumulated = negativeTimeAccumulated +
                    (long) ((newState.getNextPhase() - new Date().getTime()) * (1 - 1 / timeAttackFactor));
        }
    }

    /**
     * Removes a "random" incorrect answer - the first one it finds is different from the actual one.
     */
    private void removeIncorrect() {
        MultiPlayerState game = serverUtils.getMultiGameState(gameId);
        AbstractQuestion currentQuestion = game.getQuestionList().get(game.getRoundNumber());
        List<Button> answerChoices = currentScreenCtrl.getAnswerButtons();
        /*
        The case when a question asked is "Which is the most expensive activity?" should be
        handled independently, as the actual answers are "stored" in the "description" labels there.
         */
        if (currentScreenCtrl instanceof MultiGameMoreExpensiveQuestionScreenCtrl) {
            List<String> answers = ((MultiGameMoreExpensiveQuestionScreenCtrl) currentScreenCtrl).getDescriptions();
            int index = 0;
            for (String answer: answers) {
                /*
                As in `submitAnswer`, we should remove the last two characters from the field "containing" the answer.
                 */
                if (!answer.substring(0, answer.length() - 2).equals(currentQuestion.getCorrectAnswer())) {
                    answerChoices.get(index).setDisable(true);
                    break;
                } else {
                    index++;
                }
            }
        } else {
            for (Button answer: answerChoices) {
                /*
                As in `submitAnswer`, we should remove the last two characters from the field "containing" the answer.

                These characters are additionally appended when the Button instances have been created.
                 */
                if (!answer.getText().substring(0, answer.getText().length() - 2)
                        .equals(currentQuestion.getCorrectAnswer())) {
                    answer.setDisable(true);
                    break;
                }
            }
        }
    }

    /**
     * Initializes all image fields in the MultiplayerCtrl class.
     */
    private void initializeImages() {
        surprised = new Image(
                String.valueOf(this.getClass().getClassLoader().getResource("emoji/Surprised.png")));
        laughing = new Image(
                String.valueOf(this.getClass().getClassLoader().getResource("emoji/Laughing.png")));
        angry = new Image(
                String.valueOf(this.getClass().getClassLoader().getResource("emoji/Angry.png")));
        crying = new Image(
                String.valueOf(this.getClass().getClassLoader().getResource("emoji/Crying.png")));
    }

    /**
     * Initializes the emoji button images.
     */
    private void initializeEmojiButtonImages() {
        consumptionQuestionScreenCtrl.setEmojiImages(surprised, laughing, angry, crying);
        guessQuestionScreenCtrl.setEmojiImages(surprised, laughing, angry, crying);
        insteadQuestionScreenCtrl.setEmojiImages(surprised, laughing, angry, crying);
        moreExpensiveQuestionScreenCtrl.setEmojiImages(surprised, laughing, angry, crying);
    }
}
