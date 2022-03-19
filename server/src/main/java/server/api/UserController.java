package server.api;

import commons.single.SingleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    /**
     * Constructor for the user controller.
     * @param repo User repository final instance
     */
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Get endpoint that retrieves all users from the user repository.
     * @return the users existing in the repository
     */
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

    /**
     * Checks whether a string is empty or not.
     * @param s String variable
     * @return true/false
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
    
}
