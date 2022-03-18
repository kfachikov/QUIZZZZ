package commons.question;

import commons.Activity;
import commons.question.AbstractQuestion;

import java.util.Collections;
import java.util.List;

public class MoreExpensiveQuestion extends AbstractQuestion {
    
    /**
     * List of activities that the players will choose from.
     */
    private List<Activity> answerChoices;

    /**
     * This is the correct choice activity.
     */
    private Activity correctAnswer;

    /**
     * Constructor for the fourth question type.
     */
    public MoreExpensiveQuestion() {
        super();
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

    public List<Activity> getAnswerChoices() {
        return answerChoices;
    }

    public Activity getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Activity correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
