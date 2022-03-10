package commons;

public class FirstQuestionType extends AbstractQuestion {

    private FirstQuestionType() {
        super();
    }

    /**
     * Constructor for the first question type.
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public FirstQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    /**
     * Creates the first question format for the game.
     * @return the first question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "How much does " + this.baseTitle + " consume?";
        return question;
    }
}
