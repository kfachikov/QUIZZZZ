package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoreExpensiveQuestionTest {
    @Test
    public void testToString() {
        AbstractQuestion question = new MoreExpensiveQuestion();

        assertEquals("What activity has a higher energy consumption?", question.toString());
    }


}