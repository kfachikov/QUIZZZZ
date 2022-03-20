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

    public InsteadQuestion() {
        super();
    }

    /**
     * Constructor for the third question type.
     * @param activity the activity that is being compared
     */
    public InsteadQuestion(Activity activity) {
        this.activity = activity;
        this.answerChoices = new ArrayList<>();
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     */
    public void setAnswerChoices(List<Activity> activities) {
        this.answerChoices = new ArrayList<>();
        List<Activity> correct = activities.stream()
                .filter(x -> x.getConsumption() <= activity.getConsumption())
                .collect(Collectors.toList());
        Collections.shuffle(correct);
        List<Activity> incorrect = activities.stream()
                .filter(x -> x.getConsumption() >= activity.getConsumption())
                .collect(Collectors.toList());
        Collections.shuffle(incorrect);
        answerChoices.add(correct.get(0));
        answerChoices.add(incorrect.get(0));
        answerChoices.add(incorrect.get(0));
    }

    /**
     * Creates the third question format for the game.
     * @return the third question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "What can you do instead of " + activity.getTitle() + " and consuming the same amount of energy?";
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
}
