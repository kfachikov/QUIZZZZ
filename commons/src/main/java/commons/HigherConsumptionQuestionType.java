package commons;

import java.util.Collections;
import java.util.List;

public class HigherConsumptionQuestionType extends AbstractQuestion {

    public List<Activity> answerChoices;
    public Activity correctAnswer;

    public HigherConsumptionQuestionType() {
        super();
    }

    /**
     * Constructor for the fourth question type.
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public HigherConsumptionQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     */
    public void setAnswerChoices(List<Activity> activities) {
        Collections.shuffle(activities);
        answerChoices.add(activities.get(0));
        answerChoices.add(activities.get(1));
        answerChoices.add(activities.get(2));
        correctAnswer = (answerChoices.get(1).getConsumption() > answerChoices.get(0).getConsumption()) ?
                answerChoices.get(1) : answerChoices.get(0);
        correctAnswer = (answerChoices.get(2).getConsumption() > correctAnswer.getConsumption()) ?
                answerChoices.get(2) : correctAnswer;
    }

    /**
     * Creates the fourth question format for the game.
     * @return the fourth question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "What activity has a higher energy consumption?";
        return question;
    }
}
