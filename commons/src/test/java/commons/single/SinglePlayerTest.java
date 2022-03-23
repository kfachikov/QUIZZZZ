package commons.single;

import commons.single.SinglePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {


    @Test
    public void equalsHashCode1() {
        var a = new SinglePlayer("user", 10);
        var b = new SinglePlayer("user", 10);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode2() {
        var a = new SinglePlayer("user1", 10);
        var b = new SinglePlayer("user2", 10);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalsHashCode3() {
        var a = new SinglePlayer("user1", 10);
        var b = new SinglePlayer("user1", 10);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode4() {
        var a = new SinglePlayer("user", 9);
        var b = new SinglePlayer("user", 10);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new SinglePlayer("user", 10);
        var b = new SinglePlayer("user", 15);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString1() {
        var actual = new SinglePlayer("user", 30).toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("username"));
    }

    @Test
    public void hasToString2() {
        var actual = new SinglePlayer("katya", 30).toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("katya"));
    }

    @Test
    public void hasToString3() {
        var actual = new SinglePlayer("user", 30).toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("30"));
    }

    @Test
    public void hasToString4() {
        var actual = new SinglePlayer("user", 50).toString();
        assertTrue(actual.contains(SinglePlayer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("50"));
    }

}