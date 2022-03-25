package server.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class MultiPlayerStateUtilsTest {

    private MockActivityRepository activityRepository;
    private Random random;
    private MockGenerateQuestionUtils generateQuestionUtils;

    private MockCurrentTimeUtils currentTime;
    private MockQueueUtils queueUtils;

    private MultiPlayerStateUtils multiUtils;

    @BeforeEach
    void setUp() {
        activityRepository = new MockActivityRepository();
        random = new Random(0);
        generateQuestionUtils = new MockGenerateQuestionUtils(activityRepository, random);

        currentTime = new MockCurrentTimeUtils();
        queueUtils = new MockQueueUtils(currentTime);

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
    void startNewGame() {
    }

    @Test
    void createNextGame() {
    }

    @Test
    void generateNextGameId() {
    }
}