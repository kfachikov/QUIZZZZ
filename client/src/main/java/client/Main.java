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
package client;

import client.scenes.misc.AdministratorScreenCtrl;
import client.scenes.misc.HelpScreenCtrl;
import client.scenes.misc.HomeScreenCtrl;
import client.scenes.misc.MainCtrl;
import client.scenes.multi.LeaderboardScreenCtrl;
import client.scenes.multi.MultiGameMockScreenCtrl;
import client.scenes.multi.MultiplayerCtrl;
import client.scenes.multi.QueueScreenCtrl;
import client.scenes.multi.question.MultiGameConsumptionQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameGuessQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameInsteadQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameMoreExpensiveQuestionScreenCtrl;
import client.scenes.single.*;
import client.scenes.single.question.ConsumptionQuestionScreenCtrl;
import client.scenes.single.question.GuessQuestionScreenCtrl;
import client.scenes.single.question.InsteadQuestionScreenCtrl;
import client.scenes.single.question.MoreExpensiveQuestionScreenCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

/**
 *
 */
public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * @param args is a String[] args
     * @throws URISyntaxException is an exception
     * @throws IOException        is an exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var home = FXML.load(
                HomeScreenCtrl.class, "client", "scenes", "misc", "HomeScreen.fxml");
        var prep = FXML.load(
                PrepScreenCtrl.class, "client", "scenes", "single", "PrepScreen.fxml");
        var help = FXML.load(
                HelpScreenCtrl.class, "client", "scenes", "misc", "HelpScreen.fxml");
        var congratulations = FXML.load(
                CongratulationsScreenCtrl.class, "client", "scenes", "single", "CongratulationsScreen.fxml");
        var queue = FXML.load(
                QueueScreenCtrl.class, "client", "scenes", "multi", "QueueScreen.fxml");
        var administrator = FXML.load(
                AdministratorScreenCtrl.class, "client", "scenes", "misc", "AdministratorScreen.fxml");
        var moreExpensive = FXML.load(
                MoreExpensiveQuestionScreenCtrl.class, "client", "scenes", "single",
                "question", "MoreExpensiveQuestionScreen.fxml");
        var consumption = FXML.load(
                ConsumptionQuestionScreenCtrl.class, "client", "scenes", "single",
                "question", "ConsumptionQuestionScreen.fxml");
        var instead = FXML.load(
                InsteadQuestionScreenCtrl.class, "client", "scenes", "single",
                "question", "InsteadQuestionScreen.fxml");
        var guess = FXML.load(
                GuessQuestionScreenCtrl.class, "client", "scenes", "single",
                "question", "GuessQuestionScreen.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, home, help, prep, queue, administrator,
                moreExpensive, consumption, instead, guess, congratulations);


        var consumptionMulti = FXML.load(
                MultiGameConsumptionQuestionScreenCtrl.class, "client", "scenes", "multi", "question",
                "MultiGameConsumptionQuestionScreen.fxml");
        var guessMulti = FXML.load(
                MultiGameGuessQuestionScreenCtrl.class, "client", "scenes", "multi", "question",
                "MultiGameGuessQuestionScreen.fxml");
        var insteadMulti = FXML.load(
                MultiGameInsteadQuestionScreenCtrl.class, "client", "scenes", "multi", "question",
                "MultiGameInsteadQuestionScreen.fxml");
        var moreExpensiveMulti = FXML.load(
                MultiGameMoreExpensiveQuestionScreenCtrl.class, "client", "scenes", "multi", "question",
                "MultiGameMoreExpensiveQuestionScreen.fxml");
        var mockMulti = FXML.load(
                MultiGameMockScreenCtrl.class, "client", "scenes", "multi",
                "MultiGameMockScreen.fxml");
        var leaderboard = FXML.load(
                LeaderboardScreenCtrl.class, "client", "scenes", "multi",
                "LeaderboardScreen.fxml");

        var multiCtrl = INJECTOR.getInstance(MultiplayerCtrl.class);
        multiCtrl.initialize(consumptionMulti, guessMulti, insteadMulti, moreExpensiveMulti, mockMulti, leaderboard);
    }
}