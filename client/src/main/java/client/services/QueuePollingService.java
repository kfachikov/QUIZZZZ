package client.services;

import client.utils.ServerUtils;
import commons.MultiUser;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import java.util.List;

public class QueuePollingService extends Service<List<MultiUser>> {

    private final ServerUtils server;

    @Inject
    public QueuePollingService(ServerUtils server) {
        this.server = server;
    }

    /**
     * @return the Task to execute
     */
    @Override
    protected Task<List<MultiUser>> createTask() {
        return new Task<List<MultiUser>>() {
            @Override
            protected List<MultiUser> call() throws Exception {
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
