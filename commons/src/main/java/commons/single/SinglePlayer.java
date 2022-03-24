package commons.single;

import commons.misc.Player;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * The SinglePlayer class is used to represent the current player of the solo game.
 * Its username and final score will appear in the global leaderboard.
 */
public class SinglePlayer extends Player {

    public SinglePlayer() {
        super();
    }

    /**
     * Constructor for the SinglePlayer instances.
     *
     * @param username the name of the player
     * @param score    current score of the player
     */
    public SinglePlayer(String username, int score) {
        super(username, score);
    }

    /**
     * The method checks whether two instances of SinglePlayer are equal.
     *
     * @param obj the object that needs to be checked for equality
     * @return true/false
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * The method created the hashcode of the instance.
     *
     * @return SinglePlayer's instance hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Generates string version of the instance.
     *
     * @return String version
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
