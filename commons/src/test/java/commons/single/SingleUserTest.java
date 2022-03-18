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
package commons.single;

import commons.single.SingleUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingleUserTest {

    @Test
    public void checkConstructor() {
        var p = new SingleUser("user", 10);
        assertEquals("user", p.username);
        assertEquals(10, p.score);
    }

    @Test
    public void equalsHashCode() {
        var a = new SingleUser("user", 10);
        var b = new SingleUser("user", 10);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new SingleUser("user", 10);
        var b = new SingleUser("user", 15);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new SingleUser("user", 10).toString();
        assertTrue(actual.contains(SingleUser.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("username"));
    }
}