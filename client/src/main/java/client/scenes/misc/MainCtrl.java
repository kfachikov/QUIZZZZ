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

import client.scenes.multi.QueueScreenCtrl;
import client.scenes.single.CongratulationsScreenCtrl;
import client.scenes.single.PrepScreenCtrl;
import client.scenes.single.question.ConsumptionQuestionScreenCtrl;
import client.scenes.single.question.GuessQuestionScreenCtrl;
import client.scenes.single.question.InsteadQuestionScreenCtrl;
import client.scenes.single.question.MoreExpensiveQuestionScreenCtrl;
import client.utils.SinglePlayerUtils;
import commons.question.ConsumptionQuestion;
import commons.question.GuessQuestion;
import commons.question.InsteadQuestion;
import commons.question.MoreExpensiveQuestion;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.function.Supplier;

/**
 * Main controller which would take care of all scene and controller changes.
 */
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

    private MoreExpensiveQuestionScreenCtrl moreExpensiveCtrl;
    private Scene moreExpensive;

    private ConsumptionQuestionScreenCtrl consumptionCtrl;
    private Scene consumption;

    private InsteadQuestionScreenCtrl insteadCtrl;
    private Scene instead;

    private GuessQuestionScreenCtrl guessCtrl;
    private Scene guess;

    private CongratulationsScreenCtrl congratulationsCtrl;
    private Scene congratulations;


    /*
    Instance of the utility class for the single-player game mode.
    Would be used for some in-game logic.
     */
    private SinglePlayerUtils singlePlayerUtils;


    /**
     * @param primaryStage    is the Stage representing the initial stage variable.
     * @param home            is the home screen pair variable
     * @param help            is the help screen pair variable
     * @param prep            is the prepare screen pair variable
     * @param queue           is the queue screen pair variable
     * @param administrator   is the administrator panel screen panel pair variable
     * @param moreExpensive   is the moreExpensiveQuestion screen pair variable
     * @param consumption     is the consumptionQuestion screen pair variable
     * @param instead         is the insteadQuestion screen pair variable
     * @param guess           is the guessQuestion screen pair variable
     * @param congratulations is the congratulations screen pair variable
     */
    public void initialize(Stage primaryStage,
                           Pair<HomeScreenCtrl, Parent> home,
                           Pair<HelpScreenCtrl, Parent> help,
                           Pair<PrepScreenCtrl, Parent> prep,
                           Pair<QueueScreenCtrl, Parent> queue,
                           Pair<AdministratorScreenCtrl, Parent> administrator,
                           Pair<MoreExpensiveQuestionScreenCtrl, Parent> moreExpensive,
                           Pair<ConsumptionQuestionScreenCtrl, Parent> consumption,
                           Pair<InsteadQuestionScreenCtrl, Parent> instead,
                           Pair<GuessQuestionScreenCtrl, Parent> guess,
                           Pair<CongratulationsScreenCtrl, Parent> congratulations
    ) {

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

        this.moreExpensiveCtrl = moreExpensive.getKey();
        this.moreExpensive = new Scene(moreExpensive.getValue());

        this.consumptionCtrl = consumption.getKey();
        this.consumption = new Scene(consumption.getValue());

        this.insteadCtrl = instead.getKey();
        this.instead = new Scene(instead.getValue());

        this.guessCtrl = guess.getKey();
        this.guess = new Scene(guess.getValue());

        this.congratulationsCtrl = congratulations.getKey();
        this.congratulations = new Scene(congratulations.getValue());

        setStylesheets();
        resetDefaultOnCloseRequest();

        showHome();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Reset the behaviour of onCloseRequest to expected behavior.
     */
    public void resetDefaultOnCloseRequest() {
        primaryStage.setOnCloseRequest((event -> {
            if (primaryStage.getScene().equals(this.queue)) {
                queueCtrl.leaveQueue();
            }
            Platform.exit();
        }));
    }

    /**
     * Set the action to do when attempting to close the application.
     * <p>
     * Takes a boolean supplier (which could, possibly, show a prompt to the user),
     * which returns whether user selected "Yes" to leave the game.
     * <p>
     * If the boolean supplier returns true, the application exits.
     *
     * @param onCloseRequest Boolean supplier which return true if the application should close.
     */
    public void setOnCloseRequest(Supplier<Boolean> onCloseRequest) {
        primaryStage.setOnCloseRequest(event -> {
            if (onCloseRequest.get()) {
                Platform.exit();
            } else {
                event.consume();
            }
        });
    }

    /**
     * sets the stylesheets.
     */
    public void setStylesheets() {
        String CSSPath = "styling/GameStyle.css";

        home.getStylesheets().add(CSSPath);
        prep.getStylesheets().add(CSSPath);
        help.getStylesheets().add(CSSPath);
        queue.getStylesheets().add(CSSPath);
        administrator.getStylesheets().add(CSSPath);
        moreExpensive.getStylesheets().add(CSSPath);
        guess.getStylesheets().add(CSSPath);
        consumption.getStylesheets().add(CSSPath);
        instead.getStylesheets().add(CSSPath);
        congratulations.getStylesheets().add(CSSPath);
    }

    /**
     * Getter for the primary state of the application.
     *
     * @return Primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * sets the title and the scene as home.
     */
    public void showHome() {
        primaryStage.setTitle("Quizzz: Home");
        primaryStage.setScene(home);
        resetDefaultOnCloseRequest();
    }

    /**
     * sets the title and the scene as prep.
     *
     * @param singlePlayer Player who is joining the game.
     */
    public void showPrep(SinglePlayer singlePlayer) {
        prepCtrl.setUp();
        primaryStage.setTitle("Quizzz: Prepare");
        primaryStage.setScene(prep);
        prepCtrl.setSinglePlayer(singlePlayer);
        resetDefaultOnCloseRequest();
    }

    /**
     * sets the title and the scene as help.
     */
    public void showHelp() {
        primaryStage.setTitle("Quizzz: Help");
        primaryStage.setScene(help);
        resetDefaultOnCloseRequest();
    }

    /**
     * Method called from the PrepScreenCtrl once the "GO!" button is pressed.
     * Passed as arguments are the instance for the current player, and the game he is "connected" to.
     * <p>
     * Initializes both polling service, fields in separate screen controllers, and makes the initial call
     * so the first question is shown.
     *
     * @param singlePlayerUtils is the shared single-player utility instance.
     */
    public void playSoloGame(SinglePlayerUtils singlePlayerUtils) {
        this.singlePlayerUtils = singlePlayerUtils;
        singlePlayerUtils.chooseNextQuestion();
        primaryStage.setTitle("Quizzz: Single-player Game");
    }

    /**
     * Sets the current scene to the queue screen, starts the queue polling
     * service and initializes the queue scene controller with
     * the QueueUser instance of the person joining the queue.
     *
     * @param user          QueueUser which is joining the queue
     * @param serverAddress server address to be shown in the queue screen
     */
    public void showQueue(QueueUser user, String serverAddress) {
        primaryStage.setTitle("Quizzz: Queue");
        primaryStage.setScene(queue);
        queueCtrl.getPollingService().start();
        queueCtrl.setUser(user);
        queueCtrl.setServerAddress(serverAddress);
        queueCtrl.resetScene();
        resetDefaultOnCloseRequest();
    }

    /**
     * sets the title and the scene as Administrator Panel screen.
     */
    public void showAdministrator() {
        administratorCtrl.setup();
        
        primaryStage.setTitle("Quizzz: Administrator Panel");
        primaryStage.setScene(administrator);
        resetDefaultOnCloseRequest();
    }

    /**
     * @param selectFileButton is a Button.
     * @return it returns a name with the selectedFile.getName method
     */
    public String chooseFile(Button selectFileButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile.getName();
    }

    /**
     * "Redirects" the client to the scene of MoreExpensiveQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        singlePlayerUtils.setCurrentController(moreExpensiveCtrl);
        moreExpensiveCtrl.setQuestion(question);
        primaryStage.setScene(moreExpensive);
        setOnCloseRequest(moreExpensiveCtrl::returnHome);
    }

    /**
     * "Redirects" the client to the scene of ConsumptionQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showConsumptionQuestion(ConsumptionQuestion question) {
        singlePlayerUtils.setCurrentController(consumptionCtrl);
        consumptionCtrl.setQuestion(question);
        primaryStage.setScene(consumption);
        setOnCloseRequest(consumptionCtrl::returnHome);
    }

    /**
     * "Redirects" the client to the scene of InsteadQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showInsteadQuestion(InsteadQuestion question) {
        singlePlayerUtils.setCurrentController(insteadCtrl);
        insteadCtrl.setQuestion(question);
        primaryStage.setScene(instead);
        setOnCloseRequest(insteadCtrl::returnHome);
    }

    /**
     * "Redirects" the client to the scene of GuessQuestion type.
     *
     * @param question Question to be loaded - the next from the sequence.
     */
    public void showGuessQuestion(GuessQuestion question) {
        singlePlayerUtils.setCurrentController(guessCtrl);
        guessCtrl.setQuestion(question);
        primaryStage.setScene(guess);
        setOnCloseRequest(insteadCtrl::returnHome);
    }

    /**
     * Sets the current scene to Congratulations screen.
     */
    public void showCongratulations() {
        primaryStage.setTitle("Quizzz: Congratulations");
        congratulationsCtrl.setPoints();
        primaryStage.setScene(congratulations);
        resetDefaultOnCloseRequest();
    }

    /**
     * Getter for the home screen controller instance.
     *
     * @return  HomeScreenController reference to the only one
     *          instance controlling the home screen.
     */
    public HomeScreenCtrl getHomeCtrl() {
        return homeCtrl;
    }

}
