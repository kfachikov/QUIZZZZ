package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

import java.util.*;

/**
 * Consumption question type. Extends the AbstractQuestion parent class.
 * Add specific fields and some functionality.
 * <p>
 * The "type" value is set to "consumption" so that the instances are created accordingly
 * on the client-side from the JSON response.
 */
@JsonTypeName(value = "consumption")
public class ConsumptionQuestion extends AbstractQuestion {
    private Activity activity;
    private List<Long> answerChoices;

    /**
     * Default constructor. Used for the JSON parsing of the different question instances.
     */
    public ConsumptionQuestion() {
        super();
    }

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param activity the activity the question is about.
     */
    public ConsumptionQuestion(Activity activity) {
        this.activity = activity;
        answerChoices = new ArrayList<>();
    }

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param activity      the activity the question is about.
     * @param answerChoices answer choices for this question.,
     */
    public ConsumptionQuestion(Activity activity, List<Long> answerChoices) {
        this.activity = activity;
        this.answerChoices = answerChoices;
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     */
    public void setAnswerChoices() {
        Random rnd = new Random();
        long correct = activity.getConsumption();
        long lowerBound = correct / 2;
        long upperBound = correct * 3 / 2;
        long answer1 = lowerBound + rnd.nextInt((int) (upperBound - lowerBound + 1));
        long answer2 = lowerBound + rnd.nextInt((int) (upperBound - lowerBound + 1));
        answerChoices.add(answer1);
        answerChoices.add(answer2);
        answerChoices.add(correct);
        Collections.shuffle(answerChoices);
    }

    /**
     * Creates the first question format for the game.
     *
     * @return the first question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "HOW MUCH ENERGY DOES IT TAKE?";
        return question;
    }

    /**
     * compares the equality of the passed object, with the ConsumptionQuestion instance its called over.
     *
     * @param o object that is subject to comparison
     * @return true/false corresponding to equal/unequal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ConsumptionQuestion that = (ConsumptionQuestion) o;
        return Objects.equals(answerChoices, that.answerChoices);
    }

    /**
     * @return hashcode of the ConsumptionQuestion instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), answerChoices);
    }

    /**
     * @return activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Getter for the list consisting of the possible answers.
     *
     * @return The answerChoices field of that particular question instance.
     */
    public List<Long> getAnswerChoices() {
        return answerChoices;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAnswerChoices(List<Long> answerChoices) {
        this.answerChoices = answerChoices;
    }

    /**
     * Getter for the correct answer.
     *
     * @return long variable
     */
    public String getCorrectAnswer() {
        return activity.getConsumption().toString();
    }
}
