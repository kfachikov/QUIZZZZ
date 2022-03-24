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
        super.start();
    }

    /**
     * Stops the service and allows it to be started again.
     */
    public void stop() {
        this.cancel();
        this.reset();
    }

    /**
     * Creates a task which "constantly" polls the server for the
     * current state of the ongoing game.
     *
     * @return GameState polling task
     */
    @Override
    protected Task<MultiPlayerState> createTask() {
        return new Task<MultiPlayerState>() {
            @Override
            protected MultiPlayerState call() {
                MultiPlayerState gameState;
                while (true) {
                    gameState = server.getMultiGameState(gameId);
                    updateValue(gameState);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return gameState;
            }
        };
    }
}