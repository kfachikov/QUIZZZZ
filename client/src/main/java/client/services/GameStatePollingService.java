package client.services;

import client.utils.ServerUtils;
import commons.misc.GameState;
import commons.single.SinglePlayerState;
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
    private SinglePlayerState singlePlayerState;

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
                    gameState = server.getSoloGameState(singlePlayerState.getId());
                    updateValue(gameState);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(singlePlayerState);
                }
                return gameState;
            }
        };
    }

    /**
     * Getter for current single-player game state.
     *
     * @return Current state of game being played.
     */
    public SinglePlayerState getSinglePlayerState() {
        return singlePlayerState;
    }

    /*
    The following method should be called once the game is initialized.
    When the `startSinglePlayerGame` is called and request is sent to the server,
    the returned instance is `SinglePlayerState`. This instance should be passed
    as argument, so the polling service is set properly.
     */
    /**
     * Setter for current single-player game state - would be used for the unique gameId to fetch the correct
     * SinglePlayerState from the server.
     *
     * @param singlePlayerState SinglePlayerState instance, which id would be used for polling.
     */
    public void setSinglePlayerState(SinglePlayerState singlePlayerState) {
        this.singlePlayerState = singlePlayerState;
    }
}