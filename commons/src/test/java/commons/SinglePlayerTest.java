package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {

    @Test
    public void checkConstructor() {
        var p = new SinglePlayer("user", 45);
        assertEquals("user", p.getUsername());
    }

    @Test
    public void equalsHashCode() {
        var a = new SinglePlayer("user", 10);
        var b = new SinglePlayer("user", 10);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new SinglePlayer("user", 10);
        var b = new SinglePlayer("user", 15);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new SinglePlayer("user", 30).toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("username"));
    }

}