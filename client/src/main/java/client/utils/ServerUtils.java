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

import commons.QueueState;
import commons.QueueUser;
import commons.SingleUser;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static String currentServer;

    public SingleUser addUser(SingleUser user) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(currentServer).path("/api/users") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(user, APPLICATION_JSON), SingleUser.class);
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
}