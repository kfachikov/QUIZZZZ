package client.services;

import client.utils.ServerUtils;
import commons.queue.QueueState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

/**
 * Service responsible for repeatedly polling the queue.
 *
 * The task created by this service never terminates on its own.
 * It must be terminated by external method, preferably using stop().
 *
 * The task of this class will continuously update its temporary value to the
 * current state of the queue.
 */
public class QueuePollingService extends Service<QueueState> {

    private final ServerUtils server;

    /**
     * Constructor for QueuePollingService.
     *
     * @param server Injected ServerUtils instance
     */
    @Inject
    public QueuePollingService(ServerUtils server) {
        this.server = server;
    }

    /**
     * Stops the service and allows it to be started again.
     */
    public void stop() {
        this.cancel();
        this.reset();
    }

    /**
     * Creates a task which continuously polls the server for the list of
     * players in the queue.
     *
     * @return Queue polling task
     */
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
