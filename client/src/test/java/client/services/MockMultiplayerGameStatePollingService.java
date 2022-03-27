package client.services;

import client.utils.MockServerUtils;
import commons.multi.MultiPlayerState;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class MockMultiplayerGameStatePollingService extends MultiplayerGameStatePollingService {
    public long gameId;
    public Object returnValue;
    public List<String> calledMethods;

    /**
     * Constructor for MockMultiplayerGameStatePollingService.
     *
     * @param server Injected ServerUtils instance
     */
    public MockMultiplayerGameStatePollingService(MockServerUtils server) {
        super(server);
        this.calledMethods = new ArrayList<>();
    }

    private void call(String method) {
        calledMethods.add(method);
    }

    /**
     * Start the service with the given multiplayer game id.
     *
     * @param gameId Multiplayer game id.
     */
    @Override
    public void start(long gameId) {
        call("start");
    }

    /**
     * Stop the service.
     */
    @Override
    public void stop() {
        call("stop");
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
     * Get the state of the multiplayer game.
     *
     * @return State of the multiplayer game.
     */
    @Override
    public MultiPlayerState poll() {
        call("poll");
        return (MultiPlayerState) returnValue;
    }

    /**
     * Creates a task which continuously polls the server for the state of the game.
     *
     * @return State polling task
     */
    @Override
    protected Task<MultiPlayerState> createTask() {
        call("createTask");
        return (Task<MultiPlayerState>) returnValue;
    }
}
