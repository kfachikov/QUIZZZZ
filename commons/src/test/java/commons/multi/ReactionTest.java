package commons.multi;

import commons.multi.Reaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReactionTest {
    @Test
    public void testConstructor() {
        List<String> emojis = Arrays.asList("sad", "happy", "confused");
        Reaction reaction = new Reaction(emojis);

        assertNotNull(reaction);
    }

    @Test
    public void testGetEmojis() {
        List<String> emojis = Arrays.asList("sad", "happy", "confused");
        Reaction reaction = new Reaction(emojis);
        List<String> expected = Arrays.asList("sad", "happy", "confused");

        assertEquals(expected, reaction.getEmojis());
    }

    @Test
    public void testSetEmojis() {
        List<String> emojis = Arrays.asList("sad", "happy", "confused");
        Reaction reaction = new Reaction(emojis);
        List<String> newEmojis = Arrays.asList("tired", "nervous");
        reaction.setEmojis(newEmojis);
        List<String> expected = Arrays.asList("tired", "nervous");

        assertEquals(expected, reaction.getEmojis());
    }

    @Test
    public void testEquals() {
        List<String> emojis1 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction1 = new Reaction(emojis1);
        List<String> emojis2 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction2 = new Reaction(emojis2);

        assertEquals(reaction1, reaction2);
    }

    @Test
    public void testNotEquals() {
        List<String> emojis1 = Arrays.asList("tired", "happy", "confused");
        Reaction reaction1 = new Reaction(emojis1);
        List<String> emojis2 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction2 = new Reaction(emojis2);

        assertNotEquals(reaction1, reaction2);
    }

    @Test
    public void testHashCode1() {
        List<String> emojis1 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction1 = new Reaction(emojis1);
        List<String> emojis2 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction2 = new Reaction(emojis2);

        assertEquals(reaction1.hashCode(), reaction2.hashCode());
    }

    @Test
    public void testHashCode2() {
        List<String> emojis1 = Arrays.asList("tired", "happy", "confused");
        Reaction reaction1 = new Reaction(emojis1);
        List<String> emojis2 = Arrays.asList("sad", "happy", "confused");
        Reaction reaction2 = new Reaction(emojis2);

        assertNotEquals(reaction1.hashCode(), reaction2.hashCode());
    }


}