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

import commons.MultiUserQueue;
import commons.SingleUser;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    public SingleUser addUser(SingleUser user) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/users") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(user, APPLICATION_JSON), SingleUser.class);
    }

    public List<MultiUserQueue> getQueueUsers() {
        GenericType<List<MultiUserQueue>> genericType = new GenericType<List<MultiUserQueue>>() {
        };
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/lobby")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(genericType);
    }

    public MultiUserQueue addQueueUser(MultiUserQueue user) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/lobby")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), MultiUserQueue.class);
    }

    public MultiUserQueue deleteQueueUser(MultiUserQueue user) {
        long id = user.id;
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("/api/lobby/" + String.valueOf(id))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(MultiUserQueue.class);
    }
}