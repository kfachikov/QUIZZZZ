package server.utils;

import commons.misc.Activity;
import commons.multi.MultiPlayer;
import commons.multi.MultiPlayerState;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerStateUtilsTest {

    private MockActivityRepository activityRepository;
    private Random random;
    private MockGenerateQuestionUtils generateQuestionUtils;

    private ArrayList<AbstractQuestion> questions;

    private MockCurrentTimeUtils currentTime;
    private MockQueueUtils queueUtils;

    private MultiPlayerStateUtils multiUtils;


    private MultiPlayerState multiPlayerStateNew;
    private MultiPlayerState multiPlayerStateStarted;
    private MultiPlayerState multiPlayerStateQuestion0;
    private MultiPlayerState multiPlayerStateTransition0;
    private MultiPlayerState multiPlayerStateTransition4;
    private MultiPlayerState multiPlayerStateLeaderboard;
    private MultiPlayerState multiPlayerStateQuestion19;
    private MultiPlayerState multiPlayerStateGameOver;

    private Activity activity1;
    private Activity activity2;
    private Activity activity3;

    @BeforeEach
    void setUp() {
        activityRepository = new MockActivityRepository();
        random = new Random(0);
        generateQuestionUtils = new MockGenerateQuestionUtils(activityRepository, random);

        currentTime = new MockCurrentTimeUtils();
        queueUtils = new MockQueueUtils(currentTime);

        activity1 = new Activity("i1", "t1", "s1", "m1", 1L);
        activity2 = new Activity("i2", "t2", "s2", "m2", 2L);
        activity3 = new Activity("i3", "t3", "s3", "m3", 3L);


        questions = new ArrayList<>();
        var activities = List.of(activity1, activity2, activity3);
        for (int i = 0; i < 20; i++) {
            questions.add(new GuessQuestion(activities.get(i % 3)));
        }
        generateQuestionUtils.returnValue = questions;

        currentTime.currentTime = 345;

        multiPlayerStateNew = new MultiPlayerState(
                0,
                Long.MAX_VALUE,
                -1,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.NOT_STARTED_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateStarted = new MultiPlayerState(
                0,
                currentTime.currentTime + 3000,
                -1,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.STARTING_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateQuestion0 = new MultiPlayerState(
                0,
                currentTime.currentTime + 11000,
                0,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateQuestion19 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 19 + 8 + 3 * 5),
                19,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateTransition0 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11),
                0,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateTransition4 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 5),
                4,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateLeaderboard = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 5 + 5),
                4,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.LEADERBOARD_STATE,
                new ArrayList<>(),
                null
        );
        multiPlayerStateGameOver = new MultiPlayerState(
                0,
                Long.MAX_VALUE,
                19,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.GAME_OVER_STATE,
                new ArrayList<>(),
                null
        );


        multiUtils = new MultiPlayerStateUtils(generateQuestionUtils, queueUtils, currentTime);
    }


    @Test
    void addPlayerWrongGame() {
        var result = multiUtils.addPlayer(20, new MultiPlayer("Test", 0, true, true, true));
        assertNull(result);
    }

    @Test
    void addPlayerNull() {
        multiUtils.startNewGame();

        var result = multiUtils.addPlayer(0, null);
        assertNull(result);
        assertEquals(0, multiUtils.getGameState(0).getPlayers().size());
    }

    @Test
    void addPlayerNullName() {
        multiUtils.startNewGame();

        var player = new MultiPlayer(
                null,
                0,
                true,
                true,
                true
        );

        var result = multiUtils.addPlayer(0, player);
        assertNull(result);
        assertEquals(0, multiUtils.getGameState(0).getPlayers().size());
    }

    @Test
    void addPlayerEmptyName() {
        multiUtils.startNewGame();

        var player = new MultiPlayer(
                "",
                0,
                true,
                true,
                true
        );

        var result = multiUtils.addPlayer(0, player);
        assertNull(result);
        assertEquals(0, multiUtils.getGameState(0).getPlayers().size());
    }

    @Test
    void addPlayerExisting() {
        multiUtils.startNewGame();

        var player = new MultiPlayer(
                "Client",
                0,
                true,
                true,
                true
        );

        multiUtils.addPlayer(0, player);

        var result = multiUtils.addPlayer(0, player);
        assertNull(result);
        assertEquals(1, multiUtils.getGameState(0).getPlayers().size());
    }

    @Test
    void addPlayer() {
        multiUtils.startNewGame();

        var player = new MultiPlayer(
                "Client",
                0,
                true,
                true,
                true
        );

        var result = multiUtils.addPlayer(0, player);
        assertEquals(player, result);
        assertEquals(1, multiUtils.getGameState(0).getPlayers().size());
    }

    @Test
    void updateStateNothing() {
        currentTime.currentTime += 10500;

        multiUtils.updateState(multiPlayerStateQuestion0);

        assertEquals(new MultiPlayerState(
                0,
                345 + 11000,
                0,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                null
        ), multiPlayerStateQuestion0);
    }

    @Test
    void updateStateDo() {
        currentTime.currentTime += 11500;

        multiUtils.updateState(multiPlayerStateQuestion0);

        assertEquals(new MultiPlayerState(
                0,
                345 + 1000 * (3 + 11),
                0,
                questions,
                new ArrayList<>(),
                new ArrayList<>(),
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                null
        ), multiPlayerStateQuestion0);
    }

    /*
     * private MultiPlayerState multiPlayerStateNew;
     *     private MultiPlayerState multiPlayerStateStarted;
     *     private MultiPlayerState multiPlayerStateQuestion0;
     *     private MultiPlayerState multiPlayerStateTransition0;
     *     private MultiPlayerState multiPlayerStateTransition4;
     *     private MultiPlayerState multiPlayerStateLeaderboard;
     *     private MultiPlayerState multiPlayerStateQuestion19;
     *     private MultiPlayerState multiPlayerStateGameOver;
     */

    @Test
    void checkIfUpdate() {
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateNew));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateStarted));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateQuestion0));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateTransition0));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateTransition4));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateLeaderboard));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateQuestion19));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateGameOver));


        currentTime.currentTime += 500;
        currentTime.currentTime += 3000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateStarted));
        currentTime.currentTime += 8000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateQuestion0));
        currentTime.currentTime += 3000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateTransition0));
        currentTime.currentTime += 4 * 11 * 1000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateTransition4));
        currentTime.currentTime += 5000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateLeaderboard));
        currentTime.currentTime += 2 * 5000 + 15 * 11 * 1000;
        assertTrue(multiUtils.checkIfUpdate(multiPlayerStateQuestion19));

        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateNew));
        assertFalse(multiUtils.checkIfUpdate(multiPlayerStateGameOver));
    }

    @Test
    void switchStateQuestion0() {
        multiUtils.switchState(multiPlayerStateStarted);

        assertEquals(multiPlayerStateQuestion0, multiPlayerStateStarted);
    }

    @Test
    void switchStateTransition0() {
        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        assertEquals(multiPlayerStateTransition0, multiPlayerStateStarted);
    }

    @Test
    void switchStateTransition4() {
        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        assertEquals(multiPlayerStateTransition4, multiPlayerStateStarted);
    }

    @Test
    void switchStateLeaderboard() {
        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);
        multiUtils.switchState(multiPlayerStateStarted);

        multiUtils.switchState(multiPlayerStateStarted);

        assertEquals(multiPlayerStateLeaderboard, multiPlayerStateStarted);
    }

    @Test
    void switchStateQuestion19() {
        for (int i = 0; i < 19; i++) {
            multiUtils.switchState(multiPlayerStateStarted);
            multiUtils.switchState(multiPlayerStateStarted);

            if (i % 5 == 4) {
                multiUtils.switchState(multiPlayerStateStarted);
            }
        }

        multiUtils.switchState(multiPlayerStateStarted);

        assertEquals(multiPlayerStateQuestion19, multiPlayerStateStarted);
    }

    @Test
    void switchStateGameOver() {
        multiUtils.switchState(multiPlayerStateQuestion19);
        multiUtils.switchState(multiPlayerStateQuestion19);

        assertEquals(multiPlayerStateGameOver, multiPlayerStateQuestion19);
    }

    @Test
    void switchToQuestion() {
        multiUtils.switchToQuestion(multiPlayerStateStarted);
        assertEquals(multiPlayerStateQuestion0, multiPlayerStateStarted);
    }

    @Test
    void switchToLeaderboard() {
        multiUtils.switchToLeaderboard(multiPlayerStateTransition4);
        assertEquals(multiPlayerStateLeaderboard, multiPlayerStateTransition4);
    }

    @Test
    void switchToGameOver() {
        multiUtils.switchToGameOver(multiPlayerStateQuestion19);
        assertEquals(multiPlayerStateGameOver, multiPlayerStateQuestion19);
    }

    @Test
    void switchToTransition() {
        multiUtils.switchToTransition(multiPlayerStateQuestion0);
        assertEquals(multiPlayerStateTransition0, multiPlayerStateQuestion0);
    }

    @Test
    void startNewGameReturn() {
        assertEquals(0, multiUtils.startNewGame());
    }

    @Test
    void updateScores() {
        // Currently, this method doesn't do anything.

    }

    @Test
    void startNewGame() {
        long id = multiUtils.startNewGame();

        assertEquals(multiPlayerStateStarted, multiUtils.getGameState(id));
    }

    @Test
    void createNextGame() {
        MultiPlayerState game = multiUtils.createNextGame();

        assertEquals(multiPlayerStateNew, game);
    }

    @Test
    void generateNextGameIdEmpty() {
        assertEquals(0, multiUtils.generateNextGameId());
    }

    @Test
    void generateNextGameIdNonEmpty() {
        // Start two games
        multiUtils.startNewGame();
        multiUtils.startNewGame();

        assertEquals(2, multiUtils.generateNextGameId());
    }
}