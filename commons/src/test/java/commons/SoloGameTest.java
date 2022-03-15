package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoloGameTest {

    @Test
    void getQuestions() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new SoloGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(questions, g.getQuestions());
    }

    @Test
    void setQuestions() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new SoloGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(questions, g.questions);
    }

    @Test
    void getId() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new SoloGame(1L, new ArrayList<>());
        g.setQuestions(questions);
        assertEquals(1L, g.getId());
    }

    @Test
    void setId() {
        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new BasicMultipleChoiceQuestion("title", "filename", 250));
        var g = new MultiPlayerGame(1L, new ArrayList<>());
        g.setId(3L);
        assertEquals(3L, g.getId());
    }
}