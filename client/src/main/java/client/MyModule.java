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
import client.scenes.single.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

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
        binder.bind(SoloGameQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdministratorScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MoreExpensiveQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ConsumptionQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InsteadQuestionScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GuessQuestionScreenCtrl.class).in(Scopes.SINGLETON);
    }
}