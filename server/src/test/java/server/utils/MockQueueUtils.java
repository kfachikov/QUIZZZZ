package server.utils;

import commons.queue.QueueState;
import commons.queue.QueueUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

/**
 * Mock class for testing other classes that use QueueUtils.
 */
public class MockQueueUtils extends QueueUtils {

    /**
     * List of methods that were called on this instance.
     */
    public ArrayList<String> calledMethods = new ArrayList<>();
    /**
     * Return value of any called method.
     */
    public Queue<Object> returnValues = new LinkedList<>();

    public ArrayList<Object> params = new ArrayList<>();

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
    public MockQueueUtils(CurrentTimeUtils currentTime) {
        super(currentTime);
    }

    /**
     * Complete constructor for QueueUtils.
     *
     * @param queueState  Initial QueueState.
     * @param currentTime CurrentTimeUtils instance for getting the current time.
     */
    public MockQueueUtils(QueueState queueState, CurrentTimeUtils currentTime) {
        super(queueState, currentTime);
    }

    /**
     * Getter for the current queue state.
     *
     * @return a QueueState instance containing corresponding information as the QueueState on all clients already residing in the queue.
     */
    @Override
    public QueueState getQueue() {
        calledMethods.add("getQueue");
        return (QueueState) returnValues.poll();
    }

    /**
     * Add a user to the queue.
     * <p>
     * User is added to the queue, and the countdown for the start of the game is reset.
     *
     * @param user QueueUser to add to the queue.
     * @return QueueUser (if added successfully), null otherwise
     */
    @Override
    public QueueUser addUser(QueueUser user) {
        calledMethods.add("addUser");
        params.add(user);
        return (QueueUser) returnValues.poll();
    }

    /**
     * Remove a user from the queue.
     *
     * @param user QueueUser to remove from the queue.
     * @return QueueUser that was removed (if valid and in the queue), null otherwise
     */
    @Override
    public QueueUser removeUser(QueueUser user) {
        calledMethods.add("removeUser");
        params.add(user);
        return (QueueUser) returnValues.poll();
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
    @Override
    public boolean startCountdown() {
        calledMethods.add("startCountdown");
        return (boolean) returnValues.poll();
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
    @Override
    public void setOnStart(Supplier<Long> onStart) {
        calledMethods.add("setOnStart");
        params.add(onStart);
    }

    /**
     * Reset the countdown for the queue.
     * <p>
     * Sets the game starting variable to false, and the start time as far away in the future as possible.
     */
    @Override
    public void resetCountdown() {
        calledMethods.add("resetCountdown");
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
    @Override
    public boolean isInvalid(QueueUser user) {
        calledMethods.add("isInvalid");
        params.add(user);
        return (boolean) returnValues.poll();
    }

    /**
     * Check if a QueueUser is already present in the queue.
     *
     * @param user QueueUser to check if they are in the queue.
     * @return true iff the user is already in the queue.
     */
    @Override
    public boolean containsUser(QueueUser user) {
        calledMethods.add("containsUser");
        params.add(user);
        return (boolean) returnValues.poll();
    }

    /**
     * Get a user in the queue by their username.
     *
     * @param username Username of the user
     * @return QueueUser who is in the queue, with the given username.
     */
    @Override
    public QueueUser getByUsername(String username) {
        calledMethods.add("getByUsername");
        params.add(username);
        return (QueueUser) returnValues.poll();
    }
}
