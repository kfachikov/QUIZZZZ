package commons.misc;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commons.multi.MultiPlayerState;
import commons.question.AbstractQuestion;
import commons.single.SinglePlayerState;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class - parent of two different classes corresponding to single-player game state
 * and multiplayer game state respectively.
 *
 * To be used for shared functionality and an establishment of hierarchical structure.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SinglePlayerState.class, name = "single"),
    @JsonSubTypes.Type(value = MultiPlayerState.class, name = "multi")
})
public abstract class GameState {

    private long id;
    private long nextPhase;
    private int roundNumber;
    private List<AbstractQuestion> questionList;
    private List<GameResponse> submittedAnswers;
    /**
     * The state attribute is a String from : transition, intermittent leaderboard, question, game over.
     */
    private String state;

    /**
     * Default constructor for GameState, for object mapper.
     */
    public GameState() {
    }

    /**
     * Constructor for the state of the game.
     *
     * @param id               the id of the game.
     * @param nextPhase        the time of the next phase.
     * @param roundNumber      the round number of the game.
     * @param questionList     the list of question for a game.
     * @param submittedAnswers the answers submitted by players during game in a single round.
     * @param state            the status of the game.
     */
    public GameState(long id, long nextPhase, int roundNumber,
                     List<AbstractQuestion> questionList,
                     List<GameResponse> submittedAnswers,
                     String state) {
        this.id = id;
        this.nextPhase = nextPhase;
        this.roundNumber = roundNumber;
        this.questionList = questionList;
        this.submittedAnswers = submittedAnswers;
        this.state = state;
    }

    /**
     * Getter for the id of the game.
     *
     * @return id of the game.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the id of the game.
     *
     * @param id the id of the game.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the time of the next phase.
     *
     * @return a long representing the time of the next phase.
     */
    public long getNextPhase() {
        return nextPhase;
    }

    /**
     * Getter for the round number.
     *
     * @return an int representing the round number of the game.
     */
    public int getRoundNumber() {
        return roundNumber;
    }


    /**
     * Getter for the answer submitted.
     *
     * @return a list of Responses representing the answers given by the players in a single round.
     */
    public List<GameResponse> getSubmittedAnswers() {
        return submittedAnswers;
    }

    /**
     * Getter for the questions used during a game.
     *
     * @return a list of AbstractQuestion representing the questions needed for the game.
     */
    public List<AbstractQuestion> getQuestionList() {
        return questionList;
    }


    /**
     * Getter for the status of the game.
     *
     * @return a String representing the status of the game.
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for the time of the next phase.
     *
     * @param nextPhase the time of the next phase.
     */
    public void setNextPhase(long nextPhase) {
        this.nextPhase = nextPhase;
    }

    /**
     * Setter for the round number.
     *
     * @param roundNumber the round number of the game.
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * Setter for the questions used during a game.
     *
     * @param questionList the list of question for a game.
     */
    public void setQuestionList(List<AbstractQuestion> questionList) {
        this.questionList = questionList;
    }

    /**
     * Setter for the answer submitted.
     *
     * @param submittedAnswers the answers submitted by players during game.
     */
    public void setSubmittedAnswers(List<GameResponse> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    /**
     * Setter for the status of the game.
     *
     * @param state the status of the game.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Compares the two entities.
     *
     * @param o the instance that is compared to.
     * @return true if the two entities are equal, otherwise it will be returned false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Long.compare(gameState.nextPhase, nextPhase) == 0 && roundNumber == gameState.roundNumber &&
                Objects.equals(questionList, gameState.questionList) &&
                Objects.equals(submittedAnswers, gameState.submittedAnswers) && Objects.equals(state, gameState.state);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nextPhase, roundNumber, questionList, submittedAnswers, state);
    }
}
