package client.services;

import client.utils.ServerUtils;
import commons.queue.QueueState;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MockQueuePollingService extends QueuePollingService {

    public Object returnValue;

    public List<String> calledMethods;

    public MockQueuePollingService(ServerUtils server) {
        super(server);
        this.calledMethods = new ArrayList<>();
    }

    private void call(String method) {
        calledMethods.add(method);
    }

    /**
     * Creates a task which continuously polls the server for the list of
     * players in the queue.
     *
     * @return Queue polling task
     */
    @Override
    protected Task<QueueState> createTask() {
        call("createTask");
        return (Task<QueueState>) returnValue;
    }

    /**
     * Cancels any currently running Task, if any. The state will be set to CANCELLED.
     *
     * @return returns true if the cancel was successful
     */
    @Override
    public boolean cancel() {
        call("cancel");
        return (Boolean) returnValue;
    }

    /**
     * Cancels any currently running Task, if any, and restarts this Service. The state
     * will be reset to READY prior to execution. This method should only be called on
     * the FX application thread.
     */
    @Override
    public void restart() {
        call("restart");
    }

    /**
     * Resets the Service. May only be called while in one of the finish states,
     * that is, SUCCEEDED, FAILED, or CANCELLED, or when READY. This method should
     * only be called on the FX application thread.
     */
    @Override
    public void reset() {
        call("reset");
    }

    /**
     * Starts this Service. The Service must be in the READY state to succeed in this call.
     * This method should only be called on the FX application thread.
     */
    @Override
    public void start() {
        call("start");
    }

    @Override
    public void stop() {
        call("stop");
    }
}
