package server.api;

import commons.SingleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    private final UserRepository repo;


    public LobbyController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("")
    public List<SingleUser> getAllUsers() {
        return repo.findAll();
    }


}
