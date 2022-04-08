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
import client.scenes.multi.question.MultiGameConsumptionQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameGuessQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameInsteadQuestionScreenCtrl;
import client.scenes.multi.question.MultiGameMoreExpensiveQuestionScreenCtrl;
import client.scenes.single.CongratulationsScreenCtrl;
import client.scenes.single.PrepScreenCtrl;
import client.scenes.single.question.ConsumptionQuestionScreenCtrl;
import client.scenes.single.question.GuessQuestionScreenCtrl;
import client.scenes.single.question.InsteadQuestionScreenCtrl;
import client.scenes.single.question.MoreExpensiveQuestionScreenCtrl;
import client.services.SingleplayerGameStatePollingService;
import client.utils.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 *
 */
public class MyModule implements Module {

    /**
     * creates the binder user for injection.
     *
     * @param binder is the Binder variable
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(HomeScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PrepScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(HelpScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdministratorScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MoreExpensiveQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ConsumptionQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InsteadQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GuessQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CongratulationsScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(LeaderboardScreenCtrl.class).in(Scopes.SINGLETON);

        binder.bind(SingleplayerGameStatePollingService.class).in(Scopes.SINGLETON);
        binder.bind(SinglePlayerUtils.class).in(Scopes.SINGLETON);
        binder.bind(HomeUtils.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiGameConsumptionQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiGameGuessQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiGameInsteadQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiGameMoreExpensiveQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiGameMockScreenCtrl.class).in(Scopes.SINGLETON);

        binder.bind(ActivityImageUtils.class).in(Scopes.SINGLETON);
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
        binder.bind(InputPreloadUtils.class).in(Scopes.SINGLETON);

    }
}