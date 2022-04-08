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
import commons.misc.ActivityImageMessage;
import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.multi.ChatMessage;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
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

    private String currentServer;

    /**
     * @param leaderboardEntry is a SinglePlayerLeaderboardScore entity.
     * @return it returns a client SinglePlayerLeaderboardScore.
     */
    public SinglePlayerLeaderboardScore addSinglePlayer(SinglePlayerLeaderboardScore leaderboardEntry) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(currentServer)
                .path("/api/leaderboard/players") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(leaderboardEntry, APPLICATION_JSON), SinglePlayerLeaderboardScore.class);
    }

    /**
     * @return it returns a client QueueState.
     */
    public QueueState getQueueState() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue")
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
                .target(currentServer)
                .path("/api/queue")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), QueueUser.class);
    }

    /**
     * @param user is a QueueUser user
     * @return it returns a client QueueUser
     */
    public QueueUser deleteQueueUser(QueueUser user) {
        String username = user.getUsername();
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/queue/" + username)
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
     * GET request to /api/multi
     * <p>
     * Used for constant polling of the multiplayer game state.
     *
     * @param id Id of the multiplayer game.
     * @return Multiplayer game state for that id.
     */
    public MultiPlayerState getMultiGameState(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(MultiPlayerState.class);
    }

    /**
     * POST request to /api/solo/answer, to "submit" the answer chosen by the user.
     *
     * @param response GameResponse object to be posted
     * @return The response object "posted"
     */
    public GameResponse postAnswer(GameResponse response) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("api/solo/answer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(response, APPLICATION_JSON), GameResponse.class);
    }

    /**
     * POST request to /api/multi/answer, to "submit" the answer chosen by the user.
     *
     * @param response GameResponse object to be posted
     * @return The response object "posted"
     */
    public GameResponse postAnswerMultiplayer(GameResponse response) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("api/multi/answer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(response, APPLICATION_JSON), GameResponse.class);
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
     * POST request to api/multi/reaction to submit an emoji a client clicked.
     *
     * @param id        id of current multiplayer game
     * @param chatMessage  ChatMessage instance to be submitted
     * @return          ChatMessage that was added to the particular game.
     */
    public ChatMessage addReaction(long id, ChatMessage chatMessage) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/reaction/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(chatMessage, APPLICATION_JSON), ChatMessage.class);
    }

    /**
     * POST request to api/multi/joker to register a joker usage once a client clicks.
     *
     * @param id            id of current multiplayer game
     * @param chatMessage   ChatMessage instance to be submitted - consist a String "reference"
     *                      to the corresponding Joker used.
     * @return              ChatMessage that was added to the particular game.
     */
    public ChatMessage addJoker(long id, ChatMessage chatMessage) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/joker/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(chatMessage, APPLICATION_JSON), ChatMessage.class);
    }

    /**
     * POST request to /api/multi/ to add a multiplayer user.
     *
     * @param id          Id of the multiplayer game.
     * @param multiPlayer Multiplayer user to be added
     * @return Multiplayer player that was added.
     */
    public MultiPlayer addMultiPlayer(long id, MultiPlayer multiPlayer) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/players/" + String.valueOf(id))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(multiPlayer, APPLICATION_JSON), MultiPlayer.class);
    }

    /**
     * @return it returns a currentServer.
     */
    public String getCurrentServer() {
        return currentServer;
    }

    /**
     * Used to set the current server to which a client is connected to once they decide to play a game.
     *
     * @param currentServer A String representation of the server.
     */
    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    /**
     * @return it returns a client GenericType List Activity.
     */
    public List<Activity> getActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {
                });
    }

    /**
     * The method imports activities locally using the admin panel.
     *
     * @param fileAsString string representing the String version of a file.
     *
     * @return list of activities.
     */
    public List<Activity> importActivities(String fileAsString) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/addToRepo")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(fileAsString, APPLICATION_JSON), new GenericType<List<Activity>>() {
                });
    }

    /**
     * The method finds the activity in the repo using the provided key.
     * The method then edits its fields to be same as the provided Activity.
     *
     * @param key the activity to be edited.
     * @param newActivity a dummy activity with new values to be copied.
     * @return the new Activity.
     */
    public Activity changeActivity(Long key, Activity newActivity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/" + key)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(newActivity, APPLICATION_JSON), Activity.class);
    }

    /**
     * Removes the activity the passed key is associated with.
     *
     * @param key the key of the activity to be removed.
     * @return the removed activity.
     */
    public Activity removeActivity(Long key) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/delete/" + key)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(Activity.class);
    }

    /**
     * Getter for the activity image.
     *
     * @param key the key of the image.
     * @return ActivityImageMessage
     */
    public ActivityImageMessage getActivityImage(long key) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/images/" + key)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(ActivityImageMessage.class);
    }

    /**
     * Adds a new ActivityImage to the image repository.
     *
     * @param message the activity image message.
     * @return ActivityImageMessage
     */
    public ActivityImageMessage addActivityImage(ActivityImageMessage message) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/activities/images/" + message.getKey())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(message, APPLICATION_JSON), ActivityImageMessage.class);
    }

    /**
     * @return it returns a list SinglePlayerLeaderboardScore.
     */
    public List<SinglePlayerLeaderboardScore> getLeaderboardEntries() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/leaderboard/players")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<SinglePlayerLeaderboardScore>>() {
                });
    }

    /**
     * @return it returns a client SinglePlayerLeaderboardScore.
     *
     * @param leaderboardEntry is a SinglePlayerLeaderboardScore entry.
     */
    public SinglePlayerLeaderboardScore postLeaderboardEntry(SinglePlayerLeaderboardScore leaderboardEntry) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(currentServer)
                .path("/api/leaderboard/players")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(leaderboardEntry, APPLICATION_JSON), SinglePlayerLeaderboardScore.class);
    }
    
    /**
     * Checks if the server exists by sending a get request.
     *
     * @return it returns a boolean value
     */
    public Boolean checkServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/solo")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }

    /**
     * POST request to /api/multi/timeJoker/{id}, to update the multiplayer.
     *
     * @param player the player who used the joker.
     * @param id     id of the multiplayer game.
     */
    public void postTimeJokerPlayer(MultiPlayer player, long id) {
        ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/timeJoker/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON));
    }

    /**
     * GET request to /api/multi/timeJoker/{id}
     * <p>
     *  Used to receive the player who clicked the time joker button.
     *
     * @param id id of the multiplayer game.
     * @return   the player who used the joker.
     */
    public MultiPlayer getTimeJokerPlayer(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(currentServer)
                .path("/api/multi/timeJoker/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(MultiPlayer.class);
    }

}
