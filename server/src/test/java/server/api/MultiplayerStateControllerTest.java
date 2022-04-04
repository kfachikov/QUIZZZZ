package server.api;

import commons.multi.ChatMessage;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import server.utils.MockMultiPlayerStateUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MultiplayerStateControllerTest {

    private MockMultiPlayerStateUtils multiUtils;

    private MultiplayerStateController multiCtrl;

    private MultiPlayerState game;
    private MultiPlayer player;

    @BeforeEach
    void setup() {
        multiUtils = new MockMultiPlayerStateUtils();

        multiCtrl = new MultiplayerStateController(multiUtils);

        game = new MultiPlayerState(
                0,
                3234,
                -1,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.STARTING_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        player = new MultiPlayer(
                "Test username",
                0,
                true, true, true
        );
    }

    @Test
    void getGameStateNegativeId() {
        var result = multiCtrl.getGameState(-1L);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEqualLists(new ArrayList<>(), multiUtils.calledMethods);
    }

    @Test
    void getGameStateNotFound() {
        // Set getGameState to null
        multiUtils.returnValues.add(null);

        var result = multiCtrl.getGameState(120L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEqualLists(List.of("getGameState"), multiUtils.calledMethods);
        assertEqualLists(List.of(120L), multiUtils.params);
    }

    @Test
    void getGameState() {
        // Set getGameState to null
        multiUtils.returnValues.add(game);

        var result = multiCtrl.getGameState(120L);

        assertEquals(game, result.getBody());
        assertEqualLists(List.of("getGameState"), multiUtils.calledMethods);
        assertEqualLists(List.of(120L), multiUtils.params);
    }

    @Test
    void addMultiPlayerNegativeId() {
        var result = multiCtrl.addMultiPlayer(-1, player);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEqualLists(List.of(), multiUtils.calledMethods);
    }

    @Test
    void addMultiPlayerBadPlayer() {
        // Set addPlayer to null
        multiUtils.returnValues.add(null);

        var result = multiCtrl.addMultiPlayer(120L, player);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEqualLists(List.of("addPlayer"), multiUtils.calledMethods);
        assertEqualLists(List.of(120L, player), multiUtils.params);
    }

    @Test
    void addMultiPlayer() {
        // Set addPlayer to dummy
        var dummy = new MultiPlayer("dummy", 0, true, true, true);
        multiUtils.returnValues.add(dummy);

        var result = multiCtrl.addMultiPlayer(120L, player);

        assertEquals(dummy, result.getBody());
        assertEqualLists(List.of("addPlayer"), multiUtils.calledMethods);
        assertEqualLists(List.of(120L, player), multiUtils.params);
    }

    @Test
    void postReaction() {
        var dummy = new ChatMessage("Kayra", "laughing");
        multiUtils.returnValues.add(game);
        var result = multiCtrl.postReaction(0, dummy).getBody();
        assertEquals(result, dummy);
    }

    @Test
    void postReactionFalse() {
        var dummy = new ChatMessage("Kayra", "laughing");
        multiUtils.returnValues.add(game);
        var result = multiCtrl.postReaction(-1, dummy).getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, result);
    }

    private <T> void assertEqualLists(List<? extends T> expected, List<? extends T> result) {
        if (expected != result) {
            if (expected == null || result == null) {
                fail();
            } else if (expected.size() != result.size()) {
                assertEquals(expected.size(), result.size());
            } else {
                for (int i = 0; i < expected.size(); i++) {
                    assertEquals(expected.get(i), result.get(i));
                }
            }
        }
    }
}