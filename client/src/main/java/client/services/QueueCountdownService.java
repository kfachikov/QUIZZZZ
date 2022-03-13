package client.services;

import client.utils.ServerUtils;
import commons.QueueState;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

import javax.inject.Inject;

public class QueueCountdownService extends Service<Long> {

    private final ServerUtils server;

    private Timeline timeline;
    private LongProperty count;

    @Inject
    public QueueCountdownService(ServerUtils server) {
        this.server = server;
        this.count = new SimpleLongProperty(-1);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), new KeyValue(getCount(), 1));

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);
    }

    /**
     * Creates a task which updates the `count` variable at a regular interval.
     *
     * @return Queue polling task
     */
    @Override
    protected Task<Long> createTask() {
        return new Task<Long>() {
            @Override
            protected Long call() {
                QueueState queueState = server.getQueueState();

                getCount().set(3000);

                timeline.playFrom(Duration.millis(3000 - queueState.msToStart));

                // TODO
                // Set gameId to the actual gameId, instead of a dummy value
                long resultGameId = 99;

                return resultGameId;
            }
        };
    }

    public LongProperty getCount() {
        return count;
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
