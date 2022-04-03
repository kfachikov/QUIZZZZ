package server.api;

import commons.misc.GameResponse;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.multi.Reaction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.utils.MultiPlayerStateUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Controller responsible for handling the client requests regarding any multiplayer game.
 */
@ComponentScan(basePackageClasses = MultiPlayerStateUtils.class)
@RestController
@RequestMapping("/api/multi")
public class MultiplayerStateController {

    private final MultiPlayerStateUtils multiUtils;

    /**
     * Constructor for multiplayer state controller.
     *
     * @param multiUtils Multiplayer state utilities.
     */
    public MultiplayerStateController(MultiPlayerStateUtils multiUtils) {
        this.multiUtils = multiUtils;
    }

    /**
     * GET mapping for multiplayer game state.
     * <p>
     * Internally, the state of the game will be updated to be complete up to date.
     * This means that is might switch to another state (e.g. TRANSITION to QUESTION),
     * and it might change the players' scores.
     * <p>
     * Expectation is that this endpoint will be called at least every 500 ms.
     *
     * @param id Id for the multiplayer game.
     * @return Up to date MultiPlayerState
     */
    @GetMapping("/{id}")
    public ResponseEntity<MultiPlayerState> getGameState(@PathVariable("id") long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        } else {
            MultiPlayerState game = multiUtils.getGameState(id);
            if (game == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(game);
            }
        }
    }

    /**
     * POST mapping for emoji reactions.
     * <p>
     * The POST endpoint is used to send the reaction when a player sends a reaction.
     * The reaction is added to the list of reactions, if it is valid.
     * </p>
     *
     * @param id       id of the game the reaction was sent in.
     * @param reaction the reaction sent by the player.
     * @return         the reaction that was sent.
     */
    @PostMapping("/reaction/{id}")
    public ResponseEntity<Reaction> postReaction(@PathVariable("id") long id, @RequestBody Reaction reaction) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        } else {
            MultiPlayerState game = multiUtils.getGameState(id);
            if (game == null) {
                return ResponseEntity.notFound().build();
            }
            game.getReactionList().add(reaction);
            return ResponseEntity.ok(reaction);
        }
    }

    /**
     * POST mapping for a player in a multiplayer game.
     * <p>
     * Adds the player to the game with the given id.
     * </p>
     *
     * @param id     Id of the multiplayer game.
     * @param player MultiPlayer that is added.
     * @return MultiPlayer that was added
     */
    @PostMapping("/players/{id}")
    public ResponseEntity<MultiPlayer> addMultiPlayer(@PathVariable("id") long id, @RequestBody MultiPlayer player) {
        if (id < 0) {
            return ResponseEntity.badRequest().build();
        } else {
            MultiPlayer multiPlayer = multiUtils.addPlayer(id, player);
            if (multiPlayer == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok(multiPlayer);
            }
        }
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
        GameResponse responsePosted = multiUtils.postAnswer(response);
        if (responsePosted != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * POST mapping for time joker.
     *
     * @param id     Id of the multiplayer game.
     * @param player Multiplayer that is using the joker.
     */
    @PostMapping("/timeJoker/{id}")
    public void postTimeJoker(@PathVariable("id") long id, @RequestBody MultiPlayer player) {
        MultiPlayerState game = multiUtils.getGameState(id);
        MultiPlayer playerNew = game.getPlayerByUsername(player.getUsername());
        playerNew.setTimeJoker(false);
        game.setPlayerUsingTimeJoker(playerNew);
        resetJoker(500, id);
    }

    /**
     * GET mapping for time joker.
     *
     * @param id Id of the multiplayer game.
     * @return multiplayer who used the joker.
     */
    @GetMapping("/timeJoker/{id}")
    public ResponseEntity<MultiPlayer> getTimeJoker(@PathVariable("id") long id) {
        return ResponseEntity.ok(multiUtils.getGameState(id).getPlayerUsingTimeJoker());
    }

    /**
     * Method that resets the player who sent the joker.
     *
     * @param delay the interval for resetting the joker.
     * @param id id of the multiplayer game.
     */
    private void resetJoker(long delay, long id) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                multiUtils.getGameState(id).setPlayerUsingTimeJoker(null);
            }
        }, delay);
    }
}
