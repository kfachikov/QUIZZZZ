package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

import java.util.*;

/**
 * Consumption question type. Extends the AbstractQuestion parent class.
 * Add specific fields and some functionality.
 *
 * The "type" value is set to "consumption" so that the instances are created accordingly
 * on the client-side from the JSON response.
 */
@JsonTypeName(value = "consumption")
public class ConsumptionQuestion extends AbstractQuestion {
    private Activity activity;
    private List<String> answerChoices;

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
     * Getter for the correct answer.
     * @return long variable
     */
    public String getCorrectAnswer() {
        return activity.getConsumption().toString();
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     */
    public void setAnswerChoices() {
        Random rnd = new Random();
        long correctAnswer = activity.getConsumption();
        long lowerBound = correctAnswer / 2;
        long upperBound = correctAnswer * 3 / 2;
        long answer1 = lowerBound + rnd.nextInt((int) (upperBound - lowerBound + 1));
        long answer2 = lowerBound + rnd.nextInt((int) (upperBound - lowerBound + 1));
        answerChoices.add(answer1 + "Wh");
        answerChoices.add(answer2 + "Wh");
        answerChoices.add(correctAnswer + "Wh");
        Collections.shuffle(answerChoices);
    }

    /**
     * Creates the first question format for the game.
     *
     * @return the first question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "How much does " + activity.getTitle() + " consume?";
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
    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
    }
}
