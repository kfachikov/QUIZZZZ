package commons.queue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    private List<QueueUser> users;

    /**
     * Whether the game is currently starting, and showing the
     * "Game is starting in X..." label for the client.
     */
    private boolean gameStarting;

    /**
     * Milliseconds until the start of the game.
     * <p>
     * This value is only relevant if the game is starting.
     * <p>
     * It starts at 3000, and goes down to 0.
     */
    private long startTimeInMs;

    /**
     * GameId of the associated MultiPlayerGame.
     */
    private long upcomingGameId;

    /**
     * Default constructor for queue state, used for object mapper.
     */
    public QueueState() {
    }

    /**
     * Complete constructor of the queue.
     *
     * @param users          Users that are in the queue.
     * @param gameStarting   Whether the game is starting.
     * @param startTimeInMs  Milliseconds until the start of the game.
     * @param upcomingGameId gameId of the MultiPlayerGame
     */
    public QueueState(List<QueueUser> users, boolean gameStarting, long startTimeInMs, long upcomingGameId) {
        this.users = users;
        this.gameStarting = gameStarting;
        this.startTimeInMs = startTimeInMs;
        this.upcomingGameId = upcomingGameId;
    }

    /**
     * @return list of QueueUsers
     */
    public List<QueueUser> getUsers() {
        return users;
    }

    /**
     * sets the list of QueueUsers as the passed list.
     *
     * @param users list of QueueUsers
     */
    public void setUsers(List<QueueUser> users) {
        this.users = users;
    }

    /**
     * @return boolean value of whether the multiplayer game starts or not
     */
    public boolean isGameStarting() {
        return gameStarting;
    }

    /**
     * sets gameStarting boolean value as the passed value.
     *
     * @param gameStarting boolean value of whether game starts or not
     */
    public void setGameStarting(boolean gameStarting) {
        this.gameStarting = gameStarting;
    }

    /**
     * @return long millisecond of the start
     */
    public long getStartTimeInMs() {
        return startTimeInMs;
    }

    /**
     * sets setStartTimeInMs long value as the passed value.
     *
     * @param startTimeInMs long value of millisecond until the start
     */
    public void setStartTimeInMs(long startTimeInMs) {
        this.startTimeInMs = startTimeInMs;
    }

    /**
     * @return long gameId of the MultiPlayerGame
     */
    public long getUpcomingGameId() {
        return upcomingGameId;
    }

    /**
     * sets upcomingGameId long value as the passed value.
     *
     * @param upcomingGameId long value of gameId of the MultiPlayerGame
     */
    public void setUpcomingGameId(long upcomingGameId) {
        this.upcomingGameId = upcomingGameId;
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
