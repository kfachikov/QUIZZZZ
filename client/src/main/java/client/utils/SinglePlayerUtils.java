package client.utils;

import client.scenes.misc.MainCtrl;
import client.scenes.single.QuestionScreen;
import client.services.SingleplayerGameStatePollingService;
import commons.question.*;
import commons.single.SinglePlayerState;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Paint;

import javax.inject.Inject;

import static commons.single.SinglePlayerState.*;

/**
 * Class, that would be used to share the common single-player game logic
 * among the different controllers.
 */
public class SinglePlayerUtils {

    private final MainCtrl mainCtrl;

    /*
    Instances used for the single-player mode to extract polling service functionality.
    SinglePlayer and SinglePlayerState instances of the current pair Player-Game on the client-side.
     */
    private final SingleplayerGameStatePollingService pollingService;
    private SinglePlayerState singlePlayerState;

    private TimerThread timerThread;

    private QuestionScreen currentController;


    /**
     * Constructor used for Spring injection.
     *
     * @param mainCtrl       Common main controller instance.
     * @param pollingService Common single-player game state polling service.
     */
    @Inject
    public SinglePlayerUtils(MainCtrl mainCtrl, SingleplayerGameStatePollingService pollingService) {
        this.mainCtrl = mainCtrl;
        this.pollingService = pollingService;
    }

    /**
     * Setter for the client-side single-player and game state instances.
     * Would be used after the initial setup of the game.
     * <p>
     * The polling service would thus be initialized with the corresponding desired functionality.
     *
     * @param singlePlayerState SinglePlayerState instance of the current game the client is playing.
     */
    public void setSinglePlayerAttributes(SinglePlayerState singlePlayerState) {
        this.singlePlayerState = singlePlayerState;

        initializePollingService();
    }

    /**
     * The polling service is initialized by receiving the GameState it should pull from
     * the server constantly (every 500 milliseconds).
     * <p>
     * A listener is assigned to its property which looks for changes of the
     * GameState instance on the server.
     * <p>
     * The polling service is started.
     */
    public void initializePollingService() {
        pollingService.setSinglePlayerState(singlePlayerState);

        pollingService.valueProperty().addListener(
                ((observable, oldGameState, newGameState) -> {
                    if (newGameState != null) {
                        /*
                        The polling service "throws" a new instance of the game state every time it poll.
                        Thus, without the existence of the following comparison, the questions scenes
                        are updated constantly, even when there is no need of it.
                        */
                        if (!singlePlayerState.getState().equals(newGameState.getState())) {
                            singlePlayerState = (SinglePlayerState) newGameState;
                            switch (newGameState.getState()) {
                                case QUESTION_STATE:
                                    /*
                                    First, the question should be chosen,
                                    so that the current controller is set accordingly.
                                    */
                                    chooseNextQuestion();
                                    break;
                                case TRANSITION_STATE:
                                    /*
                                    Whenever an answer is submitted and that is registered on the server,
                                    the game state on the client-side is also updated.
                                    */
                                    revealAnswerCorrectness();
                                    break;
                                case GAME_OVER_STATE:
                                    pollingService.stop();
                                    mainCtrl.showCongratulations();
                                    break;
                            }
                        }
                    }
                }));

        pollingService.start();
    }

    /**
     * Chooses the next question by pointing out the exact question that should be shown next.
     * Calls the corresponding `MainCtrl` method to visualize the change.
     */
    public void chooseNextQuestion() {
        AbstractQuestion current = singlePlayerState.getQuestionList().get(singlePlayerState.getRoundNumber());
        if (current instanceof ConsumptionQuestion) {
            mainCtrl.showConsumptionQuestion((ConsumptionQuestion) current);
        }
        if (current instanceof GuessQuestion) {
            mainCtrl.showGuessQuestion((GuessQuestion) current);
        }
        if (current instanceof InsteadQuestion) {
            mainCtrl.showInsteadQuestion((InsteadQuestion) current);
        }
        if (current instanceof MoreExpensiveQuestion) {
            mainCtrl.showMoreExpensiveQuestion((MoreExpensiveQuestion) current);
        }
        /*
        Only now, when the currentController is set by the main controller
        (happens in one of the "show" methods above)
        , the default background should change.
        */
        setDefault();
        startTimer(currentController);
    }

    /**
     * Initializes a new instance of TimerThread and starts it.
     * Used at the beginning of each "scene-showing" process.
     *
     * @param questionScreen Controller for the corresponding scene to be visualized.
     */
    private void startTimer(QuestionScreen questionScreen) {
        ProgressBar time = questionScreen.getTime();
        long nextPhase = singlePlayerState.getNextPhase();
        /*
        The following line is used so no concurrent threads occur.
        Any existing ones are interrupted and thus, the task they execute are canceled.
         */
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
        timerThread = new TimerThread(time, nextPhase);
        timerThread.start();
    }

    /**
     * Updates the background of the current scene according to the correctness of the answer given.
     */
    public void revealAnswerCorrectness() {
        //startTimer(questionScreen);
        currentController.setScore(singlePlayerState.getPlayer().getScore());
        if (singlePlayerState.compareAnswer()) {
            currentController.getWindow()
                    .setStyle("-fx-background-color: #" + (Paint.valueOf("aedd94")).toString().substring(2));
        } else {
            currentController.getWindow()
                    .setStyle("-fx-background-color: #" + (Paint.valueOf("ff8a84")).toString().substring(2));
        }
    }

    /**
     * Used to change the color of the background of the current question scene to the initial navys color.
     * Also, updates the score the player have accumulated so far.
     */
    private void setDefault() {
        currentController.getWindow().setStyle(
                "-fx-background-color: #" + (Paint.valueOf("00236f")).toString().substring(2));
        currentController.setScore(singlePlayerState.getPlayer().getScore());
    }


    /**
     * Stops the current public service.
     * Used to end the polling once a player leaves a game.
     */
    public void stopPollingService() {
        pollingService.stop();
    }

    /**
     * Getter for the current game state.
     *
     * @return SinglePlayerState instance.
     */
    public SinglePlayerState getSinglePlayerState() {
        return singlePlayerState;
    }

    /**
     * Setter for the game state field.
     *
     * @param singlePlayerState Current game state in the player-game "relation".
     */
    public void setSinglePlayerState(SinglePlayerState singlePlayerState) {
        this.singlePlayerState = singlePlayerState;
    }

    /**
     * Getter for the current question screen controller.
     *
     * @return Instance of one of the subclasses of the QuestionScreen abstract class.
     */
    public QuestionScreen getCurrentController() {
        return currentController;
    }

    /**
     * Setter for the current question scene controller.
     *
     * @param currentController Instance of the current question controller.
     */
    public void setCurrentController(QuestionScreen currentController) {
        this.currentController = currentController;
    }

}
