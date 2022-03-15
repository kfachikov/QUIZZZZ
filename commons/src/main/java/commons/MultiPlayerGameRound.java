package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MultiPlayerGameRound {
    @Id
    @Column(name = "id", nullable = false)
    public Long roundId;

    public String finalAnswer;

    public MultiPlayerGameRound(Long roundId, String finalAnswer) {
        this.roundId = roundId;
        this.finalAnswer = finalAnswer;
    }

    public String getFinalAnswer() {
        return finalAnswer;
    }

    public void setFinalAnswer(String finalAnswer) {
        this.finalAnswer = finalAnswer;
    }

    public Long getId() {
        return roundId;
    }

    public void setId(Long id) {
        this.roundId = id;
    }
}
