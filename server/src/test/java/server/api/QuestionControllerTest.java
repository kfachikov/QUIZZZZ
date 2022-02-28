package server.api;

import commons.AbstractQuestion;
import commons.BasicMultipleChoiceQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.utils.RandomUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class QuestionControllerTest {

    private RandomUtils randomUtils;
    private TestQuestionRepository repo;

    private QuestionController ctrl;

    private int nextId;

    @BeforeEach
    public void setup() {
        randomUtils = new RandomUtils();
        repo = new TestQuestionRepository();
        ctrl = new QuestionController(randomUtils, repo);
        nextId = 0;
    }

    private void addMockQuestions() {
        List<AbstractQuestion> mockQuestions = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            mockQuestions.add(
                    new BasicMultipleChoiceQuestion("q" + nextId, "f" + nextId, nextId)
            );
            mockQuestions.get((int) i).id = nextId++;
        }
        repo.questions.addAll(mockQuestions);
    }

    @Test
    public void testGetById0() {
        addMockQuestions();
        var result = ctrl.getById(0);
        assertEquals(List.of("existsById", "getById"), repo.calledMethods);

        AbstractQuestion aq = result.getBody();
        assertEquals(new BasicMultipleChoiceQuestion("q0", "f0", 0), aq);
    }

    @Test
    public void testGetById15() {
        addMockQuestions();
        var result = ctrl.getById(15);
        assertEquals(List.of("existsById", "getById"), repo.calledMethods);

        AbstractQuestion aq = result.getBody();
        AbstractQuestion expected = new BasicMultipleChoiceQuestion("q15", "f15", 15);
        expected.id = 15;
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

            assertEquals(myShuffledList.get((int) i), aq.id);
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
