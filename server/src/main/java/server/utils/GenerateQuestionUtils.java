package server.utils;

import commons.misc.Activity;
import commons.question.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@ComponentScan(basePackageClasses = Random.class)
public class GenerateQuestionUtils {

    private final ActivityRepository repo;
    private final Random random;


    public GenerateQuestionUtils(ActivityRepository repo, Random random) {
        this.repo = repo;
        this.random = random;
    }

    public Predicate<Activity> activityPredicate(long lower, long upper) {
        return activity -> activity.getConsumption() >= lower &&
                activity.getConsumption() <= upper;
    }

    public ConsumptionQuestion generateConsumptionQuestion(boolean difficult) {
        List<Activity> activities = repo.findAll();
        // Generate a random activity
        Activity activity = activities.get(random.nextInt(activities.size()));

        // Make range of possible values smaller if question is "difficult"
        // Thus, players require more "precision" to answer correctly
        long multiplier = difficult ? 2 : 10;

        long lowerBound = activity.getConsumption() / multiplier;
        long upperBound = activity.getConsumption() * multiplier;

        /*
        Get a list of candidate answers by:
        1. Taking all activities
        2. Filtering only those within the range for good answer choices
        3. Remove the true answer from the list
         */
        List<Long> candidateAnswers =
                activities.stream()
                        .filter(activityPredicate(lowerBound, upperBound))
                        .mapToLong(Activity::getConsumption)
                        .distinct()
                        .filter(answer -> answer == activity.getConsumption())
                        .boxed()
                        .collect(Collectors.toList());

        if (candidateAnswers.size() < 2) {
            // We have no viable answers
            // Thus, we just generate some
            long range = upperBound - lowerBound + 1;
            long answer1 = lowerBound + random.nextLong() % range;
            long answer2 = lowerBound + random.nextLong() % range;

            candidateAnswers.add(answer1);
            candidateAnswers.add(answer2);
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

    public List<AbstractQuestion> generate20Questions() {

        List<AbstractQuestion> result = new ArrayList<>();
        List<Activity> activities = repo.findAll();
        int questionNumber = 1;

        Collections.shuffle(activities, random);

        while (questionNumber <= 5 && questionNumber <= activities.size()) {
            Activity activity = activities.get(questionNumber - 1);
            InsteadQuestion insteadQuestion = new InsteadQuestion(activity);
            insteadQuestion.setAnswerChoices(activities);
            result.add(insteadQuestion);
            questionNumber++;
        }

        while (questionNumber <= 10 && questionNumber <= activities.size()) {
            Activity activity = activities.get(questionNumber - 1);
            ConsumptionQuestion consumptionQuestion = new ConsumptionQuestion(activity);
            consumptionQuestion.setAnswerChoices();
            result.add(consumptionQuestion);
            questionNumber++;
        }

        while (questionNumber <= 15 && questionNumber <= activities.size()) {
            MoreExpensiveQuestion moreExpensiveQuestion = new MoreExpensiveQuestion();
            moreExpensiveQuestion.setAnswerChoices(activities);
            result.add(moreExpensiveQuestion);
            questionNumber++;
        }

        while (questionNumber <= 20 && questionNumber <= activities.size()) {
            Activity activity = activities.get(questionNumber - 1);
            GuessQuestion guessQuestionType = new GuessQuestion(activity);
            result.add(guessQuestionType);
            questionNumber++;
        }

        Collections.shuffle(result, random);
        return result;
    }
}
