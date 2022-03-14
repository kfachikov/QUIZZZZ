package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * The current state of the queue.
 * <p>
 * This class is used to communicate between the client and the server about what
 * is going on in the queue.
 */
public class QueueState {
    /**
     * List of users currently in the queue.
     */
    public List<QueueUser> users;
    /**
     * Whether the game is currently starting, and showing the
     * "Game is starting in X..." label for the client.
     */
    public boolean gameStarting;
    /**
     * Milliseconds until the start of the game.
     * <p>
     * This value is only relevant if the game is starting.
     * <p>
     * It starts at 3000, and goes down to 0.
     */
    public long msToStart;

    /**
     * Default constructor for the state of the queue.
     * <p>
     * Initializes the queue as expected:
     * - Not starting
     * - No users inside
     * - (3000ms until start)
     */
    public QueueState() {
        this.users = new ArrayList<>();
        this.gameStarting = false;
        this.msToStart = 3000;
    }

    /**
     * Constructor of the queue, taking the current users.
     * <p>
     * Creates a new instance of the queue which is not starting, but has the
     * users.
     *
     * @param users Users that are already in the queue
     */
    public QueueState(List<QueueUser> users) {
        this.users = users;
        this.gameStarting = false;
        this.msToStart = 3000;
    }

    /**
     * Complete constructor of the queue.
     *
     * @param users        Users that are in the queue.
     * @param gameStarting Whether the game is starting.
     * @param msToStart    Milliseconds until the start of the game.
     */
    public QueueState(List<QueueUser> users, boolean gameStarting, long msToStart) {
        this.users = users;
        this.gameStarting = gameStarting;
        this.msToStart = msToStart;
    }

    /**
     * Compares whether two objects are identical queues.
     * <p>
     * Note that the order of the users in the queue matters.
     * <p>
     * This method uses the default equals method that is generated using
     * EqualsBuilder.
     *
     * @param obj Compared object
     * @return Whether obj is equal to this queue.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Hash code function.
     * <p>
     * Generated automatically with HashCodeBuilder.
     *
     * @return Hash code of the queue.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Multiline string representation of the queue state.
     * <p>
     * Automatically generated using ToStringBuilder.
     *
     * @return Multiline string of the queue.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
