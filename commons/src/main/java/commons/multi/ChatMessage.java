package commons.multi;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * ChatMessage class - to be used for emoji submission and reaction revealing.
 */
public class ChatMessage {

    private String username;
    private String message;

    /**
     * Default constructor to be used for JSON parsing.
     */
    public ChatMessage() {

    }

    /**
     * Proper constructor for creating an instance of ChatMessage to be communicated with.
     *
     * @param username  The username of the player.
     * @param message     ChatMessage - either laughing, crying, etc.
     */
    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
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
    public String getMessage() {
        return message;
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
        ChatMessage chatMessage = (ChatMessage) o;
        return Objects.equals(username, chatMessage.username) && Objects.equals(message, chatMessage.message);
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
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
