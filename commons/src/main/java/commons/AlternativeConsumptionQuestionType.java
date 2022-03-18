package commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlternativeConsumptionQuestionType extends AbstractQuestion {

    public List<Activity> answerChoices;

    public AlternativeConsumptionQuestionType() {
        super();
    }

    /**
     * Constructor for the third question type.
     *
     * @param baseTitle     title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public AlternativeConsumptionQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
        this.answerChoices = new ArrayList<>();
    }

    /**
     * Sets the possible answers for a question in a random way, having between them the correct answer.
     *
     * @param activities List of all activities
     */
    public void setAnswerChoices(List<Activity> activities) {
        List<Activity> correct = activities.stream()
                .filter(x -> x.getConsumption() <= consumptionWh)
                .collect(Collectors.toList());
        Collections.shuffle(correct);
        List<Activity> incorrect = activities.stream()
                .filter(x -> x.getConsumption() > consumptionWh)
                .collect(Collectors.toList());
        Collections.shuffle(incorrect);
        answerChoices.add(correct.get(0));
        answerChoices.add(incorrect.get(0));
        answerChoices.add(incorrect.get(1));
    }

    /**
     * Creates the third question format for the game.
     *
     * @return the third question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "What can you do instead of " + this.baseTitle + " and consuming the same amount of energy?";
        return question;
    }
}
