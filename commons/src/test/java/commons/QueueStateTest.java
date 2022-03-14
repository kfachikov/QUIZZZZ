package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QueueStateTest {

    private QueueState emptyState1;
    private QueueState emptyState2;
    private QueueState emptyState3;

    private QueueState stateA1;
    private QueueState stateA2;

    private QueueState stateB1;
    private QueueState stateB2;

    private QueueState stateC1;
    private QueueState stateC2;
    private QueueState stateC3;

    @BeforeEach
    void setUp() {
        // Initialize empty QueueStates
        this.emptyState1 = new QueueState(new ArrayList<>());
        this.emptyState2 = new QueueState();
        this.emptyState3 = new QueueState(new ArrayList<>(), false, 3000);

        // Initialize 1st set of example QueueStates
        QueueUser queueUser1 = new QueueUser("TestUser1");
        QueueUser queueUser2 = new QueueUser("TestUser2");
        QueueUser queueUser3 = new QueueUser("TestUser3");

        // QueueStates are initialized using the same QueueUser instances
        this.stateA1 = new QueueState(Arrays.asList(queueUser1, queueUser2, queueUser3));
        this.stateA2 = new QueueState(Arrays.asList(queueUser1, queueUser2, queueUser3));

        // Initialize 2nd set of example QueueStates
        // They are initialized using different QueueUser instances
        this.stateB1 = new QueueState(Arrays.asList(
                new QueueUser("TestUser4"),
                new QueueUser("TestUser5"),
                new QueueUser("TestUser6")
        ));
        this.stateB2 = new QueueState(Arrays.asList(
                new QueueUser("TestUser4"),
                new QueueUser("TestUser5"),
                new QueueUser("TestUser6")
        ));
        // QueueState with different order
        this.stateC1 = new QueueState(Arrays.asList(
                new QueueUser("TestUser4"),
                new QueueUser("TestUser6"),
                new QueueUser("TestUser5")
        ));
        // QueueState that is starting
        this.stateC2 = new QueueState(Arrays.asList(
                new QueueUser("TestUser4"),
                new QueueUser("TestUser5"),
                new QueueUser("TestUser6")
        ), true, 3000);
        // QueueState whose countdown is ticking down
        this.stateC3 = new QueueState(Arrays.asList(
                new QueueUser("TestUser4"),
                new QueueUser("TestUser5"),
                new QueueUser("TestUser6")
        ), true, 1500);
    }

    @Test
    public void checkConstructorEmpty() {
        assertNotNull(emptyState2.users);
    }

    @Test
    public void checkConstructorNonEmpty() {
        assertNotNull(stateA1.users);
        assertEquals(3, stateA1.users.size());
    }

    @Test
    public void equalsHashCode1() {
        assertEquals(stateA1, stateA2);
        assertEquals(stateA1.hashCode(), stateA2.hashCode());
    }

    @Test
    public void equalsHashCode2() {
        assertEquals(stateB1, stateB2);
        assertEquals(stateB1.hashCode(), stateB2.hashCode());
    }

    @Test
    public void equalsHashCodeEmpty() {
        assertEquals(emptyState1, emptyState2);
        assertEquals(emptyState1, emptyState3);
        assertEquals(emptyState3, emptyState2);
        assertEquals(emptyState1.hashCode(), emptyState2.hashCode());
        assertEquals(emptyState1.hashCode(), emptyState3.hashCode());
        assertEquals(emptyState3.hashCode(), emptyState2.hashCode());
    }

    @Test
    public void notEqualsHashCodeDifferent() {
        assertNotEquals(stateA1, stateB1);
        assertNotEquals(stateA1.hashCode(), stateB1.hashCode());
    }

    @Test
    public void notEqualsHashCodeShuffled() {
        // Order matters
        assertNotEquals(stateC1, stateB1);
        assertNotEquals(stateC1.hashCode(), stateB1.hashCode());
    }

    @Test
    public void notEqualsHashCodeStarted() {
        // gameStarted matters
        assertNotEquals(stateC2, stateB1);
        assertNotEquals(stateC2.hashCode(), stateB1.hashCode());
    }

    @Test
    public void notEqualsHashCodeCountdown() {
        // msToStart matters
        assertNotEquals(stateC3, stateB1);
        assertNotEquals(stateC3.hashCode(), stateB1.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = stateA1.toString();
        assertTrue(actual.contains(QueueState.class.getSimpleName()));
        assertTrue(actual.contains(QueueUser.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("TestUser1"));
        assertTrue(actual.contains("TestUser2"));
        assertTrue(actual.contains("TestUser3"));
        assertTrue(actual.contains("username"));
        assertTrue(actual.contains("users"));
    }
}