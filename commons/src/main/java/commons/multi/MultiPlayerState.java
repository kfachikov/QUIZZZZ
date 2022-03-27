package commons.multi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.question.AbstractQuestion;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonTypeName(value = "multi")
public class MultiPlayerState extends GameState {

    public static final String NOT_STARTED_STATE = "NOT_STARTED";
    public static final String STARTING_STATE = "STARTING";
    public static final String QUESTION_STATE = "QUESTION";
    public static final String GAME_OVER_STATE = "GAME_OVER";
    public static final String TRANSITION_STATE = "TRANSITION";
    public static final String LEADERBOARD_STATE = "LEADERBOARD";

    private List<MultiPlayer> players;
    private Reaction reaction;
    private List<GameResponse> finalAnswers;

    /**
     * Default constructor to be used for the JSON parsing.
     */
    public MultiPlayerState() {

    }

    /**
     * Constructor for the state of the multiplayer game.
     *
     * @param id               the id of the game.
     * @param nextPhase        the time of the next phase.
     * @param roundNumber      the round number of the game.
     * @param questionList     the list of question for a game.
     * @param submittedAnswers the answers submitted by players during game in a single round.
     * @param finalAnswers     the list of answers submitted as final by players during a game.
     * @param state            the status of the game.
     * @param players          the list of players currently in the game.
     * @param reaction         the reactions used in a game.
     */
    public MultiPlayerState(long id, long nextPhase, int roundNumber,
                            List<AbstractQuestion> questionList,
                            List<GameResponse> submittedAnswers,
                            List<GameResponse> finalAnswers,
                            String state,
                            List<MultiPlayer> players, Reaction reaction) {
        super(id, nextPhase, roundNumber, questionList, submittedAnswers, state);
        this.players = players;
        this.reaction = reaction;
        this.finalAnswers = finalAnswers;
        this.players = players;
    }

    /**
     * Getter for the players currently in the game.
     *
     * @return a list of MultiPlayer representing the players currently in the game.
     */
    public List<MultiPlayer> getPlayers() {
        return players;
    }

    /**
     * Getter for the reactions.
     *
     * @return a Reaction representing the reactions used in a game.
     */
    public Reaction getReaction() {
        return reaction;
    }

    /**
     * Setter for the players currently in the game.
     *
     * @param players the list of players currently in the game.
     */
    public void setPlayers(List<MultiPlayer> players) {
        this.players = players;
    }

    /**
     * Getter for the list of answers submitted as final.
     *
     * @return a list of Responses representing the answers given by the players.
     */
    public List<GameResponse> getFinalAnswers() {
        return finalAnswers;
    }

    /**
     * Comparing answer function making use of the abstract functionality declared
     * in the parent abstract class of the different question types.
     *
     * @return Boolean value corresponding to the correctness of the answer.
     */
    public boolean compareAnswer() {
        if (finalAnswers.get(getRoundNumber()) == null) {
            return false;
        }
        String chosenAnswer = finalAnswers.get(getRoundNumber()).getAnswerChoice();
        String rightAnswer = getQuestionList().get(getRoundNumber()).getCorrectAnswer();
        return chosenAnswer.equals(rightAnswer);
    }

    /**
     * Setter for the reactions.
     *
     * @param reaction the reactions used in a game.
     */
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    /**
     * Compares two entities.
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
        if (!super.equals(o)) {
            return false;
        }
        MultiPlayerState that = (MultiPlayerState) o;
        return Objects.equals(players, that.players) && Objects.equals(reaction, that.reaction);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), players, reaction);
    }

    /**
     * Multiline string representation of the multiplayer game state.
     * <p>
     * Automatically generated using ToStringBuilder.
     *
     * @return Multiline string of the multiplayer game state.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
