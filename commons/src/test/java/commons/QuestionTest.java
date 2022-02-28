package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionTest {
    @Test
    public void testConstructor() {
        AbstractQuestion aq = new BasicMultipleChoiceQuestion("q", "f", 0);
        assertEquals("q", aq.baseTitle);
        assertEquals("f", aq.imageFilename);
        assertEquals(0, aq.consumptionWh);
    }

    @Test
    public void testEqualsHashCode() {
        AbstractQuestion aq1 = new BasicMultipleChoiceQuestion("q", "f", 0);
        AbstractQuestion aq2 = new BasicMultipleChoiceQuestion("q", "f", 0);

        assertEquals(aq1, aq2);
        assertEquals(aq1.hashCode(), aq2.hashCode());
    }

    @Test
    public void testNotEqualsHashCode1() {
        AbstractQuestion aq1 = new BasicMultipleChoiceQuestion("qq", "f", 0);
        AbstractQuestion aq2 = new BasicMultipleChoiceQuestion("q", "f", 0);

        assertNotEquals(aq1, aq2);
        assertNotEquals(aq1.hashCode(), aq2.hashCode());
    }

    @Test
    public void testNotEqualsHashCode2() {
        AbstractQuestion aq1 = new BasicMultipleChoiceQuestion("q", "ff", 0);
        AbstractQuestion aq2 = new BasicMultipleChoiceQuestion("q", "f", 0);

        assertNotEquals(aq1, aq2);
        assertNotEquals(aq1.hashCode(), aq2.hashCode());
    }

    @Test
    public void testNotEqualsHashCode3() {
        AbstractQuestion aq1 = new BasicMultipleChoiceQuestion("q", "f", 0);
        AbstractQuestion aq2 = new BasicMultipleChoiceQuestion("q", "f", 1);

        assertNotEquals(aq1, aq2);
        assertNotEquals(aq1.hashCode(), aq2.hashCode());
    }

    @Test
    public void testToString() {
        AbstractQuestion aq = new BasicMultipleChoiceQuestion("q", "f", 0);
        String result = aq.toString();

        assertTrue(result.contains(BasicMultipleChoiceQuestion.class.getSimpleName()));
        assertTrue(result.contains("baseTitle"));
    }
}
