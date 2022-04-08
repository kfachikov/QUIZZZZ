package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonTypeName(value = "moreExpensive")
public class MoreExpensiveQuestion extends AbstractQuestion {

    /**
     * List of activities that the players will choose from.
     */
    private List<Activity> answerChoices;

    private String correctAnswer;

    /**
     * Constructor for the fourth question type.
     */
    public MoreExpensiveQuestion() {
        super();
        answerChoices = new ArrayList<>();
    }

    /**
     * Constructor for more expensive question type.
     *
     * @param correctAnswer List of 3 activities, between which 1 is more expensive.
     * @param answerChoices Title of the correct answer.
     */
    public MoreExpensiveQuestion(String correctAnswer, List<Activity> answerChoices) {
        this.correctAnswer = correctAnswer;
        this.answerChoices = answerChoices;
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     *
     * @param activities List of all activities
     */
    public void setAnswerChoices(List<Activity> activities) {
        Collections.shuffle(activities);
        answerChoices.add(activities.get(0));
        answerChoices.add(activities.get(1));
        answerChoices.add(activities.get(2));
        Activity correct = (answerChoices.get(1).getConsumption() > answerChoices.get(0).getConsumption()) ?
                answerChoices.get(1) : answerChoices.get(0);
        correct = (answerChoices.get(2).getConsumption() > correct.getConsumption()) ?
                answerChoices.get(2) : correct;
        correctAnswer = correct.getTitle();
    }

    /**
     * Creates the fourth question format for the game.
     *
     * @return the fourth question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "WHAT ACTIVITY HAS A HIGHER ENERGY CONSUMPTION?";
        return question;
    }

    public List<Activity> getAnswerChoices() {
        return answerChoices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
