package server.utils;

import commons.misc.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GenerateQuestionUtilsTest {

    private MockRandom random;
    private MockActivityRepository repo;

    private GenerateQuestionUtils gqUtils;

    private List<Activity> activities;

    private <T> List<T> sublist(List<T> list, long from, long to) {
        List<T> result = new ArrayList<>();
        for (int i = (int) from; i < to; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private void setTrueRandom() {
        Random rng = new Random(0);
        for (int i = 0; i < 1000; i++) {
            random.returnValues.add(rng.nextInt());
        }
    }

    @BeforeEach
    void setUp() {
        random = new MockRandom();
        repo = new MockActivityRepository();

        gqUtils = new GenerateQuestionUtils(repo, random);

        // Setup for initialisation
        for (int i = 150; i >= 0; i--) {
            random.returnValues.add(i);
        }

        activities = new ArrayList<>();
        double consumption = 100.0;
        for (int i = 0; i < 150; i++) {
            Activity activity = new Activity(
                    "id-" + i,
                    "title " + i,
                    "source " + i,
                    "image " + i,
                    (long) consumption
            );
            activity.setKey((long) i);
            if (i % 5 == 0 || i % 4 == 0) {
                consumption *= 1.5;
            }
            activities.add(activity);
        }

        repo.repoActivities.addAll(activities);
    }

    @Test
    void initialize() {
        assertDoesNotThrow(() -> {
            gqUtils.initialize();
        });
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
        for (int i = 0; i < 150; i++) {
            Activity expected = activities.get(current);
            Activity result = gqUtils.getNextActivity();

            assertEquals(expected, result);
            current = (current + 1) % 150;
        }
    }

    @Test
    void activityPredicate() {
        Predicate<Activity> predicate = gqUtils.activityPredicate(15000L, 15000000L);
        List<Activity> result = activities.stream().filter(predicate).collect(Collectors.toList());
        assertEquals(42, result.size());
    }

    @Test
    void activitiesWithinRange1() {
        gqUtils.initialize();

        List<Activity> expected = sublist(activities, 0, 5);
        List<Activity> result = gqUtils.activitiesWithinRange(1, 1);

        assertEquals(expected, result);
    }

    @Test
    void activitiesWithinRange2() {
        gqUtils.initialize();

        List<Activity> expected = sublist(activities, 5, 25);
        List<Activity> result = gqUtils.activitiesWithinRange(1000, 5);

        assertEquals(expected, result);
    }

    @Test
    void compareActivities() {
        assertEquals(-1, gqUtils.compareActivities(activities.get(0), activities.get(1)));
    }

    @Test
    void generateConsumptionQuestion() {
        
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