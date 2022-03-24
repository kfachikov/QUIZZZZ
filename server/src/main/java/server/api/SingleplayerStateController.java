package server.api;

import commons.misc.Response;
import commons.single.SinglePlayerState;
import commons.single.SinglePlayer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.utils.SinglePlayerStateUtils;

import java.util.*;

@Configuration
@ComponentScan(basePackageClasses = SinglePlayerStateUtils.class)
@RestController
@RequestMapping("/api/solo")
public class SingleplayerStateController {

    private final Map<Long, SinglePlayerState> games;
    private final ActivityRepository repo;
    private final Random random;
    private final SinglePlayerStateUtils singlePlayerStateUtils;

    /**
     * Constructor for singleplayer state controller.
     * <p>
     * Initializes the list of games inside of the controller.
     */
    public SingleplayerStateController(ActivityRepository repo, SinglePlayerStateUtils singlePlayerStateUtils, Random random) {
        this.repo = repo;
        this.random = random;
        this.singlePlayerStateUtils = singlePlayerStateUtils;
        this.games = new HashMap<>();
    }

    /**
     * GET mapping for the singleplayer game state.
     * <p>
     * Internally, the state of the game will be updated. That means, it might
     * switch to another state (e. g. from QUESTION_STATE to TRANSITION_STATE),
     * and it might increase the players' scores.
     * <p>
     * There is expectation that this endpoint will be called every about 500 ms.
     *
     * @param id Id for the singleplayer game
     * @return SinglePlayerState after updating internal state.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SinglePlayerState> getGameState(@PathVariable("id") long id) {
        if (games.containsKey(id)) {
            SinglePlayerState game = games.get(id);
            singlePlayerStateUtils.updateState(game);
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
        SinglePlayerState newGame = singlePlayerStateUtils.createSingleGame(player, games, random, repo);
        games.put(newGame.getId(), newGame);
        return ResponseEntity.ok(newGame);
    }

    /**
     * POST mapping for a response from a player.
     * <p>
     * Inserts the new response in the appropriate game for that player.
     *
     * @param response Response that the player selected.
     * @return Response that the player just chose.
     */
    @PostMapping("/answer")
    public ResponseEntity<Response> postResponse(@RequestBody Response response) {
        long gameId = response.getGameId();
        if (!games.containsKey(gameId)) {
            return ResponseEntity.notFound().build();
        }
        SinglePlayerState game = games.get(gameId);
        game.getSubmittedAnswers().add(response);
        return ResponseEntity.ok(response);
    }
}
