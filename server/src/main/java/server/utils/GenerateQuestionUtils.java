package server.utils;

import commons.question.*;
import server.database.ActivityRepository;
import commons.misc.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GenerateQuestionUtils {

    public List<Long> distinctList(Random random, long count, long upper) {
        List<Long> result = new ArrayList<>();
        if (upper < count) {
            throw new IllegalArgumentException();
        }
        for (long i = 0; i < upper; i++) {
            result.add(i);
        }
        Collections.shuffle(result, random);
        return result.stream().limit(count).collect(Collectors.toList());
    }

    public List<AbstractQuestion> generate20Questions(Random random, ActivityRepository repo) {

        List<AbstractQuestion> result = new ArrayList<>();
        List<Activity> activities = repo.findAll();
        int questionNumber = 1;

        Collections.shuffle(activities, random);

        while (questionNumber <= 5) {
            if (questionNumber > activities.size()) break;
            Activity activity = activities.get(questionNumber - 1);
            InsteadQuestion insteadQuestion = new InsteadQuestion(activity);
            insteadQuestion.setAnswerChoices(activities);
            result.add(insteadQuestion);
            questionNumber++;
        }

        while (questionNumber <= 10) {
            if (questionNumber > activities.size()) break;
            Activity activity = activities.get(questionNumber - 1);
            ConsumptionQuestion consumptionQuestion = new ConsumptionQuestion(activity);
            consumptionQuestion.setAnswerChoices();
            result.add(consumptionQuestion);
            questionNumber++;
        }

        while (questionNumber <= 15) {
            if (questionNumber > activities.size()) break;
            MoreExpensiveQuestion moreExpensiveQuestion = new MoreExpensiveQuestion();
            moreExpensiveQuestion.setAnswerChoices(activities);
            result.add(moreExpensiveQuestion);
            questionNumber++;
        }

        while (questionNumber <= 20) {
            if (questionNumber > activities.size()) break;
            Activity activity = activities.get(questionNumber - 1);
            GuessQuestion guessQuestionType = new GuessQuestion(activity);
            result.add(guessQuestionType);
            questionNumber++;
        }

        Collections.shuffle(result, random);
        return result;
    }
}
