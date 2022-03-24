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

import commons.misc.Activity;
import commons.misc.GameState;
import commons.misc.Response;
import commons.queue.QueueState;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import commons.single.SinglePlayerState;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 *
 */
public class ServerUtils {

    private static String currentServer;

    /**
     * @param leaderboardEntry is a SinglePlayerLeaderboardScore entity.
     * @return it returns a client SinglePlayerLeaderboardScore.
     */
    public SinglePlayerLeaderboardScore addSinglePlayer(SinglePlayerLeaderboardScore leaderboardEntry) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(currentServer).path("/api/leaderboard/players") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(leaderboardEntry, APPLICATION_JSON), SinglePlayerLeaderboardScore.class);
    }

    /**
     * @return it returns a client QueueState.
     */
    public QueueState getQueueState() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/queue")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(QueueState.class);
    }

    /**
     * @param user is a QueueUser user.
     * @return it returns a client QueueUser user
     */
    public QueueUser addQueueUser(QueueUser user) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/queue")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), QueueUser.class);
    }

    /**
     * @param user is a QueueUser user
     * @return it returns a client QueueUser
     */
    public QueueUser deleteQueueUser(QueueUser user) {
        long id = user.getId();
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue/" + String.valueOf(id))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(QueueUser.class);
    }

    /**
     * GET request to api/solo.
     * <p>
     * Would be used for "constant" polling so that the game state is kept up to date.
     *
     * @param id Id of the game.
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

    /*
    Currently, the following method does not work as intended.
    There is a problem with the "object mapper" for the question classes.
    Should have some default constructors.
    */

    /**
     * POST request to /api/solo/start, to start the single-player game.
     *
     * @param singlePlayer SinglePlayer instance for the player that is about to begin a game.
     * @return The initial state of the game
     */
    public SinglePlayerState startSinglePlayerGame(SinglePlayer singlePlayer) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/solo/start")
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

    /**
     * @return it returns a currentServer.
     */
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

    /**
     * @return it returns a client GenericType List Activity.
     */
    public List<Activity> getActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer).path("/api/activities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {
                });
    }

    public List<Activity> importActivities(String fileAsString) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/addToRepo")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(fileAsString, APPLICATION_JSON), new GenericType<List<Activity>>() {});
    }
}
