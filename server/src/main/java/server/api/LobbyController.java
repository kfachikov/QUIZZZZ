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


    /** Delete a user if present from the repositroy.
     * @param id Primary-key attribute to search with
     * @return returns a ResponseEntity consisting of the deleted user if present or a Not Found error if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MultiUser> deleteUser(@PathVariable("id") long id) {
        MultiUser removed = repo.getById(id);
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        repo.delete(repo.getById(id));
        return ResponseEntity.ok(removed);
    }

}
