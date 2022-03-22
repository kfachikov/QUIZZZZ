/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes.misc;

import client.scenes.multi.MultiGameQuestionScreenCtrl;
import client.scenes.multi.QueueScreenCtrl;
import client.scenes.single.*;
import client.services.GameStatePollingService;
import client.utils.TimerThread;
import commons.misc.GameState;
import commons.question.*;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;

import static commons.single.SinglePlayerState.*;

public class MainCtrl {

    private Stage primaryStage;

    private HomeScreenCtrl homeCtrl;
    private Scene home;

    private PrepScreenCtrl prepCtrl;
    private Scene prep;

    private HelpScreenCtrl helpCtrl;
    private Scene help;

    private QueueScreenCtrl queueCtrl;
    private Scene queue;

    private AdministratorScreenCtrl administratorCtrl;
    private Scene administrator;

    private MultiGameQuestionScreenCtrl multiGameCtrl;
    private Scene multiGame;

    private MoreExpensiveQuestionScreenCtrl moreExpensiveCtrl;
    private Scene moreExpensive;

    private ConsumptionQuestionScreenCtrl consumptionCtrl;
    private Scene consumption;

    private InsteadQuestionScreenCtrl insteadCtrl;
    private Scene instead;

    private GuessQuestionScreenCtrl guessCtrl;
    private Scene guess;


    /*
    Instances used for the single-player mode to extract polling service functionality.
    SinglePlayer and SinglePlayerState instances of the current pair Player-Game on the client-side.
     */
    private GameStatePollingService pollingService;
    private SinglePlayer singlePlayer;
    private SinglePlayerState singlePlayerState;

    private TimerThread timerThread;

    /**
     * @param primaryStage is the Stage representing the initial stage variable.
     * @param home         is the home screen pair variable
     * @param help         is the help screen pair variable
     * @param prep         is the prepare screen pair variable
     * @param queue        is the queue screen pair variable
     * @param administrator is the administrator panel screen panel pair variable
     * @param multiGame     is the multiplayer game screen pair variable
     * @param moreExpensive is the moreExpensiveQuestion screen pair variable
     * @param consumption is the consumptionQuestion screen pair variable
     * @param instead is the insteadQuestion screen pair variable
     * @param guess is the guessQuestion screen pair variable
     */
    public void initialize(Stage primaryStage,
                           Pair<HomeScreenCtrl, Parent> home,
                           Pair<HelpScreenCtrl, Parent> help,
                           Pair<PrepScreenCtrl, Parent> prep,
                           Pair<QueueScreenCtrl, Parent> queue,
                           Pair<AdministratorScreenCtrl, Parent> administrator,
                           Pair<MultiGameQuestionScreenCtrl, Parent> multiGame,
                           Pair<MoreExpensiveQuestionScreenCtrl, Parent> moreExpensive,
                           Pair<ConsumptionQuestionScreenCtrl, Parent> consumption,
                           Pair<InsteadQuestionScreenCtrl, Parent> instead,
                           Pair<GuessQuestionScreenCtrl, Parent> guess) {

        this.primaryStage = primaryStage;

        this.homeCtrl = home.getKey();
        this.home = new Scene(home.getValue());

        this.prepCtrl = prep.getKey();
        this.prep = new Scene(prep.getValue());

        this.helpCtrl = help.getKey();
        this.help = new Scene(help.getValue());

        this.queueCtrl = queue.getKey();
        this.queue = new Scene(queue.getValue());

        this.administratorCtrl = administrator.getKey();
        this.administrator = new Scene(administrator.getValue());

        this.multiGameCtrl = multiGame.getKey();
        this.multiGame = new Scene(multiGame.getValue());

        this.moreExpensiveCtrl = moreExpensive.getKey();
        this.moreExpensive = new Scene(moreExpensive.getValue());

        this.consumptionCtrl = consumption.getKey();
        this.consumption = new Scene(consumption.getValue());

        this.insteadCtrl = instead.getKey();
        this.instead = new Scene(instead.getValue());

        this.guessCtrl = guess.getKey();
        this.guess = new Scene(guess.getValue());

        showHome();
        primaryStage.show();

        primaryStage.setOnCloseRequest((event -> {
            if (primaryStage.getScene().equals(this.queue)) {
                queueCtrl.leaveQueue();
            }
            Platform.exit();
        }));
    }

    /**
     * sets the title and the scene as home.
     */
    public void showHome() {
        primaryStage.setTitle("Quizzz: Home");
        primaryStage.setScene(home);
    }

    /**
     * sets the title and the scene as prep.
     */
    public void showPrep(SinglePlayer singlePlayer) {
        primaryStage.setTitle("Quizzz: Prepare");
        primaryStage.setScene(prep);
        prepCtrl.setSinglePlayer(singlePlayer);
    }

    /**
     * sets the title and the scene as help.
     */
    public void showHelp() {
        primaryStage.setTitle("Quizzz: Help");
        primaryStage.setScene(help);
    }

    /**
     * Method called from the PrepScreenCtrl once the "GO!" button is pressed.
     * Passed as arguments are the instance for the current player, and the game he is "connected" to.
     *
     * Initializes both polling service, fields in separate screen controllers, and makes the initial call
     * so the first question is shown.
     *
     * @param singlePlayer      Player instance consisting of username and initial score - 0
     * @param singlePlayerState GameState instance consisting of all information required for a game.
     */
    public void playSoloGame(SinglePlayer singlePlayer, SinglePlayerState singlePlayerState) {
        this.singlePlayer = singlePlayer;
        this.singlePlayerState = singlePlayerState;
        this.pollingService = consumptionCtrl.getPollingService();

        initializePollingService();
        initializeSoloControllers();

        showNextQuestionSinglePlayer();
    }

    /**
     * The instances of SinglePlayer and SinglePlayerState are assigned to the corresponding
     * fields in the single-player game mode controllers.
     */
    public void initializeSoloControllers() {
        consumptionCtrl.setSinglePlayer(singlePlayer);
        consumptionCtrl.setSinglePlayerState(singlePlayerState);

        guessCtrl.setSinglePlayer(singlePlayer);
        guessCtrl.setSinglePlayerState(singlePlayerState);

        insteadCtrl.setSinglePlayer(singlePlayer);
        insteadCtrl.setSinglePlayerState(singlePlayerState);

        moreExpensiveCtrl.setSinglePlayer(singlePlayer);
        moreExpensiveCtrl.setSinglePlayerState(singlePlayerState);
    }

    /**
     * The polling service is initialized by receiving the GameState it should pull from
     * the server constantly (every 500 milliseconds).
     *
     * A listener is assigned to its property which looks for changes of the
     * GameState instance on the server.
     *
     * The polling service is started.
     */
    public void initializePollingService() {
        pollingService.setSinglePlayerState(singlePlayerState);

        pollingService.valueProperty().addListener(((observable, oldGameState, newGameState) -> {
            if (newGameState != null) {
                singlePlayerState = (SinglePlayerState) newGameState;
                updateSinglePlayerState();
                switch (newGameState.getState()) {
                case QUESTION_STATE:
                    setDefaultQuestionBackground();
                    showNextQuestionSinglePlayer();
                    break;
                case TRANSITION_STATE:
                    updateBackground(typeCurrentQuestion(newGameState));
                    break;
                case GAME_OVER_STATE:
                    pollingService.stop();
                    break;
                }
            }
        }));

        pollingService.start();
    }

    /**
     * Updates the SinglePlayerState instances of the controllers after
     * receiving the new value from the polling service.
     */
    public void updateSinglePlayerState() {
        consumptionCtrl.setSinglePlayerState(singlePlayerState);
        guessCtrl.setSinglePlayerState(singlePlayerState);
        insteadCtrl.setSinglePlayerState(singlePlayerState);
        moreExpensiveCtrl.setSinglePlayerState(singlePlayerState);
    }

    /**
     * Checks the type of the current question, so that it's background color can be changed later.
     *
     * @param gameState Current game state object - the game the client is playing.
     * @return An instance of the current question screen controller.
     */
    public QuestionScreen typeCurrentQuestion(GameState gameState) {
        AbstractQuestion currentQuestion = gameState.getQuestionList().get(gameState.getRoundNumber());
        if (currentQuestion instanceof ConsumptionQuestion) {
            return consumptionCtrl;
        } else if (currentQuestion instanceof InsteadQuestion) {
            return insteadCtrl;
        } else if (currentQuestion instanceof GuessQuestion) {
            return guessCtrl;
        } else {
            return moreExpensiveCtrl;
        }
    }

    /**
     * Updates the background of the current scene according to the correctness of the answer given.
     * @param questionScreen Controller of the current question scene.
     */
    public void updateBackground(QuestionScreen questionScreen) {
        startTimer(questionScreen);
        questionScreen.setScore(singlePlayerState.getPlayer().getScore());
        if (questionScreen.compareAnswer()) {
            questionScreen.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("aedd94")).toString().substring(2));
        } else {
            questionScreen.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("ff8a84")).toString().substring(2));
        }
    }

    /**
     * "Reveals" the next question on the client-side.
     * Checks its type and decides on the scene to be loaded accordingly.
     */
    public void showNextQuestionSinglePlayer() {
        primaryStage.setTitle("Quizzz: Single-player Game");
        AbstractQuestion current = singlePlayerState.getQuestionList().get(singlePlayerState.getRoundNumber());
        if (current instanceof ConsumptionQuestion) {
            showConsumptionQuestion((ConsumptionQuestion) current);
        }
        if (current instanceof GuessQuestion) {
            showGuessQuestion((GuessQuestion) current);
        }
        if (current instanceof InsteadQuestion) {
            showInsteadQuestion((InsteadQuestion) current);
        }
        if (current instanceof MoreExpensiveQuestion) {
            showMoreExpensiveQuestion((MoreExpensiveQuestion) current);
        }
    }

    /**
     * Initializes a new instance of TimerThread and starts it.
     * Used at the beginning of each "scene-showing" process.
     *
     * @param questionScreen    Controller for the corresponding scene to be visualized.
     */
    private void startTimer (QuestionScreen questionScreen) {
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
     * Sets the current scene to the queue screen, starts the queue polling
     * service and initializes the queue scene controller with
     * the QueueUser instance of the person joining the queue.
     *
     * @param user QueueUser which is joining the queue
     */
    public void showQueue(QueueUser user) {
        primaryStage.setTitle("Quizzz: Queue");
        primaryStage.setScene(queue);
        queueCtrl.getPollingService().start();
        queueCtrl.setUser(user);
        queueCtrl.setServerAddress(homeCtrl.getServer());
        queueCtrl.resetScene();
    }

    /**
     * Set the current scene to Multiplayer game question screen.
     *
     * @param id        Multiplayer game id
     * @param queueUser QueueUser of the user who was just in the queue
     */
    public void showMultiGameQuestion(long id, QueueUser queueUser) {
        primaryStage.setTitle("Quizzz: Multi-player Game");
        primaryStage.setScene(multiGame);
        multiGameCtrl.setGameId(id);
    }

    public void showAdministrator() {
        primaryStage.setTitle("Quizzz: Administrator Panel");
        primaryStage.setScene(administrator);
    }

    public String chooseFile(Button selectFileButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile =  fileChooser.showOpenDialog(null);
        return selectedFile.getName();
    }

    /**
     * "Redirects" the client to the scene of MoreExpensiveQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        moreExpensiveCtrl.setQuestion(question);
        primaryStage.setScene(moreExpensive);
        startTimer(moreExpensiveCtrl);
    }

    /**
     * "Redirects" the client to the scene of ConsumptionQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showConsumptionQuestion(ConsumptionQuestion question) {
        consumptionCtrl.setQuestion(question);
        primaryStage.setScene(consumption);
        startTimer(consumptionCtrl);
    }

    /**
     * "Redirects" the client to the scene of InsteadQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showInsteadQuestion(InsteadQuestion question) {
        insteadCtrl.setQuestion(question);
        primaryStage.setScene(instead);
        startTimer(insteadCtrl);
    }

    /**
     * "Redirects" the client to the scene of GuessQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showGuessQuestion(GuessQuestion question) {
        guessCtrl.setQuestion(question);
        primaryStage.setScene(guess);
        startTimer(guessCtrl);
    }

    /**
     * Used to change the color of the background of all question scenes to the initial blue color.
     */
    private void setDefaultQuestionBackground() {
        consumptionCtrl.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("a8c6fa")).toString().substring(2));
        insteadCtrl.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("a8c6fa")).toString().substring(2));
        moreExpensiveCtrl.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("a8c6fa")).toString().substring(2));
        guessCtrl.getWindow().setStyle("-fx-background-color: #" + (Paint.valueOf("a8c6fa")).toString().substring(2));
    }

}
