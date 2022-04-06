package client.scenes.multi;

import client.scenes.misc.MainCtrl;
import client.services.QueueCountdownService;
import client.services.QueuePollingService;
import client.utils.ServerUtils;
import commons.queue.QueueUser;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class QueueScreenCtrl {

    /**
     * Initialize class constant for the number of player's usernames visible on screen.
     */
    private static final int PLAYERSCOUNT = 16;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final MultiplayerCtrl multiCtrl;
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

    @FXML
    private Label serverAddress;


    /**
     * Constructor for queue screen controller.
     *
     * @param server           Server utilities
     * @param mainCtrl         Main controller
     * @param multiCtrl        Multiplayer controller
     * @param pollingService   Queue polling service
     * @param countdownService Queue countdown service
     */
    @Inject
    public QueueScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl,
            MultiplayerCtrl multiCtrl,
            QueuePollingService pollingService,
            QueueCountdownService countdownService
    ) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiCtrl = multiCtrl;
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
        leaveQueue();
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
        mainCtrl.getPrimaryStage().setTitle("Quizzz: Multiplayer Game");

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
        pollingService.valueProperty().addListener((observable, oldState, newState) -> {
            if (newState != null) {
                queueLabel.textProperty().set("Queue: " + newState.getUsers().size() + " players");

                updateBubbles(newState.getUsers());

                // Internal state should be consistent with server state
                gameStarting.set(newState.isGameStarting());
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
                countdownService.stop();
            }
        }));

        /*
         * Switch scene once countdown reaches 0
         */
        countdownService.getTimeline().setOnFinished(event -> {
            Long upcomingGameId = countdownService.getValue();
            multiCtrl.start(upcomingGameId, leaveQueue().getUsername());
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

    /**
     * Convenience method for leaving the queue, with the appropriate cleanup.
     * <p>
     * Stops any running services (if any) and returns the associated queue user.
     *
     * @return QueueUser instance inside the queue
     */
    public QueueUser leaveQueue() {
        pollingService.stop();
        countdownService.stop();
        multiCtrl.stop();
        return server.deleteQueueUser(user);
    }

    /**
     * @param serverAddress is a String
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress.setText("Server address: " + serverAddress);
    }

    /**
     * Update the list of players shown in bubbles with the given users.
     * <p>
     * Removes any players which are no longer in the list.
     * Then adds any players who have joined.
     *
     * @param users List of QueueUsers to display in bubbles.
     */
    public void updateBubbles(List<QueueUser> users) {
        List<QueueUser> alreadyUsers = new ArrayList<>();
        // Remove players who have left
        for (Iterator<Node> iterator = bubbles.getChildren().iterator(); iterator.hasNext(); ) {
            Node node = iterator.next();
            if (node instanceof Label) {
                Label label = (Label) node;
                QueueUser potentialLeaver = new QueueUser(label.getText());
                if (!users.contains(potentialLeaver)) {
                    iterator.remove();
                } else {
                    alreadyUsers.add(potentialLeaver);
                }
            }
        }

        List<QueueUser> joinedUsers = new ArrayList<>(users);
        joinedUsers.removeAll(alreadyUsers);

        // Add players who have joined
        for (QueueUser joiner : joinedUsers) {
            Label label = new Label(joiner.getUsername());
            label.setTextAlignment(TextAlignment.CENTER);
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(50);
            label.setMinWidth(70);
            bubbles.getChildren().add(label);
        }
    }
}
