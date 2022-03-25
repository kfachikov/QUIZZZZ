package server.api;


import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.utils.QueueUtils;

/**
 * Controller responsible for managing the state of the queue.
 * <p>
 * Most logic is delegated to QueueUtils, so this class only sets up the endpoints for the queue.
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueUtils queueUtils;

    /**
     * Constructor for the QueueController.
     *
     * @param queueUtils QueueUtils instance, responsible for logic.
     */
    public QueueController(QueueUtils queueUtils) {
        this.queueUtils = queueUtils;
    }

    /**
     * Get the current state of the queue.
     * The state of the queue contains the following items:
     * - List of users in the queue
     *
     * @return the current state of the queue
     */
    @GetMapping("")
    public ResponseEntity<QueueState> getQueueState() {
        return ResponseEntity.ok(queueUtils.getQueue());
    }


    /**
     * First if handles the case when the username entered is empty.
     * Second one corresponds to username already in database case (not unique per multiplayer queue).
     *
     * @param user the user to be added to the QueueUser repository
     * @return response
     */
    @PostMapping("")
    public ResponseEntity<QueueUser> add(@RequestBody QueueUser user) {
        if (queueUtils.isInvalid(user)) {
            return ResponseEntity.badRequest().build();
        } else if (queueUtils.containsUser(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(queueUtils.addUser(user));
    }

    /**
     * Starts the multiplayer game with the users inside the queue.
     * <p>
     * The start of the game is set to be 3 seconds after this endpoint is called.
     *
     * @return The updated state of the game.
     */
    @PostMapping("/start")
    public ResponseEntity<QueueState> startGame() {
        if (queueUtils.startCountdown()) {
            return ResponseEntity.ok(queueUtils.getQueue());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Delete a user if present from the repository.
     *
     * @param username Username of the user to be deleted from the queue
     * @return returns a ResponseEntity consisting of the deleted user if present or a Not Found error if not found.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<QueueUser> deleteUser(@PathVariable("username") String username) {
        QueueUser target = queueUtils.getByUsername(username);
        QueueUser removed = queueUtils.removeUser(target);
        if (removed != null) {
            return ResponseEntity.ok(removed);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
