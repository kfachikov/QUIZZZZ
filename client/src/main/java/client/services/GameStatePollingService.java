package client.services;

import client.utils.ServerUtils;
import commons.GameState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;


/**
 * Service responsible for repeatedly polling the game state.
 *
 * The task of this class will continuously update its temporary value to the
 * current state of the ongoing game.
 */
public class GameStatePollingService extends Service<GameState> {

    private final ServerUtils server;

    /**
     * Constructor for GameStatePollingService.
     *
     * @param server Injected ServerUtils instance
     */
    @Inject
    public GameStatePollingService(ServerUtils server) {
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
     * Creates a task which "constantly" polls the server for the
     * current state of the ongoing game.
     *
     * @return GameState polling task
     */
    @Override
    protected Task<GameState> createTask() {
        return new Task<GameState>() {
            @Override
            protected GameState call() throws Exception {
                GameState gameState;
                while (true) {
                    gameState = server.getGameState();
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