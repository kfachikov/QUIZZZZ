package server.api;

import commons.AbstractQuestion;
import commons.Activity;
import commons.GuessQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.utils.GenerateQuestionUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class QuestionControllerTest {

    private GenerateQuestionUtils generateQuestionUtils;
    private MockQuestionRepository repo;
    private QuestionController ctrl;
    private int nextId;

    @BeforeEach
    public void setup() {
        generateQuestionUtils = new GenerateQuestionUtils();
        repo = new MockQuestionRepository();
        ctrl = new QuestionController(generateQuestionUtils, repo);
        nextId = 0;
    }

    private void addMockQuestions() {
        List<AbstractQuestion> mockQuestions = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            mockQuestions.add(new GuessQuestion(new Activity("id" + nextId, "title" + nextId, "source" + nextId, "image" + nextId, 100L + nextId)));
            mockQuestions.get((int) i).setId(nextId++);
        }
        repo.questions.addAll(mockQuestions);
    }

    @Test
    public void testMethodCall() {
        addMockQuestions();
        ctrl.getById(0);
        assertEquals(List.of("existsById", "getById"), repo.calledMethods);
    }

    @Test
    public void testGetById0() {
        addMockQuestions();
        var result = ctrl.getById(0);
        assertEquals(List.of("existsById", "getById"), repo.calledMethods);

        AbstractQuestion aq = result.getBody();
        assertEquals(new GuessQuestion(new Activity("id0", "title0", "source0", "image0", 100L)), aq);
    }

    @Test
    public void testGetById15() {
        addMockQuestions();
        var result = ctrl.getById(15);
        assertEquals(List.of("existsById", "getById"), repo.calledMethods);

        AbstractQuestion aq = result.getBody();
        AbstractQuestion expected = new GuessQuestion(new Activity("id15", "title15", "source15", "image15", 115L));
        expected.setId(15);
        assertEquals(expected, aq);
    }

    @Test
    public void testGetByNegativeId() {
        addMockQuestions();
        var result = ctrl.getById(-1);
        assertEquals(List.of(), repo.calledMethods);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetByNonexistentId() {
        addMockQuestions();
        var result = ctrl.getById(20);
        assertEquals(List.of("existsById"), repo.calledMethods);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetByGameRound() {
        addMockQuestions();
        List<Long> myShuffledList = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            myShuffledList.add(i);
        }
        Collections.shuffle(myShuffledList, new Random(0));

        for (long i = 0; i < 20; i++) {
            var result = ctrl.getByGameRound(0, i);
            AbstractQuestion aq = result.getBody();

            assertEquals(myShuffledList.get((int) i), aq.getId());
            assertEquals(3 * i + 3, repo.calledMethods.size());
        }
    }

    @Test
    public void testGetByNegativeGameRound() {
        addMockQuestions();

        var result = ctrl.getByGameRound(-1, 0);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetByGameRoundEmpty() {
        var result = ctrl.getByGameRound(0, 0);

        assertEquals(NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testGetByGameRoundNegativeQuestion() {
        addMockQuestions();

        var result = ctrl.getByGameRound(0, -1);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetByGameRoundTwentythQuestion() {
        addMockQuestions();

        var result = ctrl.getByGameRound(0, 20);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }
}
