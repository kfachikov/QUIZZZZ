package commons;

import java.util.*;

public class ActivityConsumptionQuestionType extends AbstractQuestion {

    public List<String> answerChoices;

    private ActivityConsumptionQuestionType() {
        super();
    }

    /**
     * Constructor for the activity's energy consumption question type.
     *
     * @param baseTitle     title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public ActivityConsumptionQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
        answerChoices = new ArrayList<>();
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     */
    public void setAnswerChoices() {
        Random rnd = new Random();
        long correct = this.consumptionWh;
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
        question = "How much does " + this.baseTitle + " consume?";
        return question;
    }

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
        ActivityConsumptionQuestionType that = (ActivityConsumptionQuestionType) o;
        return Objects.equals(answerChoices, that.answerChoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), answerChoices);
    }
}
