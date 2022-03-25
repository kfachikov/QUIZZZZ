package server.utils;

import commons.misc.Activity;
import commons.multi.MultiPlayerState;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private MultiPlayerState multiPlayerStateQ1;

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

        currentTime.currentTime = 12345;

        multiPlayerStateNew = new MultiPlayerState(
                0,
                Long.MAX_VALUE,
                -1,
                questions,
                new ArrayList<>(),
                MultiPlayerState.NOT_STARTED_STATE,
                new ArrayList<>(),
                null
        );

        multiPlayerStateStarted = new MultiPlayerState(
                0,
                12345 + 3000,
                -1,
                questions,
                new ArrayList<>(),
                MultiPlayerState.STARTING_STATE,
                new ArrayList<>(),
                null

        );


        multiUtils = new MultiPlayerStateUtils(generateQuestionUtils, queueUtils, currentTime);
    }

    @Test
    void getGameState() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void updateState() {
    }

    @Test
    void checkIfUpdate() {
    }

    @Test
    void switchState() {
    }

    @Test
    void switchToQuestion() {
    }

    @Test
    void switchToLeaderboard() {
    }

    @Test
    void switchToGameOver() {
    }

    @Test
    void switchToTransition() {
    }

    @Test
    void startNewGameReturn() {
        assertEquals(0, multiUtils.startNewGame());
    }

    @Test
    void updateScores() {

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