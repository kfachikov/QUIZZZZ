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
package client.utils;

import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static String currentServer;

    public SinglePlayer addSinglePlayer(SinglePlayer singlePlayer) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(currentServer).path("/api/users") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(singlePlayer, APPLICATION_JSON), SinglePlayer.class);
    }

    public QueueState getQueueState() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/queue")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(QueueState.class);
    }

    public QueueUser addQueueUser(QueueUser user) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/queue")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), QueueUser.class);
    }

    public QueueUser deleteQueueUser(QueueUser user) {
        long id = user.id;
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue/" + String.valueOf(id))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(QueueUser.class);
    }

    /**
     * GET request to api/solo.
     *
     * Would be used for "constant" polling so that the game state is kept up to date.
     *
     * @return The current state of the ongoing game.
     */
    public GameState getSoloGameState(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/solo/" + String.valueOf(id))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(GameState.class);
    }

    /**
     * POST request to /api/solo/answer, to "submit" the answer chosen by the user.
     *
     * @param response Response object to be posted
     * @return The response object "posted"
     */
    public Response postAnswer(Response response) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("api/solo/answer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(response, APPLICATION_JSON), Response.class);
    }

    /**
     * POST request to /api/solo/start, to start the single-player game.
     *
     * @param singlePlayer SinglePlayer instance for the player that is about to begin a game.
     * @return The initial state of the game
     */
    public SinglePlayerState startSinglePlayerGame(SinglePlayer singlePlayer) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue/start")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(singlePlayer, APPLICATION_JSON), SinglePlayerState.class);
    }

    /**
     * POST request to /api/queue/start, to start the multiplayer game.
     *
     * @return The updated state of the game
     */
    public QueueState startMultiplayerGame() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue/start")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(null, QueueState.class);
    }

    public static String getCurrentServer() {
        return currentServer;
    }

    /**
     * Used to set the current server to which a client is connected to once they decide to play a game.
     *
     * @param currentServer A String representation of the server.
     */
    public static void setCurrentServer(String currentServer) {
        ServerUtils.currentServer = currentServer;
    }

    public List<Activity> getActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/activities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }
}