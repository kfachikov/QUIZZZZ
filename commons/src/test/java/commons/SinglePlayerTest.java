package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {

    @Test
    public void checkConstructor() {
        var p = new SinglePlayer("user");
        assertEquals("user", p.username);
    }

    @Test
    public void equalsHashCode() {
        var a = new SinglePlayer("user");
        var b = new SinglePlayer("user");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new SinglePlayer("user1");
        var b = new SinglePlayer("user2");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new SinglePlayer("user").toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("username"));
    }

}