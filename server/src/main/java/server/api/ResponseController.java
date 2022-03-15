package server.api;

import commons.MultiPlayerGameRound;
import commons.SoloGameRound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.MultiPlayerGameAnswerRepository;
import server.database.SoloGameAnswerRepository;

import java.util.List;


@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final SoloGameAnswerRepository soloGameAnswerRepository;
    private final MultiPlayerGameAnswerRepository multiPlayerGameAnswerRepository;

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
     * @param soloGameRound to be sent
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<SoloGameRound> sendSoloAnswer(@RequestBody SoloGameRound soloGameRound) {
        if (soloGameRound == null || isNullOrEmpty(soloGameRound.finalAnswer)) {
            return ResponseEntity.badRequest().build();
        }
        SoloGameRound saved = soloGameAnswerRepository.save(soloGameRound);
        return ResponseEntity.ok(saved);
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
     * @param multiPlayerGameRound to be sent
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<MultiPlayerGameRound> sendMultiAnswer(@RequestBody MultiPlayerGameRound multiPlayerGameRound) {
        if (multiPlayerGameRound == null || isNullOrEmpty(multiPlayerGameRound.finalAnswer)) {
            return ResponseEntity.badRequest().build();
        }
        MultiPlayerGameRound saved = multiPlayerGameAnswerRepository.save(multiPlayerGameRound);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
