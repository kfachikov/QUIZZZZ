package commons.multi;

import commons.Activity;
import commons.GameState;
import commons.question.AbstractQuestion;

import java.util.List;
import java.util.Objects;

public class MultiPlayerState extends GameState {

    private List<MultiPlayer> players;
    private Reaction reaction;

    /**
     * Constructor for the state of the multiplayer game.
     *  @param timeLeft the time left during a round.
     * @param roundNumber the round number of the game.
     * @param questionList the list of question for a game.
     * @param submittedAnswers the answers submitted by players during game.
     * @param activityList the list of activities used for the game.
     * @param state the status of the game.
     * @param players the list of players currently in the game.
     * @param reaction the reactions used in a game.
     */
    public MultiPlayerState(double timeLeft, int roundNumber, List<AbstractQuestion> questionList, List<String> submittedAnswers, List<Activity> activityList, String state, List<MultiPlayer> players, Reaction reaction) {
        super(timeLeft, roundNumber, questionList, submittedAnswers, activityList, state);
        this.players = players;
        this.reaction = reaction;
    }

    /**
     * Getter for the players currently in the game.
     * @return a list of MultiPlayer representing the players currently in the game.
     */
    public List<MultiPlayer> getPlayers() {
        return players;
    }

    /**
     * Getter for the reactions.
     * @return a Reaction representing the reactions used in a game.
     */
    public Reaction getReaction() {
        return reaction;
    }

    /**
     * Setter for the players currently in the game.
     * @param players the list of players currently in the game.
     */
    public void setPlayers(List<MultiPlayer> players) {
        this.players = players;
    }

    /**
     * Setter for the reactions.
     * @param reaction the reactions used in a game.
     */
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    /**
     * Compares two entities.
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
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), players, reaction);
    }
}
