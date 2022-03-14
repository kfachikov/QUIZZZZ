package server.api;

import commons.AbstractQuestion;

import org.springframework.web.bind.annotation.*;
import server.database.SinglePlayerRepository;


@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final SinglePlayerRepository repo;


    public ResponseController(SinglePlayerRepository repo) {
        this.repo = repo;
    }


    /**
      Sends the answer of the SinglePlayer to AbstractQuestion question to the server.
      @param question to be sent
     */
    @PostMapping("")
    public void send(@RequestBody AbstractQuestion question) {
        //if(question.finalAnswer! = question.consumptionWh)
    }

}
