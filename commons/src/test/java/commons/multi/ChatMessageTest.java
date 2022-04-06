package commons.multi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    private ChatMessage chatMessage1;
    private ChatMessage chatMessage2;
    private ChatMessage chatMessage3;

    @BeforeEach
    void setUp() {
        chatMessage1 = new ChatMessage("Kate", "laughing");
        chatMessage2 = new ChatMessage("Kate", "laughing");
        chatMessage3 = new ChatMessage("John", "laughing");
    }

    @Test
    public void testConstructor() {
        assertNotNull(chatMessage1);
    }

    @Test
    public void testGetUsername() {
        assertEquals("Kate", chatMessage1.getUsername());
    }

    @Test
    public void testGetEmoji() {
        assertEquals("laughing", chatMessage1.getMessage());
    }

    @Test
    public void testEquals() {
        assertEquals(chatMessage1, chatMessage2);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(chatMessage1, chatMessage3);
    }

    @Test
    public void testHashCode1() {
        assertEquals(chatMessage1.hashCode(), chatMessage2.hashCode());
    }

    @Test
    public void testHashCode2() {
        assertNotEquals(chatMessage1.hashCode(), chatMessage3.hashCode());
    }


}