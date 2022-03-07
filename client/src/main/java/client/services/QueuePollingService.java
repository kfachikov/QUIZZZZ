package client.services;

import client.utils.ServerUtils;
import commons.MultiUserQueue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import java.util.List;

public class QueuePollingService extends Service<List<MultiUserQueue>> {

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
    @Override
    protected Task<List<MultiUserQueue>> createTask() {
        return new Task<List<MultiUserQueue>>() {
            @Override
            protected List<MultiUserQueue> call() throws Exception {
                while (true) {
                    updateValue(server.getQueueUsers());
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return server.getQueueUsers();
            }
        };
    }
}
