package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerGameTest {

    @Test
    void testSetQuestions() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new MultiPlayerGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(questions, g.questions);
    }

    @Test
    void testGetQuestions() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new MultiPlayerGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(questions, g.getQuestions());
    }

    @Test
    void testGetId() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new MultiPlayerGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(1L, g.getId());
    }

    @Test
    void testSetId() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new MultiPlayerGame(1L, new ArrayList<>());
        g.setId(3L);
        assertEquals(3L, g.getId());
    }
}