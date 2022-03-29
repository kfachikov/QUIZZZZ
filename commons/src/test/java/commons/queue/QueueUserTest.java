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
package commons.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import commons.queue.QueueUser;
import org.junit.jupiter.api.Test;

public class QueueUserTest {
    final private QueueUser queueUser = new QueueUser("username");

    @Test
    public void getUsername() {
        assertEquals("username", queueUser.getUsername());
    }

    @Test
    public void setUsername() {
        String result = "newUsername";
        queueUser.setUsername(result);
        assertEquals(result, queueUser.getUsername());
    }

    @Test
    public void equalsHashCode() {
        var a = new QueueUser("user");
        var b = new QueueUser("user");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new QueueUser("user1");
        var b = new QueueUser("user2");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new QueueUser("user").toString();
        assertTrue(actual.contains(QueueUser.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("username"));
    }
}