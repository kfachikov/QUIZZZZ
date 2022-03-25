package client.services;

import client.utils.ServerUtils;
import commons.queue.QueueState;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

import javax.inject.Inject;
import java.util.Date;

/**
 * Responsible for the countdown in the queue.
 * <p>
 * When started, this service will query the server for the queue state, and get
 * the milliseconds until the game starts. It will then correct any offset, so
 * the countdown will always end precisely when the game starts.
 * <p>
 * As the service runs, the LongProperty count will be gradually updated,
 * starting with 3000 (or less, accounting for the offset), towards 1.
 * <p>
 * The service will end immediately after it is run, as a Timeline is created in
 * the background. To do some action after the countdown ends,
 * getTimeline().setOnFinished(event -> {}) is the appropriate method for that.
 * <p>
 * TODO
 * The service will return the game ID of the upcoming game.
 */
public class QueueCountdownService extends Service<Long> {

    private final ServerUtils server;

    private final Timeline timeline;
    private final LongProperty count;

    /**
     * Constructor for the QueueCountdownService.
     * <p>
     * Initializes the count and timeline fields.
     *
     * @param server Injected ServerUtils instance
     */
    @Inject
    public QueueCountdownService(ServerUtils server) {
        this.server = server;
        this.count = new SimpleLongProperty(-1);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), new KeyValue(count, 1));

        timeline = new Timeline(keyFrame);
    }

    /**
     * Stops the service and the associated timeline.
     */
    public void stop() {
        this.cancel();
        this.reset();
        timeline.stop();
    }

    /**
     * Creates a task which updates the count variable at a regular interval.
     *
     * @return Queue polling task
     */
    @Override
    protected Task<Long> createTask() {
        return new Task<Long>() {
            @Override
            protected Long call() {
                QueueState queueState = server.getQueueState();
                long msToStart = queueState.getStartTimeInMs() - new Date().getTime();

                getCount().set(3000);

                timeline.playFrom(Duration.millis(3000 - msToStart));

                return queueState.getUpcomingGameId();
            }
        };
    }

    /**
     * Getter for the count property.
     * <p>
     * Ranges from 3000 to 1.
     *
     * @return Countdown count.
     */
    public LongProperty getCount() {
        return count;
    }

    /**
     * Getter for the timeline.
     *
     * @return Timeline instance associated with this service.
     */
    public Timeline getTimeline() {
        return timeline;
    }
}
