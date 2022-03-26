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
package server.database;

import commons.queue.QueueUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Queue user repository for the multiplayer queue users.
 */
public interface QueueUserRepository extends JpaRepository<QueueUser, Long> {
    /**
     * Generates a method iterating over all entries in the repository and checking whether a username exists.
     * JpaRepository uses injection to autogenerate the expected behaviour of this method
     * as far as its name follows a particular convention.
     *
     * @param username A String to check for existence in the repository.
     * @return Boolean value due to whether the username is present or not.
     */
    boolean existsQueueUserByUsername(String username);
}