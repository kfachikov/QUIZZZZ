package commons.question;

import commons.misc.Activity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockConsumptionQuestion extends ConsumptionQuestion {

    private Activity activity1 = new Activity("id", "title", "source", "image", 100L);
    private List<String> answerChoicesMock;

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param activity the activity the question is about
     */
    public MockConsumptionQuestion(Activity activity) {
        super(activity);
        answerChoicesMock = new ArrayList<String>();
    }

    @Override
    public void setAnswerChoices() {
        long consumption = activity1.getConsumption();
        answerChoicesMock.add(consumption + "Wh");
        answerChoicesMock.add(consumption - 10 + "Wh");
        answerChoicesMock.add(consumption + 10 + "Wh");
    }

    public List<String> getAnswerChoicesMock() {
        return answerChoicesMock;
    }
}

class ConsumptionQuestionTest {
    private Activity activity = new Activity("id", "title", "source", "image", 100L);

    @Test
    public void testConstructor() {
        ConsumptionQuestion question = new ConsumptionQuestion(activity);
        assertEquals(new Activity("id", "title", "source", "image", 100L), question.getActivity());
    }

    @Test
    public void testEquals() {
        ConsumptionQuestion question1 = new ConsumptionQuestion(activity);
        ConsumptionQuestion question2 = new ConsumptionQuestion(activity);

        assertEquals(question1, question2);
    }

    @Test
    public void testNotEquals1() {
        ConsumptionQuestion question1 = new ConsumptionQuestion(activity);
        Activity activity2 = new Activity("iddd", "title", "source", "image", 100L);
        ConsumptionQuestion question2 = new ConsumptionQuestion(activity2);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testSetAnswerChoices() {
        ConsumptionQuestion question = new ConsumptionQuestion(activity);
        question.setAnswerChoices();

        assertEquals(3, question.getAnswerChoices().size());
        assertTrue(question.getAnswerChoices().contains(100L));
    }

    @Test
    public void testSetAnswerChoicesEquals() {
        MockConsumptionQuestion question1 = new MockConsumptionQuestion(activity);
        MockConsumptionQuestion question2 = new MockConsumptionQuestion(activity);

        question1.setAnswerChoices();
        question2.setAnswerChoices();

        assertEquals(question1, question2);
    }

    @Test
    public void testHashCode() {
        ConsumptionQuestion question1 = new ConsumptionQuestion(activity);
        ConsumptionQuestion question2 = new ConsumptionQuestion(activity);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode1() {
        ConsumptionQuestion question1 = new ConsumptionQuestion(activity);
        ConsumptionQuestion question2 = new ConsumptionQuestion(new Activity("iddd", "title", "source", "image", 100L));

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testToString() {
        ConsumptionQuestion question = new ConsumptionQuestion(activity);

        assertEquals("HOW MUCH ENERGY DOES IT TAKE?", question.toString());
    }

}