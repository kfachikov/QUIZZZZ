package server.utils;

import commons.misc.Activity;
import commons.question.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class responsible for generating random questions.
 */
@Component
@ComponentScan(basePackageClasses = Random.class)
public class GenerateQuestionUtils {

    private final ActivityRepository repo;
    private final Random random;

    // Current index of the id of the activities in activityIds
    private int activityIndex;
    // Randomized list of activity IDs
    private List<Long> activityIds;
    // Map from id of activity to its index in activityIds list
    private Map<Long, Integer> idIndices;

    // Copy of repo.findAll() for streams
    private List<Activity> activitiesCopy;

    // Median consumption in Wh, for guess questions
    private long median;


    /**
     * Constructor for GenerateQuestionUtils.
     *
     * @param repo   ActivityRepository to use when generating the questions.
     * @param random Source of random numbers.
     */
    public GenerateQuestionUtils(ActivityRepository repo, Random random) {
        this.repo = repo;
        this.random = random;
        this.activityIds = new ArrayList<>();
    }

    /**
     * Initialize GenerateQuestionUtils so it can generate unique activities.
     */
    public void initialize() {
        this.activitiesCopy = repo.findAll();

        this.activityIds =
                activitiesCopy.stream()
                        .mapToLong(Activity::getKey)
                        .boxed()
                        .collect(Collectors.toList());
        Collections.shuffle(activityIds, random);
        this.activityIndex = 0;

        if (activityIds.size() < 5) {
            throw new IllegalStateException("Too few activities to generate any questions");
        }

        List<Activity> sortedActivities = activitiesCopy.stream()
                .sorted(this::compareActivities)
                .collect(Collectors.toList());

        this.median = sortedActivities.get(sortedActivities.size() / 2).getConsumption();

        idIndices = new HashMap<>();
        for (int i = 0; i < activityIds.size(); i++) {
            idIndices.put(activityIds.get(i), i);
        }
    }

    /**
     * Gets the next random unique activity from the list of activities.
     *
     * @return Next unique random activity.
     */
    public Activity getNextActivity() {
        long activityId = activityIds.get(activityIndex);
        Optional<Activity> optionalActivity = repo.findById(activityId);

        if (optionalActivity.isEmpty()) {
            throw new IllegalStateException("Activity IDs stored non-existent activity.");
        }
        Activity activity = optionalActivity.get();

        this.activityIndex = (this.activityIndex + 1) % activityIds.size();

        return activity;
    }

    /**
     * Gets N random activities from the given list.
     * <p>
     * Shifts the next activity accordingly.
     *
     * @param source Given list to get the activities from.
     * @param count  N - number of activities to get
     * @return List of N activities, randomly picked
     */
    public List<Activity> getRandomActivities(List<Activity> source, long count) {
        List<Activity> sorted = sortedByNext(source);

        int index = 0;

        List<Activity> result = new ArrayList<>();
        while (result.size() < count) {
            result.add(sorted.get(index++));
        }
        Activity lastActivity = sorted.get(--index);
        activityIndex = idIndices.get(lastActivity.getKey()) + 1;

        return result;
    }

    /**
     * Sorts the given list of activities by the "planned" order of activities.
     *
     * @param list List of activities to sort.
     * @return Sorted activities.
     */
    public List<Activity> sortedByNext(List<Activity> list) {
        List<Activity> sorted = list.stream()
                .sorted(Comparator.comparingInt(a -> idIndices.get(a.getKey())))
                .collect(Collectors.toList());

        List<Activity> before =
                sorted.stream()
                        .filter(activity ->
                                idIndices.get(activity.getKey()) < activityIndex
                        )
                        .collect(Collectors.toList());
        List<Activity> after =
                sorted.stream()
                        .filter(activity ->
                                idIndices.get(activity.getKey()) >= activityIndex
                        )
                        .collect(Collectors.toList());

        List<Activity> result = new ArrayList<>();
        result.addAll(after);
        result.addAll(before);

        return result;
    }

    /**
     * Utility function for constructing a predicate which can filter activities
     * that fall inside the given range.
     *
     * @param lower Lower bound of the range.
     * @param upper Upper bound of the range.
     * @return Predicate which returns true iff activity falls inside the range.
     */
    public Predicate<Activity> activityPredicate(long lower, long upper) {
        return activity -> activity.getConsumption() >= lower &&
                activity.getConsumption() <= upper;
    }

    /**
     * Find all activities within the given range.
     * <p>
     * The range is specified by its center, and the multiplier.
     * <p>
     * For example, if the center is 500 and multiplier is 2,
     * then the range will be between 250 and 1000.
     * <p>
     * Also, if fewer than 4 activities are found, the range is increased until enough
     * activities are found.
     *
     * @param center     Center of the range.
     * @param multiplier Multiplier to multiply/divide the center by to get the range.
     * @return All activities within multiple of the center.
     */
    public List<Activity> activitiesWithinRange(long center, long multiplier) {
        List<Activity> result = new ArrayList<>();


        // Reduce multiplier before loop, since it increases in each iteration of the loop.
        multiplier--;
        while (result.size() < 4) {
            multiplier++;
            result = activitiesCopy.stream()
                    .filter(activityPredicate(center / multiplier,
                            center * multiplier))
                    .collect(Collectors.toList());
        }

        return result;
    }

    /**
     * Compare two activities by their consumption.
     *
     * @param a Activity to be compared against.
     * @param b Activity to compare.
     * @return compareTo result of activity consumptions.
     */
    public int compareActivities(Activity a, Activity b) {
        return a.getConsumption().compareTo(b.getConsumption());
    }

    /**
     * Generate a consumption question.
     * <p>
     * Chooses a random activity, and then 2 activities within a multiplier,
     * based on difficulty.
     * <p>
     * The answer choices are the consumptions of the activities.
     *
     * @param difficult Whether this question should be difficult (decreases relative difference between answers).
     * @return Generated ConsumptionQuestion
     */
    public ConsumptionQuestion generateConsumptionQuestion(boolean difficult) {
        // Generate a random activity
        Activity activity = getNextActivity();

        // Make range of possible values smaller if question is "difficult"
        // Thus, players require more "precision" to answer correctly
        long multiplier = difficult ? 10 : 100;

        long center = activity.getConsumption();

        /*
        Get a list of candidate answers by:
        1. Taking all activities
        2. Filtering only those within the range for good answer choices
        3. Remove the true answer from the list
        4. Repeat process with bigger and bigger range until we have enough candidates
         */
        List<Long> candidateAnswers = new ArrayList<>();

        while (candidateAnswers.size() < 2) {
            candidateAnswers = activitiesWithinRange(center, multiplier).stream()
                    .mapToLong(Activity::getConsumption)
                    .distinct()
                    .filter(answer -> answer != center)
                    .boxed()
                    .collect(Collectors.toList());
            multiplier += 1;
        }

        Collections.shuffle(candidateAnswers, random);

        List<Long> answerChoices = new ArrayList<>();
        // Add correct answer
        answerChoices.add(activity.getConsumption());
        // Add incorrect answers
        answerChoices.add(candidateAnswers.get(0));
        answerChoices.add(candidateAnswers.get(1));

        return new ConsumptionQuestion(activity, answerChoices);
    }

    /**
     * Generate a "which is more expensive" question.
     * <p>
     * Chooses a random "middle" point, then picks some activities around that middle.
     * <p>
     * The correct answer is set to the activity with the highest consumption.
     *
     * @param difficult Whether this question should be difficult (decreases relative difference between choices).
     * @return Generated MoreExpensiveQuestion.
     */
    public MoreExpensiveQuestion generateMoreExpensiveQuestion(boolean difficult) {
        // Pick a random center
        long center = getNextActivity().getConsumption();

        long multiplier = difficult ? 10 : 100;

        List<Activity> answerChoices = activitiesWithinRange(center, multiplier);
        // Limit to 3 answers
        answerChoices = getRandomActivities(answerChoices, 3);

        Activity correct = answerChoices.stream().max(this::compareActivities).get();
        String correctAnswer = correct.getTitle();

        return new MoreExpensiveQuestion(correctAnswer, answerChoices);
    }

    /**
     * Generate a guess question.
     * <p>
     * For difficult questions, simply picks a random activity.
     * <p>
     * For non-difficult questions, picks an activity that is equal to, or below the median based on consumption.
     * The logic for that is: the guess question is rated based on absolute value of the true answer, but activities
     * are mostly following the power law. Thus, generating an activity with a "lower" consumption will give greater
     * odds of correctly guessing.
     *
     * @param difficult Whether this question should be difficult.
     * @return Generated GuessQuestion.
     */
    public GuessQuestion generateGuessQuestion(boolean difficult) {
        Activity activity = getNextActivity();

        // Activities with very high consumptions tend to be very difficult to guess
        // Thus, we restrict it to be below median
        while (!difficult && activity.getConsumption() > median) {
            activity = getNextActivity();
        }

        return new GuessQuestion(activity);
    }

    /**
     * Generate a "which activity can you do instead" question.
     * <p>
     * Picks a random activity, then tries to pick some activities within close range of this activity to
     * generate a "correct" answer (which will never include the actual original activity).
     * <p>
     * For the incorrect answers, simply a larger range is selected, excluding all potential answers from the pool.
     * <p>
     * If incorrect answers cannot be generated, this method does not fail, and instead generates an "un-fun" question
     * by allowing multiple choices which are all close to each other.
     *
     * @param difficult Whether this question should be difficult (decreases range of incorrect answers)
     * @return Generated InsteadQuestion
     */
    public InsteadQuestion generateInsteadQuestion(boolean difficult) {
        Activity activity = getNextActivity();

        long center = activity.getConsumption();
        long multiplier = difficult ? 10 : 100;

        // We look for the closest match
        List<Activity> correctAnswers = activitiesWithinRange(center, 1);
        // Much larger range
        List<Activity> incorrectAnswers = activitiesWithinRange(center, multiplier);
        // Make sure correct answers are not included in the incorrect answers
        incorrectAnswers.removeAll(correctAnswers);

        // Make sure that correct answer does not have the original activity
        correctAnswers.remove(activity);

        // Add one of the correct answers
        List<Activity> answerChoices = new ArrayList<>(getRandomActivities(correctAnswers, 1));
        String correctAnswer = answerChoices.get(0).getTitle();

        if (incorrectAnswers.size() < 2) {
            // We couldn't successfully generate incorrect answers.
            // Just pick whatever is not the true answer
            incorrectAnswers = activitiesWithinRange(center, multiplier);
            incorrectAnswers.remove(activity);
            incorrectAnswers.removeAll(answerChoices);
        }

        // Add two incorrect answers
        answerChoices.addAll(getRandomActivities(incorrectAnswers, 2));

        return new InsteadQuestion(activity, correctAnswer, answerChoices);
    }

    /**
     * Generate a random question.
     *
     * @param difficult Whether the question should be difficult.
     * @param number    Number of the question. (Used, modulo 4, to determine question type)
     * @return Generated question.
     */
    public AbstractQuestion generateQuestion(boolean difficult, int number) {
        return switch (number % 4) {
            case 0 -> generateInsteadQuestion(difficult);
            case 1 -> generateConsumptionQuestion(difficult);
            case 2 -> generateMoreExpensiveQuestion(difficult);
            case 3 -> generateGuessQuestion(difficult);
            default -> null;
        };
    }

    /**
     * Generate 20 questions for a game.
     *
     * @return List of 20 generated questions.
     */
    public List<AbstractQuestion> generate20Questions() {
        initialize();

        List<AbstractQuestion> easy = new ArrayList<>();
        List<AbstractQuestion> hard = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if (i < 12) {
                easy.add(generateQuestion(false, i));
            } else {
                hard.add(generateQuestion(true, i));
            }
        }

        Collections.shuffle(easy, random);
        Collections.shuffle(hard, random);

        List<AbstractQuestion> result = new ArrayList<>();
        result.addAll(easy);
        result.addAll(hard);

        return result;
    }
}
