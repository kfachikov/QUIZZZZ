package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GuessQuestionTest {
    final private Activity activity = new Activity("id", "title", "source", "image", 100L);
    final private Activity activity2 = new Activity("iddd", "title", "source", "image", 100L);

    @Test
    public void testConstructor() {
        GuessQuestion question = new GuessQuestion(activity);
        assertEquals(activity, question.getActivity());
    }

    @Test
    public void testEquals() {
        AbstractQuestion question1 = new GuessQuestion(activity);
        AbstractQuestion question2 = new GuessQuestion(activity);

        assertEquals(question1, question2);
    }

    @Test
    public void testNotEquals1() {
        AbstractQuestion question1 = new GuessQuestion(activity);
        AbstractQuestion question2 = new GuessQuestion(activity2);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testHashCode() {
        AbstractQuestion question1 = new GuessQuestion(activity);
        AbstractQuestion question2 = new GuessQuestion(activity);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode1() {
        AbstractQuestion question1 = new GuessQuestion(activity);
        AbstractQuestion question2 = new GuessQuestion(activity2);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testToString() {
        AbstractQuestion question = new GuessQuestion(activity);

        assertEquals("How much energy does it take for the activity below?", question.toString());
    }
}
