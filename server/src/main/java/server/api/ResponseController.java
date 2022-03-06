package server.api;

import org.springframework.web.bind.annotation.*;
import server.database.SinglePlayerRepository;


@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final SinglePlayerRepository repo;


    public ResponseController(SinglePlayerRepository repo) {
        this.repo = repo;
    }


    /*
      Sends the answer of the SinglePlayer to AbstractQuestion question to the server.
      @param question to be sent
     * @return response
     */
    //this remains to be discussed.
   /* @PostMapping("")
    public ResponseEntity<Object> send(@RequestBody AbstractQuestion question) {
      /*  var answer = question.consumptionWh;

        return ResponseEntity.ok(answer);
    }
    */
}
