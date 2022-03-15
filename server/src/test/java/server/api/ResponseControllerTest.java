package server.api;

import commons.SoloGameRound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseControllerTest {

    private MockSoloAnswerRepository mockSoloAnswerRepository;
    private MockMultiAnswerRepository mockMultiAnswerRepository;
    private ResponseController responseCtrl;

    @BeforeEach
    public void setup() {
        mockMultiAnswerRepository = new MockMultiAnswerRepository();
        mockSoloAnswerRepository = new MockSoloAnswerRepository();
        responseCtrl = new ResponseController(mockSoloAnswerRepository, mockMultiAnswerRepository);
    }

    private List<SoloGameRound> addMockSoloAnswers() {
        List<SoloGameRound> mockSoloAnswers = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            SoloGameRound soloGameRound = new SoloGameRound(i, "answer" + i);
            mockSoloAnswers.add(soloGameRound);
        }
        mockSoloAnswerRepository.answers.addAll(mockSoloAnswers);
        return mockSoloAnswers;
    }

    @Test
    public void testGetAllSoloAnswers() {
        var expected = addMockSoloAnswers();
        var result = responseCtrl.getSoloAnswers();
        assertEquals(expected, result);
    }

    @Test
    public void testSoloMethodCall() {
        addMockSoloAnswers();
        responseCtrl.getSoloAnswers();
        assertEquals(List.of("findAll"), mockSoloAnswerRepository.calledMethods);
    }

    @Test
    public void databaseIsUsed() {
        responseCtrl.sendSoloAnswer(getSoloAnswer(1L, "answer1"));
        assertEquals(List.of("save"), mockSoloAnswerRepository.calledMethods);
    }

    private static SoloGameRound getSoloAnswer(long id, String answer) {
        return new SoloGameRound(id, answer);
    }



}
