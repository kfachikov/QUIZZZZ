package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockActivityConsumption extends ActivityConsumptionQuestionType {

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public MockActivityConsumption(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    @Override
    public void setAnswerChoices() {
        answerChoices.add(consumptionWh + "Wh");
        answerChoices.add(consumptionWh - 10 + "Wh");
        answerChoices.add(consumptionWh + 10 + "Wh");
    }
}

class ActivityConsumptionQuestionTypeTest {

    @Test
    public void testConstructor() {
        AbstractQuestion question = new ActivityConsumptionQuestionType("taking a shower", "image", 250);
        assertEquals("taking a shower", question.baseTitle);
        assertEquals("image", question.imageFilename);
        assertEquals(250, question.consumptionWh);
    }

    @Test
    public void testEquals() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);

        assertEquals(question1, question2);
    }

    @Test
    public void testNotEquals1() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("watching TV", "image1", 250);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testNotEquals2() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image2", 250);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testNotEquals3() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image1", 200);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testSetAnswerChoices() {
        ActivityConsumptionQuestionType question = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        question.setAnswerChoices();

        assertEquals(3, question.answerChoices.size());
        assertTrue(question.answerChoices.contains("250Wh"));
    }

    @Test
    public void testSetAnswerChoicesEquals() {
        MockActivityConsumption question1 = new MockActivityConsumption("taking a shower", "image1", 250);
        MockActivityConsumption question2 = new MockActivityConsumption("taking a shower", "image1", 250);

        question1.setAnswerChoices();
        question2.setAnswerChoices();

        assertEquals(question1, question2);
    }

    @Test
    public void testHashCode() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode1() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("watching TV", "image1", 250);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode2() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image2", 250);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode3() {
        AbstractQuestion question1 = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new ActivityConsumptionQuestionType("taking a shower", "image1", 200);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testToString() {
        AbstractQuestion question = new ActivityConsumptionQuestionType("taking a shower", "image1", 250);

        assertEquals("How much does taking a shower consume?", question.toString());
    }

}