package server.utils;

import commons.queue.QueueState;
import server.database.QueueUserRepository;

import java.util.Date;

/**
 * Class for the queue utilities.
 */
public class QueueUtils {

    private boolean gameStarting;
    private long startTimeInMs;
    private long upcomingGameId;


    /**
     * Getter for the current queue state.
     *
     * @param repo  is the current repository of clients waiting in the queue screen
     * @return      a QueueState instance containing corresponding information -
     *              as the QueueState on all clients already residing in the queue.
     */
    public QueueState getCurrentQueue(QueueUserRepository repo) {
        return new QueueState(repo.findAll(), gameStarting, startTimeInMs - new Date().getTime(), upcomingGameId);
    }

    /**
     * Setter for the boolean gameStarting field.
     *
     * @param gameStarting  Boolean value to be set.
     */
    public void setGameStarting(boolean gameStarting) {
        this.gameStarting = gameStarting;
    }

    /**
     * Getter for the boolean field corresponding to whether the game is starting.
     *
     * @return  Whether the current queue is in "starting" phase.
     */
    public boolean isGameStarting() {
        return gameStarting;
    }

    /**
     * Initialized the countdown feature and sets the "state" of the queue to starting.
     */
    public void beginCountdown() {
        gameStarting = true;
        startTimeInMs = new Date().getTime() + 3000;
    }
}
