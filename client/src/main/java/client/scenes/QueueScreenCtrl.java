package client.scenes;

import client.utils.QueuePollingService;
import client.utils.ServerUtils;
import commons.MultiUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QueueScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final QueuePollingService pollingService;

    private Task<List<MultiUser>> pollingTask;

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
}
