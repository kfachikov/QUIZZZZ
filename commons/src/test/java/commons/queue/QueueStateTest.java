package commons.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QueueStateTest {
    private QueueUser user1;
    private QueueUser user2;
    private List<QueueUser> users;

    private boolean gameStarting;
    private long msToStart;
    private long upcomingGameId;

    @BeforeEach
    void setUp() {
        gameStarting = true;
        msToStart = 10L;
        upcomingGameId = 10L;
        users = new ArrayList<>();
        user1 = new QueueUser("username1");
        user2 = new QueueUser("username2");
        users.add(user1);
        users.add(user2);
    }

    @Test
    void getUsers() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(users, queueState.getUsers());
    }

    @Test
    void setUsers() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        List<QueueUser> newUsers = new ArrayList<>();
        newUsers.add(user1);
        queueState.setUsers(newUsers);
        assertEquals(newUsers, queueState.getUsers());

    }

    @Test
    void isGameStarting() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(gameStarting, queueState.isGameStarting());
    }

    @Test
    void setGameStarting() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        boolean newGameStarting = false;
        queueState.setGameStarting(newGameStarting);
        assertEquals(false, queueState.isGameStarting());
    }

    @Test
    void getMsToStart() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(msToStart, queueState.getStartTimeInMs());
    }

    @Test
    void setMsToStart() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        long newMsToStart = 1L;
        queueState.setStartTimeInMs(newMsToStart);
        assertEquals(1L, queueState.getStartTimeInMs());
    }

    @Test
    void getUpcomingGameId() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(upcomingGameId, queueState.getUpcomingGameId());
    }

    @Test
    void setUpcomingGameId() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        long newUpcomingGameId = 1L;
        queueState.setUpcomingGameId(newUpcomingGameId);
        assertEquals(1L, queueState.getUpcomingGameId());
    }

    @Test
    void testEquals() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        QueueState queueState2 = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(queueState, queueState2);
    }

    @Test
    void testEqualsDifferentUsers() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        List<QueueUser> newUsers = new ArrayList<>();
        newUsers.add(user1);
        QueueState queueState2 = new QueueState(newUsers, gameStarting, msToStart, upcomingGameId);
        assertNotEquals(queueState, queueState2);
    }

    @Test
    void testEqualsDifferentGameStarting() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        boolean newGameStarting = false;
        QueueState queueState2 = new QueueState(users, newGameStarting, msToStart, upcomingGameId);
        assertNotEquals(queueState, queueState2);
    }

    @Test
    void testEqualsDifferentMsToStart() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        QueueState queueState2 = new QueueState(users, gameStarting, 1L, upcomingGameId);
        assertNotEquals(queueState, queueState2);
    }

    @Test
    void testEqualsDifferentUpcomingGameId() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        QueueState queueState2 = new QueueState(users, gameStarting, msToStart, 1L);
        assertNotEquals(queueState, queueState2);
    }

    @Test
    void testHashCode() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        QueueState queueState2 = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        assertEquals(queueState.hashCode(), queueState2.hashCode());
    }

    @Test
    void testHashCodeDifferent() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);
        QueueState queueState2 = new QueueState(users, gameStarting, msToStart, 1L);
        assertNotEquals(queueState.hashCode(), queueState2.hashCode());
    }

    @Test
    void testToString() {
        QueueState queueState = new QueueState(users, gameStarting, msToStart, upcomingGameId);

    }
}