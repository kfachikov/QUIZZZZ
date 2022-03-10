package commons;

public class ThirdQuestionType extends AbstractQuestion {

    private ThirdQuestionType() {
        super();
    }

    /**
     * Constructor for the third question type.
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public ThirdQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    /**
     * Creates the third question format for the game.
     * @return the third question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "What can you do instead of " + this.baseTitle + " and consuming the same amount of energy?";
        return question;
    }
}
