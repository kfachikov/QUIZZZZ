package server.api;

import commons.MultiPlayerGameRound;
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
    public void databaseIsUsed1() {
        responseCtrl.sendSoloAnswer(getSoloAnswer(1L, "answer1"));
        assertEquals(List.of("save"), mockSoloAnswerRepository.calledMethods);
    }

    private static SoloGameRound getSoloAnswer(long id, String answer) {
        return new SoloGameRound(id, answer);
    }

    private List<MultiPlayerGameRound> addMocKMultiPlayerAnswers() {
        List<MultiPlayerGameRound> mockMultiPlayerAnswers = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            MultiPlayerGameRound multiPlayerGameRound = new MultiPlayerGameRound(i, "answer" + i);
            mockMultiPlayerAnswers.add(multiPlayerGameRound);
        }
        mockMultiAnswerRepository.answers.addAll(mockMultiPlayerAnswers);
        return mockMultiPlayerAnswers;
    }

    @Test
    public void testGetAllMultiAnswers() {
        var expected = addMocKMultiPlayerAnswers();
        var result = responseCtrl.getMultiAnswers();
        assertEquals(expected, result);
    }

    @Test
    public void testMultiMethodCall() {
        addMocKMultiPlayerAnswers();
        responseCtrl.getMultiAnswers();
        assertEquals(List.of("findAll"), mockMultiAnswerRepository.calledMethods);
    }

    @Test
    public void databaseIsUsed2() {
        responseCtrl.sendMultiAnswer(getMultiAnswer(1L, "answer1"));
        assertEquals(List.of("save"), mockMultiAnswerRepository.calledMethods);
    }

    private static MultiPlayerGameRound getMultiAnswer(long id, String answer) {
        return new MultiPlayerGameRound(id, answer);
    }




}
