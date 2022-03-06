package client.scenes;

import client.services.QueuePollingService;
import client.utils.ServerUtils;
import commons.MultiUser;
import jakarta.ws.rs.NotFoundException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.List;

public class QueueScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final QueuePollingService pollingService;

    private Task<List<MultiUser>> pollingTask;

    private MultiUser user;

    @FXML
    private Label queueLabel;

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

    public QueuePollingService getPollingService() {
        return pollingService;
    }

    public void bindQueueLabel() {
        pollingService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                queueLabel.textProperty().set("Queue: " + newValue.size() + " players");
            }
        });
    }

    public MultiUser getUser() {
        return user;
    }

    public void setUser(MultiUser user) {
        this.user = user;
    }
}
