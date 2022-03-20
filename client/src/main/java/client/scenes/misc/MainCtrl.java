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
import commons.question.*;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.List;

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

    /**
     * @param primaryStage is the Stage representing the initial stage variable.
     * @param home         is the home screen pair variable
     * @param help         is the help screen pair variable
     * @param prep         is the prepare screen pair variable
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
     * sets the title and the scene as single-player game.
     */
    public synchronized void showSoloGameQuestion(SinglePlayer singlePlayer, SinglePlayerState singlePlayerState) {
        primaryStage.setTitle("Quizzz: Single-player Game");
        AbstractQuestion current = singlePlayerState.getQuestionList().get(singlePlayerState.getRoundNumber());
        if (current instanceof ConsumptionQuestion) {
            consumptionCtrl.getPollingService().stop();
            consumptionCtrl.startTimer();
            consumptionCtrl.setSinglePlayer(singlePlayer);
            consumptionCtrl.setSinglePlayerState(singlePlayerState);
            showConsumptionQuestion((ConsumptionQuestion) current);
            consumptionCtrl.getPollingService().setSinglePlayerState(singlePlayerState);
            consumptionCtrl.getPollingService().start();
        }
        if (current instanceof GuessQuestion) {
            guessCtrl.getPollingService().stop();
            guessCtrl.startTimer();
            guessCtrl.setSinglePlayer(singlePlayer);
            guessCtrl.setSinglePlayerState(singlePlayerState);
            showGuessQuestion((GuessQuestion) current);
            guessCtrl.getPollingService().setSinglePlayerState(singlePlayerState);
            guessCtrl.getPollingService().start();
        }
        if (current instanceof InsteadQuestion) {
            insteadCtrl.getPollingService().stop();
            insteadCtrl.startTimer();
            insteadCtrl.setSinglePlayer(singlePlayer);
            insteadCtrl.setSinglePlayerState(singlePlayerState);
            showInsteadQuestion((InsteadQuestion) current);
            insteadCtrl.getPollingService().setSinglePlayerState(singlePlayerState);
            insteadCtrl.getPollingService().start();
        }
        if (current instanceof MoreExpensiveQuestion) {
            moreExpensiveCtrl.getPollingService().stop();
            moreExpensiveCtrl.startTimer();
            moreExpensiveCtrl.setSinglePlayer(singlePlayer);
            moreExpensiveCtrl.setSinglePlayerState(singlePlayerState);
            showMoreExpensiveQuestion((MoreExpensiveQuestion) current);
            moreExpensiveCtrl.getPollingService().setSinglePlayerState(singlePlayerState);
            moreExpensiveCtrl.getPollingService().start();
        }
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
     * sets the title and the scene as moreExpensiveQuestion screen.
     */
    public void showMoreExpensiveQuestion(MoreExpensiveQuestion question) {
        //moreExpensiveCtrl.setQuestion(question);
        primaryStage.setScene(moreExpensive);
        moreExpensiveCtrl.startTimer();
    }

    /**
     * sets the title and the scene as consumptionQuestion screen.
     */
    public void showConsumptionQuestion(ConsumptionQuestion question) {
        //consumptionCtrl.setQuestion(question);
        primaryStage.setScene(consumption);
        consumptionCtrl.startTimer();
    }

    /**
     * sets the title and the scene as insteadQuestion screen.
     */
    public void showInsteadQuestion(InsteadQuestion question) {
        //insteadCtrl.setQuestion(question);
        primaryStage.setScene(instead);
        insteadCtrl.startTimer();
    }

    /**
     * sets the title and the scene as guessQuestion screen.
     */
    public void showGuessQuestion(GuessQuestion question) {
        //guessCtrl.setQuestion(question);
        primaryStage.setScene(guess);
        insteadCtrl.startTimer();
    }
}
