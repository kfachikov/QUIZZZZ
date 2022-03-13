package client.services;

import client.utils.ServerUtils;
import commons.QueueState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class QueuePollingService extends Service<QueueState> {

    private final ServerUtils server;

    @Inject
    public QueuePollingService(ServerUtils server) {
        this.server = server;
    }

    /**
     * Creates a task which continuously polls the server for the list of
     * players in the queue.
     *
     * @return Queue polling task
     */

    public void stop() {
        this.cancel();
        this.reset();
    }

    @Override
    protected Task<QueueState> createTask() {
        return new Task<QueueState>() {
            @Override
            protected QueueState call() throws Exception {
                QueueState queueState;
                while (true) {
                    queueState = server.getQueueState();
                    updateValue(queueState);
                    try {
                        //noinspection BusyWait
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return queueState;
            }
        };
    }
}
