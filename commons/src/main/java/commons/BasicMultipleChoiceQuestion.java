package commons;

import javax.persistence.Entity;

@Entity
public class BasicMultipleChoiceQuestion extends AbstractQuestion {

    @SuppressWarnings("unused")
    private BasicMultipleChoiceQuestion() {
        super();
    }

    public BasicMultipleChoiceQuestion(String baseTitle, String imageFilename, long consumptionWh) {
        super(baseTitle, imageFilename, consumptionWh);
    }

}
