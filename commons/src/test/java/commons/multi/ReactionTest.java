package commons.multi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReactionTest {

    private Reaction reaction1;
    private Reaction reaction2;
    private Reaction reaction3;

    @BeforeEach
    void setUp() {
        reaction1 = new Reaction("Kate", "laughing");
        reaction2 = new Reaction("Kate", "laughing");
        reaction3 = new Reaction("John", "laughing");
    }

    @Test
    public void testConstructor() {
        assertNotNull(reaction1);
    }

    @Test
    public void testGetUsername() {
        assertEquals("Kate", reaction1.getUsername());
    }

    @Test
    public void testGetEmoji() {
        assertEquals("laughing", reaction1.getEmoji());
    }

    @Test
    public void testEquals() {
        assertEquals(reaction1, reaction2);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(reaction1, reaction3);
    }

    @Test
    public void testHashCode1() {
        assertEquals(reaction1.hashCode(), reaction2.hashCode());
    }

    @Test
    public void testHashCode2() {
        assertNotEquals(reaction1.hashCode(), reaction3.hashCode());
    }


}