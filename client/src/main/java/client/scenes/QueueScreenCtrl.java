package client.scenes;

import client.services.QueuePollingService;
import client.utils.ServerUtils;
import commons.MultiUserQueue;
import jakarta.ws.rs.NotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class QueueScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final QueuePollingService pollingService;

    private MultiUserQueue user;

    @FXML
    private Label queueLabel;

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

    /**
     * Getter for the queue polling service.
     *
     * @return Queue polling service
     */
    public QueuePollingService getPollingService() {
        return pollingService;
    }

    /**
     * Initializes the queue screen controller by binding the queue label to the
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
        pollingService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                queueLabel.textProperty().set("Queue: " + newValue.size() + " players");
            }
        });
    }

    /**
     * Getter for the MultiUserQueue instance (of this client) inside the queue.
     *
     * @return MultiUserQueue instance inside the queue
     */
    public MultiUserQueue getUser() {
        return user;
    }

    /**
     * Setter for the MultiUserQueue instance (for this client) inside the queue.
     *
     * @param user MultiUserQueue that just joined the queue
     */
    public void setUser(MultiUserQueue user) {
        this.user = user;
    }
}
