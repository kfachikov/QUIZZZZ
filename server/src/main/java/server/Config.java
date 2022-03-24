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
package server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.utils.GenerateQuestionUtils;
import server.utils.MultiPlayerStateUtils;
import server.utils.SinglePlayerStateUtils;


import java.nio.channels.MulticastChannel;
import java.util.Random;

/**
 * Config class.
 */
@Configuration
public class Config {

    /**
     * Getter for a new random instance.
     *
     * @return new random instance.
     */
    @Bean
    public Random getRandom() {
        return new Random();
    }

    /**
     * Getter for a new instance of the GenerateQuestionUtils class.
     * Notated as bean, it would be only a single one used by all controllers/utilities.
     *
     * @return A new GenerateQuestionUtils instance.
     */
    @Bean
    public GenerateQuestionUtils getGenerateQuestionUtils() {
        return new GenerateQuestionUtils(getRandom());
    }

    /**
     * Getter for a new instance of SinglePlayerStateUtils.
     * Notated as bean, it would be only a single one used by all controllers.
     *
     * @return A new SinglePlayerStateUtils instance.
     */
    @Bean
    public SinglePlayerStateUtils getSinglePlayerStateUtils() { return new SinglePlayerStateUtils(getGenerateQuestionUtils()); }

    /**
     * Getter for a new instance of MultiPlayerStateUtils.
     * Notated as bean, it would be only a single one used by all controllers.
     *
     * @return A new MultiPlayerStateUtils instance.
     */
    @Bean
    public MultiPlayerStateUtils getMultiPlayerStateUtils() {
        return new MultiPlayerStateUtils(getGenerateQuestionUtils());
    }

}