package server.utils;

import commons.*;
import server.database.ActivityRepository;
import commons.Activity;

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
            Activity activity = activities.get(questionNumber);
            AlternativeConsumptionQuestionType alternativeConsumptionQuestionType = new AlternativeConsumptionQuestionType(activity.getTitle(), activity.getImage(), activity.getConsumption());
            //alternativeConsumptionQuestionType.setAnswerChoices(activities);
            result.add(alternativeConsumptionQuestionType);
            questionNumber++;
        }

        while (questionNumber <= 10) {
            Activity activity = activities.get(questionNumber);
            ActivityConsumptionQuestionType activityConsumptionQuestionType = new ActivityConsumptionQuestionType(activity.getTitle(), activity.getImage(), activity.getConsumption());
            activityConsumptionQuestionType.setAnswerChoices();
            result.add(activityConsumptionQuestionType);
            questionNumber++;
        }

        while (questionNumber <= 15) {
            Activity activity = activities.get(questionNumber);
            HigherConsumptionQuestionType higherConsumptionQuestionType = new HigherConsumptionQuestionType(activity.getTitle(), activity.getImage(), activity.getConsumption());
            //higherConsumptionQuestionType.setAnswerChoices(activities);
            result.add(higherConsumptionQuestionType);
            questionNumber++;
        }

        while (questionNumber <= 20) {
            Activity activity = activities.get(questionNumber);
            GuessQuestionType guessQuestionType = new GuessQuestionType(activity.getTitle(), activity.getImage(), activity.getConsumption());
            result.add(guessQuestionType);
            questionNumber++;
        }

        Collections.shuffle(result, random);
        return result;
    }
}
