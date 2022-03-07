package server.api;


import commons.MultiUserQueue;
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
    public List<MultiUserQueue> getAllUsers() {
        return repo.findAll();
    }

    /**
     * @param user the user to be added to the MultiUserQueue repository
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<MultiUserQueue> add(@RequestBody MultiUserQueue user) {
        if (user == null || isNullOrEmpty(user.username)) {
            return ResponseEntity.badRequest().build();
        }
        MultiUserQueue saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


    /** Delete a user if present from the repository.
     * @param id Primary-key attribute to search with
     * @return returns a ResponseEntity consisting of the deleted user if present or a Not Found error if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MultiUserQueue> deleteUser(@PathVariable("id") long id) {
        MultiUserQueue removed = repo.findById(id).orElse(null);
        if (removed != null) {
            repo.delete(removed);
            return ResponseEntity.ok(removed);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
