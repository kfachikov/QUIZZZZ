package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreExpensiveQuestionTest {

    @Test
    public void testConstructor() {
        AbstractQuestion question = new MoreExpensiveQuestion();
        assertNotNull(question);
    }

    @Test
    public void testNotEquals() {
        MoreExpensiveQuestion question1 = new MoreExpensiveQuestion();
        List<Activity> answerChoices1 = new ArrayList<>();
        Activity activity1 = new Activity("id", "title", "source", "image", 100L);
        Activity activity2 = new Activity("newId", "newTitle", "newSource", "newImage", 110L);
        Activity activity3 = new Activity("newwId", "newTitle", "newSource", "newImage", 110L);
        answerChoices1.add(activity1);
        answerChoices1.add(activity2);
        answerChoices1.add(activity3);
        question1.setAnswerChoices(answerChoices1);

        MoreExpensiveQuestion question2 = new MoreExpensiveQuestion();
        List<Activity> answerChoices2 = new ArrayList<>();
        Activity activity4 = new Activity("idd", "title", "source", "image", 100L);
        Activity activity5 = new Activity("nedwId", "newTitle", "newSource", "newImage", 110L);
        Activity activity6 = new Activity("nedwwId", "newTitle", "newSource", "newImage", 110L);
        answerChoices2.add(activity4);
        answerChoices2.add(activity5);
        answerChoices2.add(activity6);
        question2.setAnswerChoices(answerChoices2);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testEquals() {
        MoreExpensiveQuestion question1 = new MoreExpensiveQuestion();
        List<Activity> answerChoices1 = new ArrayList<>();
        Activity activity1 = new Activity("id", "title", "source", "image", 100L);
        Activity activity2 = new Activity("newId", "newTitle", "newSource", "newImage", 110L);
        Activity activity3 = new Activity("newwId", "newTitle", "newSource", "newImage", 110L);
        answerChoices1.add(activity1);
        answerChoices1.add(activity2);
        answerChoices1.add(activity3);
        question1.setAnswerChoices(answerChoices1);

        MoreExpensiveQuestion question2 = new MoreExpensiveQuestion();
        List<Activity> answerChoices2 = new ArrayList<>();
        Activity activity4 = new Activity("id", "title", "source", "image", 100L);
        Activity activity5 = new Activity("newId", "newTitle", "newSource", "newImage", 110L);
        Activity activity6 = new Activity("newwId", "newTitle", "newSource", "newImage", 110L);
        answerChoices2.add(activity4);
        answerChoices2.add(activity5);
        answerChoices2.add(activity6);
        question2.setAnswerChoices(answerChoices2);

        assertEquals(question1, question2);
    }

    @Test
    public void testToString() {
        AbstractQuestion question = new MoreExpensiveQuestion();

        assertEquals("What activity has a higher energy consumption?", question.toString());
    }


}