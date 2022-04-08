package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonTypeName(value = "instead")
public class InsteadQuestion extends AbstractQuestion {

    private Activity activity;
    private List<Activity> answerChoices;
    private String correctAnswer;

    public InsteadQuestion() {
        super();
    }

    /**
     * Constructor for the third question type.
     *
     * @param activity the activity that is being compared
     */
    public InsteadQuestion(Activity activity) {
        this.activity = activity;
        this.answerChoices = new ArrayList<>();
    }

    /**
     * Constructor for instead question.
     *
     * @param activity      the activity that is being compared
     * @param correctAnswer Title of the correct answer.
     * @param answerChoices List of 3 activities, 1 of which is close to the true answer.
     */
    public InsteadQuestion(Activity activity, String correctAnswer, List<Activity> answerChoices) {
        this.activity = activity;
        this.correctAnswer = correctAnswer;
        this.answerChoices = answerChoices;
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     *
     * @param activities List of all activities
     */
    public void setAnswerChoices(List<Activity> activities) {
        this.answerChoices = new ArrayList<>();
        List<Activity> correct = activities.stream()
                .filter(x -> x.getConsumption() <= activity.getConsumption())
                .collect(Collectors.toList());
        Collections.shuffle(correct);
        List<Activity> incorrect = activities.stream()
                .filter(x -> x.getConsumption() > activity.getConsumption())
                .collect(Collectors.toList());
        Collections.shuffle(incorrect);
        if (correct.isEmpty()) {
            answerChoices.add(incorrect.get(0));
            answerChoices.add(incorrect.get(1));
            answerChoices.add(incorrect.get(2));
        } else {
            answerChoices.add(correct.get(0));
            correctAnswer = answerChoices.get(0).getTitle();
            if (incorrect.isEmpty()) {
                answerChoices.add(correct.get(1));
                answerChoices.add(correct.get(2));
            } else if (incorrect.size() == 1) {
                answerChoices.add(correct.get(1));
                answerChoices.add(incorrect.get(0));
            } else {
                answerChoices.add(incorrect.get(0));
                answerChoices.add(incorrect.get(1));
            }
        }
        Collections.shuffle(answerChoices);
    }

    /**
     * Creates the third question format for the game.
     *
     * @return the third question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "INSTEAD OF ..., YOU COULD BE ...";
        return question;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Activity> getAnswerChoices() {
        return answerChoices;
    }

    /**
     * Returns the title of the activity to be checked.
     * Should be compared to the submitted answer's consumption.
     *
     * @return String of the consumption of the "right" activity.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
