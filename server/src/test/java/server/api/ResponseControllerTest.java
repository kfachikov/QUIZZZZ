package server.api;

import commons.SoloGameRound;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class ResponseControllerTest {

    private MockSoloAnswerRepository mockSoloAnswerRepository;
    private MockMultiAnswerRepository mockMultiAnswerRepository;
    private ResponseController responseCtrl;
    private int nextId = 0;

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
    /*
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
        SoloGameRound soloGameRound = new SoloGameRound(1L, "answer1");
        responseCtrl.sendSoloAnswer(soloGameRound);
        assertEquals(List.of("save"), mockSoloAnswerRepository.calledMethods);
    }
    */
}
