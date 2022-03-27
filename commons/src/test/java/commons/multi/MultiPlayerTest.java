package commons.multi;

import commons.misc.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerTest {

    @Test
    public void testConstructor() {

        Player player = new MultiPlayer("Kate", 0, true, true, true);

        assertNotNull(player);

    }

    @Test
    public void testGetUsername() {

        Player player = new MultiPlayer("Kate", 0, true, true, true);

        assertEquals("Kate", player.getUsername());

    }

    @Test
    public void testGetScore() {

        Player player = new MultiPlayer("Kate", 0, true, true, true);

        assertEquals(0, player.getScore());

    }

    @Test
    public void testGetTimeJoker() {

        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);

        assertTrue(player.getTimeJoker());
    }

    @Test
    public void testGetIncorrectAnswerJoker() {

        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);

        assertTrue(player.getIncorrectAnswerJoker());
    }

    @Test
    public void testGetPointsDoubledJoker() {

        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);

        assertTrue(player.getPointsDoubledJoker());

    }

    @Test
    public void testSetUsername() {

        Player player = new MultiPlayer("Kate", 0, true, true, true);
        player.setUsername("Mike");

        assertEquals("Mike", player.getUsername());

    }

    @Test
    public void testSetScore() {

        Player player = new MultiPlayer("Kate", 0, true, true, true);
        player.setScore(10);

        assertEquals(10, player.getScore());

    }

    @Test
    public void testSetTimeJoker() {

        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);
        player.setTimeJoker(false);

        assertFalse(player.getTimeJoker());

    }

    @Test
    public void testSetIncorrectAnswerJoker() {

        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);
        player.setIncorrectAnswerJoker(false);

        assertFalse(player.getIncorrectAnswerJoker());

    }

    @Test
    public void testSetPointsDoubledJoker() {
        MultiPlayer player = new MultiPlayer("Kate", 0, true, true, true);
        player.setPointsDoubledJoker(false);

        assertFalse(player.getPointsDoubledJoker());

    }

    @Test
    public void testEquals() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Kate", 0, true, true, true);

        assertEquals(player1, player2);

    }

    @Test
    public void testNotEquals() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Mike", 10, false, true, true);

        assertNotEquals(player1, player2);
    }

    @Test
    public void testNotEqualsName() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Mike", 0, true, true, true);

        assertNotEquals(player1, player2);
    }

    @Test
    public void testNotEqualsScore() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Kate", 10, true, true, true);

        assertNotEquals(player1, player2);
    }

    @Test
    public void testNotEqualsJoker() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Kate", 0, false, true, true);

        assertNotEquals(player1, player2);
    }

    @Test
    public void testHashCode1() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Kate", 0, true, true, true);

        assertEquals(player1.hashCode(), player2.hashCode());

    }

    @Test
    public void testHashcode2() {
        Player player1 = new MultiPlayer("Kate", 0, true, true, true);
        Player player2 = new MultiPlayer("Mike", 10, false, true, true);

        assertNotEquals(player1.hashCode(), player2.hashCode());

    }
}

