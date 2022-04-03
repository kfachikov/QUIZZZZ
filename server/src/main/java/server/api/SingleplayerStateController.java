package server.api;

import commons.misc.GameResponse;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.utils.SinglePlayerStateUtils;

/**
 * Controller responsible for handling the client requests regarding any single-player game.
 */
@Configuration
@ComponentScan(basePackageClasses = SinglePlayerStateUtils.class)
@RestController
@RequestMapping("/api/solo")
public class SingleplayerStateController {


    private final ActivityRepository repo;
    private final SinglePlayerStateUtils singlePlayerStateUtils;

    /**
     * Constructor for singleplayer state controller.
     * <p>
     * Initializes the list of games inside of the controller.
     *
     * @param repo                   ActivityRepository instance.
     * @param singlePlayerStateUtils injected instance of SinglePlayerStateUtils
     */
    public SingleplayerStateController(ActivityRepository repo, SinglePlayerStateUtils singlePlayerStateUtils) {
        this.repo = repo;
        this.singlePlayerStateUtils = singlePlayerStateUtils;
    }

    /**
     * GET mapping for the singleplayer game state.
     * <p>
     * Internally, the state of the game will be updated. That means, it might
     * switch to another state (e.g. from QUESTION_STATE to TRANSITION_STATE),
     * and it might increase the players' scores.
     * <p>
     * There is expectation that this endpoint will be called every about 500 ms.
     *
     * @param id Id for the singleplayer game
     * @return SinglePlayerState after updating internal state.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SinglePlayerState> getGameState(@PathVariable("id") long id) {
        SinglePlayerState game = singlePlayerStateUtils.getGameStateById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST mapping for starting a new singleplayer game.
     * <p>
     * Constructs a new game object and inserts it in the list of games.
     *
     * @param player SinglePlayer that is starting the game.
     * @return Newly started singleplayer game state
     */
    @PostMapping("/start")
    public ResponseEntity<SinglePlayerState> startSingleGame(@RequestBody SinglePlayer player) {
        SinglePlayerState newGame = singlePlayerStateUtils.createSingleGame(player, repo);
        return ResponseEntity.ok(newGame);
    }

    /**
     * POST mapping for a response from a player.
     * <p>
     * Inserts the new response in the appropriate game for that player.
     *
     * @param response GameResponse that the player selected.
     * @return GameResponse that the player just chose.
     */
    @PostMapping("/answer")
    public ResponseEntity<GameResponse> postResponse(@RequestBody GameResponse response) {
        GameResponse responsePosted = singlePlayerStateUtils.postAnswer(response);
        if (responsePosted != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<Boolean> getServerResponse() {
        Boolean serverCheck = true;
        return ResponseEntity.ok(serverCheck);
    }
}
