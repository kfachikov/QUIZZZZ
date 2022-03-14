package server.api;

import commons.MultiPlayerGameRound;
import commons.SoloGameRound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.MultiPlayerGameAnswerRepository;
import server.database.SoloGameAnswerRepository;



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
     * @param gameId is the game id
     * @return response
     */
    @GetMapping("/soloGame/answers")
    public ResponseEntity<Object> getSoloAnswers(@PathVariable long gameId) {
        if (gameId < 0 || !soloGameAnswerRepository.existsById(gameId)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(soloGameAnswerRepository.getById(gameId));
        }
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
     * @param gameId is the game id
     * @return response
     */
    @GetMapping("/soloGame/answers")
    public ResponseEntity<Object> getMultiAnswers(@PathVariable long gameId) {
        if (gameId < 0 || !multiPlayerGameAnswerRepository.existsById(gameId)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(multiPlayerGameAnswerRepository.getById(gameId));
        }
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
