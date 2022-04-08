package commons.question;

import commons.question.MoreExpensiveQuestion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoreExpensiveQuestionTest {

    @Test
    public void testToString() {
        MoreExpensiveQuestion question = new MoreExpensiveQuestion();
        assertEquals("WHAT ACTIVITY HAS A HIGHER ENERGY CONSUMPTION?", question.toString());
    }
}