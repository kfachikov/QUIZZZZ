package server.api;

import commons.SingleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("")
    public List<SingleUser> getAllUsers() {
        return repo.findAll();
    }

    /**
     * @param user the user to be added to the SingleUser repository
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<SingleUser> add(@RequestBody SingleUser user) {
        if (user == null || isNullOrEmpty(user.username)) {
            return ResponseEntity.badRequest().build();
        }
        SingleUser saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
