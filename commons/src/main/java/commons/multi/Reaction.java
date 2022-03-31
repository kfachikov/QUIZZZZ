package commons.multi;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * Reaction class - to be used for emoji submission and reaction revealing.
 */
public class Reaction {

    private String username;
    private String emoji;

    /**
     * Default constructor to be used for JSON parsing.
     */
    public Reaction() {

    }

    /**
     * Proper constructor for creating an instance of Reaction to be communicated with.
     *
     * @param username  The username of the player.
     * @param emoji     Reaction - either laughing, crying, etc.
     */
    public Reaction(String username, String emoji) {
        this.username = username;
        this.emoji = emoji;
    }

    /**
     * Getter for the username of the player who submitted a particular reaction.
     *
     * @return  String representing the username of that player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the emoji submitted by any player.
     *
     * @return  String representing the emoji field.
     */
    public String getEmoji() {
        return emoji;
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
        Reaction reaction = (Reaction) o;
        return Objects.equals(username, reaction.username) && Objects.equals(emoji, reaction.emoji);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Converts the current object into user-friendly format.
     *
     * @return  String representation of our Response object.
     */
    public String toString() { return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE); }

}
