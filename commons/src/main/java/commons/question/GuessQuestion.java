package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

/**
 * The class for the guess question.
 */
@JsonTypeName(value = "guess")
public class GuessQuestion extends AbstractQuestion {
    /**
     * The activity the question is about.
     */
    private Activity activity;

    /**
     * Constructor for the guess question.
     */
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

    /**
     * Getter for the activity.
     *
     * @return the actual activity.
     *
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Setter for the activtiy.
     *
     * @param activity the actual activity.
     *
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Getter for the correct answer.
     *
     * @return String version of the correct answer.
     *
     */
    public String getCorrectAnswer() {
        return activity.getConsumption().toString();
    }
}
