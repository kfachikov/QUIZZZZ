package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FourthQuestionTypeTest {
    @Test
    public void testConstructor() {
        AbstractQuestion question = new FourthQuestionType("taking a shower", "image", 250);
        assertEquals("taking a shower", question.baseTitle);
        assertEquals("image", question.imageFilename);
        assertEquals(250, question.consumptionWh);
    }

    @Test
    public void testEquals() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image1", 250);

        assertEquals(question1, question2);
    }

    @Test
    public void testNotEquals1() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("watching TV", "image1", 250);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testNotEquals2() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image2", 250);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testNotEquals3() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image1", 200);

        assertNotEquals(question1, question2);
    }

    @Test
    public void testHashCode() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image1", 250);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode1() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("watching TV", "image1", 250);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode2() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image2", 250);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testHashCode3() {
        AbstractQuestion question1 = new FourthQuestionType("taking a shower", "image1", 250);
        AbstractQuestion question2 = new FourthQuestionType("taking a shower", "image1", 200);

        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void testToString() {
        AbstractQuestion question = new FourthQuestionType("taking a shower", "image1", 250);

        assertEquals("What activity has a higher energy consumption?", question.toString());
    }

}