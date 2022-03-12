package client.scenes;

import client.services.QueuePollingService;
import client.utils.ServerUtils;
import commons.QueueUser;
import jakarta.ws.rs.NotFoundException;
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

    private QueueUser user;

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
    public QueueScreenCtrl(ServerUtils server, MainCtrl mainCtrl, QueuePollingService pollingService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pollingService = pollingService;
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

    public void startGame() {
        server.startMultiplayerGame();
        startButton.setDisable(true);
        startLabel.setVisible(true);
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

                startButton.setDisable(newQueueState.gameStarting);
                startLabel.setVisible(newQueueState.gameStarting);
            }
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
