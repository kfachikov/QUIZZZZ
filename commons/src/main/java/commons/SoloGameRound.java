package commons;

import javax.persistence.*;

@Entity
public class SoloGameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Long roundId;

    public String finalAnswer;

    public SoloGameRound(Long roundId, String finalAnswer) {
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
