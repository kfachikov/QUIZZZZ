package client.scenes;

import client.services.QueueCountdownService;
import client.services.QueuePollingService;
import client.utils.ServerUtils;
import commons.QueueUser;
import jakarta.ws.rs.NotFoundException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class QueueScreenCtrl {

    /**
     * Initialize class constant for the number of player's usernames visible on screen.
     */
    private static final int PLAYERSCOUNT = 16;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final QueuePollingService pollingService;
    private final QueueCountdownService countdownService;

    private QueueUser user;
    private BooleanProperty gameStarting;

    @FXML
    private Label queueLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Button startButton;

    @FXML
    private FlowPane bubbles;


    /**
     * Constructor for queue screen controller.
     *
     * @param server         Server utilities
     * @param mainCtrl       Main controller
     * @param pollingService Queue polling service
     */
    @Inject
    public QueueScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl,
            QueuePollingService pollingService,
            QueueCountdownService countdownService
    ) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pollingService = pollingService;
        this.countdownService = countdownService;
    }

    /**
     * Reset the queue scene back to normal values.
     * This prevents flickering when re-entering the queue
     */
    public void resetScene() {
        queueLabel.textProperty().set("Queue: 0 players");
        startLabel.setVisible(false);
        startLabel.setText("Game is starting in 3...");
        startButton.setDisable(false);
    }

    /**
     * Returns from the queue screen back to the home screen.
     */
    public void returnHome() {
        pollingService.cancel();
        pollingService.reset();
        try {
            server.deleteQueueUser(user);
        } catch (NotFoundException ignored) {
        }
        mainCtrl.showHome();
    }

    /**
     * Start the multiplayer game.
     * <p>
     * Sends a POST request to the server, puts a clear white label for the
     * countdown, disables the button and starts the countdown.
     */
    public void startGame() {
        server.startMultiplayerGame();
        startButton.setDisable(true);
        startLabel.setVisible(true);
        gameStarting.set(true);
    }

    /**
     * Getter for the queue polling service.
     *
     * @return Queue polling service
     */
    public QueuePollingService getPollingService() {
        return pollingService;
    }

    /**
     * Initializes the queue screen controller by binding the queue label the queue "bubbles" to the
     * results of the polling service.
     * <p>
     * The queue polling service will repeatedly poll the server, and update its
     * own value.
     * When such update occurs, any attached event listeners are called.
     * This method attaches such an event listener to the value of the polling
     * service. Thus, whenever the polling service updates its value, this event
     * listener is called.
     * <p>
     * The actual callback of the event listener simply sets the queue label to
     * the appropriate value.
     */
    public void initialize() {
        gameStarting = new SimpleBooleanProperty(false);

        /*
        Create an event listener for short-polling
         */
        pollingService.valueProperty().addListener((observable, oldValue, newQueueState) -> {
            if (newQueueState != null) {
                List<QueueUser> queueUsers = newQueueState.users;
                queueLabel.textProperty().set("Queue: " + queueUsers.size() + " players");

                int currentNodeIndex = 0;
                List<Node> presentPlayers = bubbles.getChildren();
                Collections.reverse(queueUsers);

                for (QueueUser multiplayer : queueUsers) {
                    Node currentNode = presentPlayers.get(currentNodeIndex);
                    ((Label) currentNode).setText(multiplayer.username);
                    currentNode.setVisible(true);
                    currentNodeIndex++;
                    if (currentNodeIndex > PLAYERSCOUNT) {
                        break;
                    }
                }
                for (int i = currentNodeIndex; i <= PLAYERSCOUNT; i++) {
                    Node currentNode = presentPlayers.get(currentNodeIndex);
                    currentNode.setVisible(false);
                }

                // Internal state should be consistent with server state
                gameStarting.set(newQueueState.gameStarting);
            }
        });

        /*
        Create an event listener for the countdown, to update the start label
        text in real time
         */
        countdownService.getCount().addListener(((observable, oldValue, newValue) -> {
            // We can only update UI elements from the main JavaFX thread
            // This method adds whatever task we want to do to this thread
            Platform.runLater(() -> {
                double fraction = newValue.doubleValue() / 1000;
                long count = (long) Math.ceil(fraction);
                startLabel.setText("Game is starting in " + count + "...");
            });
        }));
        /*
        Start and stop the service depending on the internal state
         */
        gameStarting.addListener(((observable, oldValue, newValue) -> {
            // "Go!" button should be disabled if the game is already starting
            startButton.setDisable(newValue);
            // "Game is starting in X..." label should be visible if the game
            // is starting
            startLabel.setVisible(newValue);
            if (newValue) {
                countdownService.start();
            } else {
                countdownService.cancel();
                countdownService.reset();
            }
        }));

        /*
         * Switch scene once countdown reaches 0
         */
        countdownService.setOnSucceeded(event -> {
            Long result = (Long) event.getSource().getValue();
            mainCtrl.showMultiGameQuestion(result);
        });
    }

    /**
     * Getter for the QueueUser instance (of this client) inside the queue.
     *
     * @return QueueUser instance inside the queue
     */
    public QueueUser getUser() {
        return user;
    }

    /**
     * Setter for the QueueUser instance (for this client) inside the queue.
     *
     * @param user QueueUser that just joined the queue
     */
    public void setUser(QueueUser user) {
        this.user = user;
    }
}
