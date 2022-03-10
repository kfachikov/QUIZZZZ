package commons;

public class FourthQuestionType extends AbstractQuestion {

    private FourthQuestionType() {
        super();
    }

    /**
     * Constructor for the fourth question type.
     * @param baseTitle title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public FourthQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

    /**
     * Creates the fourth question format for the game.
     * @return the fourth question type in a human-readable way.
     */
    public String toString() {
        String question;
        question = "What activity has a higher energy consumption?";
        return question;
    }
}
