package commons;

public class GuessQuestionType extends AbstractQuestion {

    public GuessQuestionType() {
        super();
    }

    /**
     * Constructor for the second question type.
     *
     * @param baseTitle     title for the activity.
     * @param imageFilename file name.
     * @param consumptionWh consumption in wh.
     */
    public GuessQuestionType(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
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
}
