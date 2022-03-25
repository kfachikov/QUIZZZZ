package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

@JsonTypeName(value = "guess")
public class GuessQuestion extends AbstractQuestion {
    /**
     * The activity the question is about.
     */
    private Activity activity;

    public GuessQuestion() {
        super();
    }

    /**
     * Constructor for the second question type.
     *
     * @param activity the activity the question is about
     */
    public GuessQuestion(Activity activity) {
        this.activity = activity;
    }

    /**
     * Creates the second question format for the game.
     *
     * @return the second question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "How much energy does it take for the activity below?";
        return question;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getCorrectAnswer() {
        return activity.getConsumption().toString();
    }
}
