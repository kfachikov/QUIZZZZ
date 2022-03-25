package server.utils;

import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utilities class, responsible for keeping state of a multiplayer queue.
 */
@Component
public class QueueUtils {

    private final CurrentTimeUtils currentTime;

    private final QueueState queueState;
    private Supplier<Long> onStart;

    /**
     * Default constructor for QueueUtils.
     * <p>
     * Initializes the starting queue to a default state:
     * - No users in the queue.
     * - Game is not starting.
     * - The start of the game is as far into the future as possible.
     * - Upcoming game ID is 0.
     *
     * @param currentTime CurrentTimeUtils instance for getting the current time.
     */
    public QueueUtils(CurrentTimeUtils currentTime) {
        this(new QueueState(
                new ArrayList<>(),
                false,
                Long.MAX_VALUE,
                0
        ), currentTime);
    }

    /**
     * Complete constructor for QueueUtils.
     *
     * @param queueState  Initial QueueState.
     * @param currentTime CurrentTimeUtils instance for getting the current time.
     */
    public QueueUtils(QueueState queueState, CurrentTimeUtils currentTime) {
        this.queueState = queueState;
        this.currentTime = currentTime;
    }

    /**
     * Getter for the current queue state.
     *
     * @return a QueueState instance containing corresponding information as the QueueState on all clients already residing in the queue.
     */
    public QueueState getQueue() {
        return queueState;
    }

    /**
     * Add a user to the queue.
     * <p>
     * User is added to the queue, and the countdown for the start of the game is reset.
     *
     * @param user QueueUser to add to the queue.
     * @return QueueUser (if added successfully), null otherwise
     */
    public QueueUser addUser(QueueUser user) {
        if (isInvalid(user)) {
            return null;
        } else if (containsUser(user)) {
            return null;
        } else {
            getUsers().add(user);
            resetCountdown();
            return user;
        }
    }

    /**
     * Remove a user from the queue.
     *
     * @param user QueueUser to remove from the queue.
     * @return QueueUser that was removed (if valid and in the queue), null otherwise
     */
    public QueueUser removeUser(QueueUser user) {
        if (isInvalid(user)) {
            return null;
        } else if (!containsUser(user)) {
            return null;
        } else {
            getUsers().remove(user);
            return user;
        }
    }

    /**
     * Begins the countdown for the queue.
     * <p>
     * Sets the game starting variable to true, and the start time to 3 seconds in the future.
     * <p>
     * The method does nothing and returns false if the countdown is already started.
     *
     * @return true iff countdown wasn't started already.
     */
    public boolean startCountdown() {
        if (queueState.isGameStarting()) {
            return false;
        } else {
            queueState.setGameStarting(true);
            queueState.setStartTimeInMs(new Date().getTime() + 3000);

            long upcomingGameId = onStart.get();
            queueState.setUpcomingGameId(upcomingGameId);

            return true;
        }
    }

    /**
     * Setter for upcoming game id supplier, that is called when game starts.
     * <p>
     * This method is used to signal to MultiPlayerStateUtils that a new game is starting,
     * to allow it to generate a new instance of a game, and set the instance `nextGame` to start.
     * <p>
     * The reason for doing this is to keep a clear hierarchy of dependency injection.
     * Other solutions might end up inverting it
     * (e.g. multiplayer games would be a dependency for queue),
     * or accidentally creating a dependency cycle.
     *
     * @param onStart Supplier that returns upcoming game id.
     */
    public void setOnStart(Supplier<Long> onStart) {
        this.onStart = onStart;
    }

    /**
     * Reset the countdown for the queue.
     * <p>
     * Sets the game starting variable to false, and the start time as far away in the future as possible.
     */
    public void resetCountdown() {
        queueState.setGameStarting(false);
        queueState.setStartTimeInMs(Long.MAX_VALUE);
    }

    /**
     * Check if a QueueUser instance is invalid.
     * <p>
     * A QueueUser instance is considered invalid iff any of the following hold:
     * - It is null.
     * - Its username is null.
     * - Its username is empty.
     *
     * @param user QueueUser to check for incorrectness.
     * @return true iff QueueUser is invalid.
     */
    public boolean isInvalid(QueueUser user) {
        if (user == null) {
            return true;
        } else if (user.getUsername() == null) {
            return true;
        } else {
            return user.getUsername().isEmpty();
        }
    }

    /**
     * Check if a QueueUser is already present in the queue.
     *
     * @param user QueueUser to check if they are in the queue.
     * @return true iff the user is already in the queue.
     */
    public boolean containsUser(QueueUser user) {
        return getUsers().contains(user);
    }

    /**
     * Get a user in the queue by their username.
     *
     * @param username Username of the user
     * @return QueueUser who is in the queue, with the given username.
     */
    public QueueUser getByUsername(String username) {
        return getUsers().stream()
                .filter((user -> user.getUsername().equals(username)))
                .findAny().orElse(null);
    }

    /**
     * Shorthand method for getting list of users in the queue.
     *
     * @return List of users in the queue.
     */
    private List<QueueUser> getUsers() {
        return queueState.getUsers();
    }
}
