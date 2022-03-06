package server.api;


import commons.MultiUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.MultiUserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    private final MultiUserRepository repo;

    public LobbyController(MultiUserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("")
    public List<MultiUser> getAllUsers() {
        return repo.findAll();
    }

    /**
     * @param user the user to be added to the MultiUser repository
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<MultiUser> add(@RequestBody MultiUser user) {
        if (user == null || isNullOrEmpty(user.username)) {
            return ResponseEntity.badRequest().build();
        }
        MultiUser saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
