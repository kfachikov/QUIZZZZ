package commons.single;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.question.AbstractQuestion;

import java.util.List;
import java.util.Objects;

@JsonTypeName(value = "single")
public class SinglePlayerState extends GameState {

    public static final String QUESTION_STATE = "QUESTION";
    public static final String GAME_OVER_STATE = "GAME_OVER";
    public static final String TRANSITION_STATE = "TRANSITION";
    private SinglePlayer player;
    private List<GameResponse> finalAnswers;

    public SinglePlayerState() {

    }

    /**
     * Constructor for the state of the solo game.
     *
     * @param id               the id of the game.
     * @param nextPhase        the time of the next phase.
     * @param roundNumber      the round number of the game.
     * @param questionList     the list of question for a game.
     * @param finalAnswers     the list of answers submitted as final by players during a game
     * @param submittedAnswers the answers submitted by players during game in a single round.
     * @param state            the status of the game.
     * @param player           the player that is currently in the game.
     */
    public SinglePlayerState(long id, long nextPhase, int roundNumber, List<AbstractQuestion> questionList,
                             List<GameResponse> finalAnswers,
                             List<GameResponse> submittedAnswers,
                             String state, SinglePlayer player) {
        super(id, nextPhase, roundNumber, questionList, submittedAnswers, state);
        this.player = player;
        this.finalAnswers = finalAnswers;
    }

    /**
     * Getter for the player.
     *
     * @return a SinglePlayer representing the player that is currently in the game.
     */
    public SinglePlayer getPlayer() {
        return player;
    }

    /**
     * Setter for the player.
     *
     * @param player the player that is currently in the game.
     */
    public void setPlayer(SinglePlayer player) {
        this.player = player;
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
        if (!super.equals(o)) {
            return false;
        }
        SinglePlayerState that = (SinglePlayerState) o;
        return Objects.equals(player, that.player);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }

}
