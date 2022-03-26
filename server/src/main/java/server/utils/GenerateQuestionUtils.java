package server.utils;

import commons.misc.Activity;
import commons.question.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class for the generation of question utilities.
 */
@Component
@ComponentScan(basePackageClasses = Random.class)
public class GenerateQuestionUtils {

    private final Random random;

    /**
     * The constructor  for question utilities instance.
     *
     * @param random the random instance.
     */
    public GenerateQuestionUtils(Random random) {
        this.random = random;
    }

    /**
     * The method generates the 20 questions for the game.
     * It generates 5 question for each of the 4 types and then shuffles them.
     *
     * @param repo the activity repository.
     *
     * @return new list of abstract questions.
     */
    public List<AbstractQuestion> generate20Questions(ActivityRepository repo) {
        List<AbstractQuestion> result = new ArrayList<>();
        List<Activity> activities = repo.findAll();
        int questionNumber = 1;

        Collections.shuffle(activities, random);

        while (questionNumber <= 5) {
            if (questionNumber > activities.size()) {
                break;
            }
            Activity activity = activities.get(questionNumber - 1);
            InsteadQuestion insteadQuestion = new InsteadQuestion(activity);
            insteadQuestion.setAnswerChoices(activities);
            result.add(insteadQuestion);
            questionNumber++;
        }

        while (questionNumber <= 10) {
            if (questionNumber > activities.size()) {
                break;
            }
            Activity activity = activities.get(questionNumber - 1);
            ConsumptionQuestion consumptionQuestion = new ConsumptionQuestion(activity);
            consumptionQuestion.setAnswerChoices();
            result.add(consumptionQuestion);
            questionNumber++;
        }

        while (questionNumber <= 15) {
            if (questionNumber > activities.size()) {
                break;
            }
            MoreExpensiveQuestion moreExpensiveQuestion = new MoreExpensiveQuestion();
            moreExpensiveQuestion.setAnswerChoices(activities);
            result.add(moreExpensiveQuestion);
            questionNumber++;
        }

        while (questionNumber <= 20) {
            if (questionNumber > activities.size()) {
                break;
            }
            Activity activity = activities.get(questionNumber - 1);
            GuessQuestion guessQuestionType = new GuessQuestion(activity);
            result.add(guessQuestionType);
            questionNumber++;
        }

        Collections.shuffle(result, random);
        return result;
    }
}
