package client.services;

import client.utils.ServerUtils;
import commons.multi.MultiPlayerState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

/**
 * Service responsible for repeatedly polling the game state.
 * <p>
 * The task of this class will continuously update its temporary value to the
 * current state of the ongoing game.
 */
public class MultiplayerGameStatePollingService extends Service<MultiPlayerState> {

    private final ServerUtils server;
    private long gameId;

    /**
     * Constructor for GameStatePollingService.
     *
     * @param server Injected ServerUtils instance
     */
    @Inject
    public MultiplayerGameStatePollingService(ServerUtils server) {
        this.server = server;
    }

    /**
     * Start the service with the given multiplayer game id.
     *
     * @param gameId Multiplayer game id.
     */
    public void start(long gameId) {
        this.gameId = gameId;
        this.start();
    }

    /**
     * Stop the service.
     */
    public void stop() {
        this.cancel();
        this.reset();
    }

    /**
     * Get the state of the multiplayer game.
     *
     * @return State of the multiplayer game.
     */
    public MultiPlayerState poll() {
        return server.getMultiGameState(gameId);
    }

    @Override
    protected Task<MultiPlayerState> createTask() {
        return new Task<MultiPlayerState>() {
            @Override
            protected MultiPlayerState call() {
                MultiPlayerState state;
                while (true) {
                    state = poll();
                    updateValue(state);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return state;
            }
        };
    }
}