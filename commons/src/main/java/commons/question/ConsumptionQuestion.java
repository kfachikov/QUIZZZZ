package commons.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.Activity;

import java.util.*;

@JsonTypeName(value = "consumption")
public class ConsumptionQuestion extends AbstractQuestion {
    private Activity activity;
    private List<String> answerChoices;

    public ConsumptionQuestion() {
        super();
    }

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param activity the activity the question is about
     */
    public ConsumptionQuestion(Activity activity) {
        this.activity = activity;
        answerChoices = new ArrayList<>();
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
        answerChoices.add(answer1 + "Wh");
        answerChoices.add(answer2 + "Wh");
        answerChoices.add(correct + "Wh");
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
