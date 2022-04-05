package server.utils;

import commons.misc.Activity;
import commons.question.ConsumptionQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GenerateQuestionUtilsTest {

    public static final int ACTIVITY_COUNT = 150;

    private MockRandom random;
    private MockRandom randomSimple;
    private MockActivityRepository repo;
    private MockActivityRepository repoSimple;

    private GenerateQuestionUtils gqUtils;
    private GenerateQuestionUtils gqUtilsSimple;


    private List<Activity> activities;
    private List<Activity> activitiesSimple;

    private <T> List<T> sublist(List<T> list, long from, long to) {
        List<T> result = new ArrayList<>();
        for (int i = (int) from; i < to; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @BeforeEach
    void setUp() {
        random = new MockRandom();
        randomSimple = new MockRandom();
        repo = new MockActivityRepository();
        repoSimple = new MockActivityRepository();

        gqUtils = new GenerateQuestionUtils(repo, random);
        gqUtilsSimple = new GenerateQuestionUtils(repoSimple, randomSimple);

        // Setup for initialisation
        for (int i = ACTIVITY_COUNT; i >= 0; i--) {
            random.returnValues.add(i);
            randomSimple.returnValues.add(i);
        }

        activities = new ArrayList<>();
        activitiesSimple = new ArrayList<>();
        double consumption = 100.0;
        double consumptionSimple = 100.0;

        for (int i = 0; i < ACTIVITY_COUNT; i++) {
            Activity activity = new Activity(
                    "id-" + i,
                    "title " + i,
                    "source " + i,
                    "image " + i,
                    (long) consumption
            );
            Activity activitySimple = new Activity(
                    "id-" + i,
                    "title " + i,
                    "source " + i,
                    "image " + i,
                    (long) consumptionSimple
            );
            activity.setKey((long) i);
            activitySimple.setKey((long) i);
            if (i % 5 == 0 || i % 4 == 0) {
                consumption *= 1.5;
                consumptionSimple *= 1.5;
            }
            if (i % 47 == 46) {
                consumption *= 100;
            }
            if (consumption * 1000.0 > Long.MAX_VALUE) {
                consumption = 120.0;
            }
            activities.add(activity);
            activitiesSimple.add(activitySimple);
        }

        repo.repoActivities.addAll(activities);
        repoSimple.repoActivities.addAll(activitiesSimple);
    }

    @Test
    void initialize() {
        assertDoesNotThrow(() -> {
            gqUtils.initialize();
        });
        assertEquals(ACTIVITY_COUNT - 1, random.calledMethods.size());
    }

    @Test
    void initializeThrows() {
        repo.repoActivities.clear();
        assertThrows(IllegalStateException.class, () -> {
            gqUtils.initialize();
        });
    }

    @Test
    void getNextActivityOnce() {
        gqUtils.initialize();
        // Make sure that random is NOT called.
        random.returnValues = new LinkedList<>();

        Activity expected = activities.get(1);
        Activity result = gqUtils.getNextActivity();

        assertEquals(expected, result);
    }

    @Test
    void getNextActivityTwice() {
        gqUtils.initialize();
        random.returnValues = new LinkedList<>();

        Activity expected1 = activities.get(1);
        Activity expected2 = activities.get(2);
        Activity result1 = gqUtils.getNextActivity();
        Activity result2 = gqUtils.getNextActivity();

        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    @Test
    void getNextActivityAll() {
        gqUtils.initialize();
        random.returnValues = new LinkedList<>();

        int current = 1;
        for (int i = 0; i < ACTIVITY_COUNT; i++) {
            Activity expected = activities.get(current);
            Activity result = gqUtils.getNextActivity();

            assertEquals(expected, result);
            current = (current + 1) % ACTIVITY_COUNT;
        }
    }

    @Test
    void activityPredicate() {
        Predicate<Activity> predicate = gqUtilsSimple.activityPredicate(15000L, 15000000L);
        List<Activity> result = activitiesSimple.stream().filter(predicate).collect(Collectors.toList());
        assertEquals(42, result.size());
    }

    @Test
    void activitiesWithinRange1() {
        gqUtilsSimple.initialize();

        List<Activity> expected = sublist(activities, 0, 5);
        List<Activity> result = gqUtilsSimple.activitiesWithinRange(1, 1);

        assertEquals(expected, result);
    }

    @Test
    void activitiesWithinRange2() {
        gqUtilsSimple.initialize();

        List<Activity> expected = sublist(activities, 5, 25);
        List<Activity> result = gqUtilsSimple.activitiesWithinRange(1000, 5);

        assertEquals(expected, result);
    }

    @Test
    void compareActivities() {
        assertEquals(-1, gqUtils.compareActivities(activities.get(0), activities.get(1)));
    }

    @Test
    void generateConsumptionQuestion() {
        gqUtils.initialize();

        // Random will be called - preparing values.
        for (int i = ACTIVITY_COUNT; i >= 0; i--) {
            random.returnValues.add(i);
        }

        ConsumptionQuestion result = gqUtils.generateConsumptionQuestion(false);

        assertEquals(activities.get(1), result.getActivity());
    }

    @RepeatedTest(1000)
    void generateConsumptionQuestionEasyRange() {
        // Random will be called - preparing values.
        random.returnValues = new LinkedList<>();
        Random rng = new Random();
        for (int i = 0; i < 2 * ACTIVITY_COUNT; i++) {
            random.returnValues.add(rng.nextInt());
        }

        gqUtils.initialize();

        ConsumptionQuestion result = gqUtils.generateConsumptionQuestion(false);

        for (long value : result.getAnswerChoices()) {
            assertTrue(value <= result.getActivity().getConsumption() * 150,
                    value + " <= " + result.getActivity().getConsumption() * 150
            );
            assertTrue(value >= result.getActivity().getConsumption() / 150,
                    value + " >= " + result.getActivity().getConsumption() / 150
            );
        }
    }

    @RepeatedTest(1000)
    void generateConsumptionQuestionHardRange() {
        // Random will be called - preparing values.
        random.returnValues = new LinkedList<>();
        Random rng = new Random();
        for (int i = 0; i < 2 * ACTIVITY_COUNT; i++) {
            random.returnValues.add(rng.nextInt());
        }

        gqUtils.initialize();

        ConsumptionQuestion result = gqUtils.generateConsumptionQuestion(true);

        for (long value : result.getAnswerChoices()) {
            assertTrue(value <= result.getActivity().getConsumption() * 150);
            assertTrue(value >= result.getActivity().getConsumption() / 150);
        }
    }

    @Test
    void generateMoreExpensiveQuestion() {
    }

    @Test
    void generateGuessQuestion() {
    }

    @Test
    void generateInsteadQuestion() {
    }

    @Test
    void generateQuestion() {
    }

    @Test
    void generate20Questions() {
    }
}