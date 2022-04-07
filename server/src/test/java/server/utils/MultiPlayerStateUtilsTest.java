package server.utils;

import commons.misc.Activity;
import commons.misc.GameResponse;
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
    private MockScoreCountingUtils scoreCounting;

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

    private GameResponse gameResponse1;
    private GameResponse gameResponse2;
    private GameResponse gameResponse3;

    private MultiPlayer playerA1;
    private MultiPlayer playerA2;
    private MultiPlayer playerA3;
    private MultiPlayer playerB1;
    private MultiPlayer playerB2;
    private MultiPlayer playerC1;
    private MultiPlayer playerC2;

    @BeforeEach
    void setUp() {
        activityRepository = new MockActivityRepository();
        random = new Random(0);
        generateQuestionUtils = new MockGenerateQuestionUtils(activityRepository, random);

        currentTime = new MockCurrentTimeUtils();
        queueUtils = new MockQueueUtils(currentTime);
        scoreCounting = new MockScoreCountingUtils();

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
                MultiPlayerState.NOT_STARTED_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateStarted = new MultiPlayerState(
                0,
                currentTime.currentTime + 3000,
                -1,
                questions,
                new ArrayList<>(),
                MultiPlayerState.STARTING_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateQuestion0 = new MultiPlayerState(
                0,
                /*
                Sets nextPhase of this multiplayer state object to be the end of the
                current QUESTION_STATE
                 */
                currentTime.currentTime + 11000,
                0,
                questions,
                new ArrayList<>(),
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateQuestion19 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 19 + 8 + 3 * 5),
                19,
                questions,
                new ArrayList<>(),
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateTransition0 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11),
                0,
                questions,
                new ArrayList<>(),
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateTransition4 = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 5),
                4,
                questions,
                new ArrayList<>(),
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateLeaderboard = new MultiPlayerState(
                0,
                currentTime.currentTime + 1000 * (3 + 11 * 5 + 5),
                4,
                questions,
                new ArrayList<>(),
                MultiPlayerState.LEADERBOARD_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiPlayerStateGameOver = new MultiPlayerState(
                0,
                Long.MAX_VALUE,
                19,
                questions,
                new ArrayList<>(),
                MultiPlayerState.GAME_OVER_STATE,
                new ArrayList<>(),
                new ArrayList<>()
        );
        multiUtils = new MultiPlayerStateUtils(generateQuestionUtils, queueUtils, currentTime, scoreCounting);

        setResponses();
    }

    void setResponses() {
        /*
        Gets the current question being asked - in the particular case, it would be the first question.
         */
        AbstractQuestion questionAsked = multiPlayerStateQuestion0
                .getQuestionList().get(multiPlayerStateQuestion0.getRoundNumber());
        gameResponse1 = new GameResponse(
                0,
                /*
                The response would be submitted just 1 second before the end of the first round.
                The multiPlayerStateQuestion0 instance would be used.
                 */
                currentTime.currentTime + 10000,
                0,
                "Client A",
                questionAsked.getCorrectAnswer()
        );

        gameResponse2 = new GameResponse(
                0,
                /*
                The response would be submitted 3 seconds before the end of the first round.
                The multiPlayerStateQuestion0 instance would be used.
                 */
                currentTime.currentTime + 8000,
                0,
                "Client A",
                questionAsked.getCorrectAnswer()
        );

        gameResponse3 = new GameResponse(
                0,
                /*
                The response would be submitted just 1 second before the end of the first round.
                The multiPlayerStateQuestion0 instance would be used.
                 */
                currentTime.currentTime + 10000,
                0,
                "Client A",
                /*
                The answer though, would be slightly different from the actual one.
                 */
                String.valueOf(Long.parseLong(questionAsked.getCorrectAnswer()) - 400)
        );
    }

    @BeforeEach
    void setupPlayers() {
        playerA1 = new MultiPlayer(
                "Client A",
                0,
                true,
                true,
                true
        );
        playerA2 = new MultiPlayer(
                "Client A",
                1000,
                true,
                true,
                true
        );
        playerA3 = new MultiPlayer(
                "Client A",
                0,
                true,
                true,
                true
        );
        playerB1 = new MultiPlayer(
                "Client B",
                0,
                true,
                true,
                true
        );
        playerB2 = new MultiPlayer(
                "Client B",
                0,
                true,
                false,
                true
        );
        playerC1 = new MultiPlayer(
                "Client C",
                0,
                true,
                true,
                true
        );
        playerC2 = new MultiPlayer(
                "Client C",
                100,
                false,
                false,
                false
        );
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
                false
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
                false
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
                false
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
                false
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
                MultiPlayerState.QUESTION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
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
                MultiPlayerState.TRANSITION_STATE,
                new ArrayList<>(),
                new ArrayList<>()
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

    @Test
    void containsPlayerSingle() {
        // Start a game
        multiUtils.startNewGame();

        multiUtils.addPlayer(0, playerA1);
        assertFalse(multiUtils.containsPlayer(playerB1, multiUtils.getGameState(0)));
        assertFalse(multiUtils.containsPlayer(playerB2, multiUtils.getGameState(0)));
        assertFalse(multiUtils.containsPlayer(playerC1, multiUtils.getGameState(0)));
        assertFalse(multiUtils.containsPlayer(playerC2, multiUtils.getGameState(0)));

        assertTrue(multiUtils.containsPlayer(playerA1, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerA2, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerA3, multiUtils.getGameState(0)));
    }

    @Test
    void containsPlayerMultiple() {
        // Start a game
        multiUtils.startNewGame();

        multiUtils.addPlayer(0, playerA1);
        multiUtils.addPlayer(0, playerB1);
        assertFalse(multiUtils.containsPlayer(playerC1, multiUtils.getGameState(0)));
        assertFalse(multiUtils.containsPlayer(playerC2, multiUtils.getGameState(0)));

        assertTrue(multiUtils.containsPlayer(playerA1, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerA2, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerA3, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerB1, multiUtils.getGameState(0)));
        assertTrue(multiUtils.containsPlayer(playerB2, multiUtils.getGameState(0)));
    }

    @Test
    void correctAnswerPointDistribution1secondRemaining() {
        int scoreCount = scoreCounting.computeScore(multiPlayerStateQuestion0, null, gameResponse1);
        assertEquals(100 + 1 * 50, scoreCount);
    }

    @Test
    void correctAnswerPointDistribution3secondsRemaining() {
        int scoreCount = scoreCounting.computeScore(multiPlayerStateQuestion0, null, gameResponse2);
        assertEquals(100 + 3 * 50, scoreCount);
    }

    @Test
    void approximateAnswerPointDistribution() {
        int scoreCount = scoreCounting.computeScore(multiPlayerStateQuestion0, null, gameResponse3);
        assertEquals(100 + 1 * 50 - .1 * 400, scoreCount);
    }

    /**
     * Answer posted even if the player is not in the current game.
     * Happens as posting an answer does not include "player-check" anywhere.
     */
    @Test
    void postAnswerValidGame() {
        // Start a game.
        Long gameId = multiUtils.startNewGame();

        GameResponse response = new GameResponse(
                gameId,
                /*
                The answer is posted during the 5th second of the QUESTION_STATE,
                Note that during the first 3 seconds, the players are in "preparation"
                in the queue - countdown happens.
                 */
                currentTime.currentTime + 8000,
                0,
                "Client A",
                "MockAnswer"
        );
        multiUtils.postAnswer(response);

        assertEquals(multiUtils.getGameState(gameId).getSubmittedAnswers(), List.of(response));
    }

    @Test
    void postAnswerInvalidGame() {
        // Start a game.
        Long gameId = multiUtils.startNewGame();

        GameResponse response = new GameResponse(
                Integer.MIN_VALUE,
                currentTime.currentTime + 8000,
                0,
                "Client A",
                "MockAnswer"
        );
        assertNull(multiUtils.postAnswer(response));
    }

    @Test
    void computeFinalAnswerEmptyList() {
        assertNull(multiUtils.computeFinalAnswer(new ArrayList<>()));
    }

    @Test
    void computeFinalAnswerLastResponse() {
        /*
        In theory, gameResponse2 should be submitted first, at its time is "lowest".
         */
        assertEquals(gameResponse3,
                multiUtils.computeFinalAnswer(List.of(gameResponse2, gameResponse1, gameResponse3)));
    }

    @Test
    void computeFinalAnswerSameSubmission() {
        /*
        Both gameResponse1 and gameResponse2 choose the same answer -the correct one. So, the algorithm
        should return the firstly submitted one.
         */
        assertEquals(gameResponse2,
                multiUtils.computeFinalAnswer(List.of(gameResponse2, gameResponse1)));
    }
}