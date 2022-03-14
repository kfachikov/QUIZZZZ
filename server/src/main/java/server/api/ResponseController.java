package server.api;

import commons.MultiPlayerGameRound;
import commons.SoloGameRound;
import org.springframework.web.bind.annotation.*;
import server.database.MultiPlayerGameAnswerRepository;
import server.database.SoloGameAnswerRepository;

import java.util.List;


@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private SoloGameAnswerRepository soloGameAnswerRepository;
    private MultiPlayerGameAnswerRepository multiPlayerGameAnswerRepository;

    public ResponseController(SoloGameAnswerRepository soloGameAnswerRepository, MultiPlayerGameAnswerRepository multiPlayerGameAnswerRepository) {
        this.soloGameAnswerRepository = soloGameAnswerRepository;
        this.multiPlayerGameAnswerRepository = multiPlayerGameAnswerRepository;
    }

    /**
     * This endpoint sends the answer of a solo game question to the solo game answer repository.
     * @return list of answers
     */
    @GetMapping("/soloGame/answers")
    public List<SoloGameRound> getSoloAnswers() {
        return soloGameAnswerRepository.findAll();
    }

    /**
      Sends the answer of the SinglePlayer to AbstractQuestion question to the solo.
      @param soloGameRound to be sent
     */
    @PostMapping("")
    public void sendSoloAnswer(@RequestBody SoloGameRound soloGameRound) {
        soloGameAnswerRepository.save(soloGameRound);
    }

    /**
     * This endpoint sends the answer of a multiplayer game question to the multiplayer game answer repository.
     * @return list of answers
     */
    @GetMapping("/soloGame/answers")
    public List<MultiPlayerGameRound> getMultiAnswers() {
        return multiPlayerGameAnswerRepository.findAll();
    }

    /**
     Sends the answer of the SinglePlayer to AbstractQuestion question to the solo.
     @param multiPlayerGameRound to be sent
     */
    @PostMapping("")
    public void sendSoloAnswer(@RequestBody MultiPlayerGameRound multiPlayerGameRound) {
        multiPlayerGameAnswerRepository.save(multiPlayerGameRound);
    }

}
