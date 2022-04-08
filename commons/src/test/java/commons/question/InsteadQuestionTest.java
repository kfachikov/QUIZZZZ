package commons.question;

import commons.misc.Activity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InsteadQuestionTest {
    final private Activity activity = new Activity("id", "title", "source", "image", 100L);
    final private Activity activity2 = new Activity("iddd", "title", "source", "image", 100L);

    @Test
    public void testConstructor() {
        InsteadQuestion question = new InsteadQuestion(activity);
        assertEquals(activity, question.getActivity());
    }

    @Test
    public void testEquals() {
        InsteadQuestion question1 = new InsteadQuestion(activity);
        InsteadQuestion question2 = new InsteadQuestion(activity);

        assertEquals(question1, question2);
    }

    @Test
    public void testNotEquals1() {
        InsteadQuestion question1 = new InsteadQuestion(activity);
        InsteadQuestion question2 = new InsteadQuestion(activity2);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testHashCode() {
        InsteadQuestion question1 = new InsteadQuestion(activity);
        InsteadQuestion question2 = new InsteadQuestion(activity);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode1() {
        InsteadQuestion question1 = new InsteadQuestion(activity);
        InsteadQuestion question2 = new InsteadQuestion(activity2);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testToString() {
        InsteadQuestion question = new InsteadQuestion(activity);
        assertEquals("INSTEAD OF ..., YOU COULD BE ...", question.toString());
    }


}