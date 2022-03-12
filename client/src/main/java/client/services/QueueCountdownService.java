package client.services;

import client.utils.ServerUtils;
import commons.QueueState;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

import javax.inject.Inject;

public class QueueCountdownService extends Service<Long> {

    private IntegerProperty count;

    private final ServerUtils server;

    @Inject
    public QueueCountdownService(ServerUtils server) {
        this.server = server;
        this.count = new SimpleIntegerProperty(-1);
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

                getCount().set(3);

                KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), new KeyValue(getCount(), 1));

                Timeline timeline = new Timeline(keyFrame);
                timeline.setCycleCount(1);

                timeline.playFrom(Duration.millis(3000 - queueState.msToStart));

                return -1L;
            }
        };
    }

    public IntegerProperty getCount() {
        return count;
    }
}
