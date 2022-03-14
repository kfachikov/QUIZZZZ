package server.api;


import org.junit.jupiter.api.BeforeEach;


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

}
