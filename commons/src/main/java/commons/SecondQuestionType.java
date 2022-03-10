package commons;

public class SecondQuestionType extends AbstractQuestion {

    private SecondQuestionType() {
        super();
    }

    /**
     * Constructor for the second question type.
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public SecondQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    /**
     * Creates the second question format for the game.
     * @return the second question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "Which of these activities consumes " + this.consumptionWh + " watts per hour?";
        return question;
    }
}
