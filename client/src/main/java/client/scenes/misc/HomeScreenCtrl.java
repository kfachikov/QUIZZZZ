package client.scenes.misc;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import commons.single.SingleUser;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class HomeScreenCtrl {

    private static final int FORBIDDEN = 403;
    private static final int BAD_REQUEST = 400;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField serverURL;

    @FXML
    private Label errorMessage;

    /**
     * initializes HomeScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * runs showHelp() function.
     */
    public void helpMe() {
        mainCtrl.showHelp();
    }

    /**
     * adds the user to the server and initialises the title and scene to Prepare.
     */
    public void playSolo() {
        try {
            setDefault();
            ServerUtils.setCurrentServer(getServer());
            /* The following line is useless, as the SinglePlayer instances would be stored anywhere.
               Still there could be some sort of POST request to the server, which would be used to check whether the server is valid.
               The missing username in this case could be handled in the getSinglePlayer() method, which
               could check whether the textField is null or empty.
            */
            // SinglePlayer singlePlayer = server.addSinglePlayer(getSinglePlayer());
            mainCtrl.showPrep(getSinglePlayer());
        } catch (WebApplicationException e) {
            switch (e.getResponse().getStatus()) {
            case BAD_REQUEST: usernameMissing();
            break;
            default: unknownError();
            }
        } catch (ProcessingException e) {
            serverInvalid();
        }
    }

    /**
     * @return a new SingleUser object that contains its username and score.
     */
    public SinglePlayer getSinglePlayer() {
        String user = usernameField.getText();
        return new SinglePlayer(user, 0);
    }

    /**
     * @return the server address entered in server address field.
     */
    public String getServer() {
        return serverURL.getText();
    }

    /**
     * Sends a POST request to the server, adding the user to the queue,
     * and then switches the scene to the queue.
     *
     * If a player tries to enter multiplayer queue without entering username, or using an already existing one,
     * WebApplicationException is thrown and handled accordingly.
     *
     * If the server entered is not a valid (running) one, then ProcessingException is thrown.
     */
    public void playMulti() {
        try {
            setDefault();
            String username = usernameField.getText();
            ServerUtils.setCurrentServer(getServer());
            QueueUser user = server.addQueueUser(new QueueUser(username));
            mainCtrl.showQueue(user);
        } catch (WebApplicationException e) {
            switch (e.getResponse().getStatus()) {
            case FORBIDDEN: usernameNotUnique();
            break;
            case BAD_REQUEST: usernameMissing();
            break;
            default: unknownError();
            }
        } catch (ProcessingException e) {
            serverInvalid();
        }
    }

    public void showAdministratorPanel() {
        mainCtrl.showAdministrator();
    }

    /**
     * Reusable method to be executed once an invalid server is entered.
     *
     * Sets server field background to red to pull the attention of the client.
     * Removes the background of the username field, as the current problem is somewhere else.
     */
    private void serverInvalid () {
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("f2dede")).toString().substring(2));
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Invalid URL address!");
        errorMessage.setVisible(true);
    }

    /**
     * Reusable method to be executed once the username entered in not unique - already exist in queue.
     *
     * Sets username field background to red to pull the attention of the client.
     * Removes the background of the server field, as the current problem is somewhere else.
     */
    private void usernameNotUnique () {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("f2dede")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Username not unique!");
        errorMessage.setVisible(true);
    }

    /**
     * Sets all fields to their default state - called before a player tries to proceed to remove old error notifications.
     */
    private void setDefault() {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setVisible(false);
    }

    /**
     * Reusable method to be executed once a user tries to join a game without a username
     *
     * Sets username field background to red to pull the attention of the client.
     * Removes the background of the server field, as the current problem is somewhere else.
     */
    private void usernameMissing () {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("f2dede")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Username missing!");
        errorMessage.setVisible(true);
    }

    /**
     * Reusable method to be executed once a user tries to join a game and an unknown error arises.
     */
    private void unknownError () {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Make sure you have entered a unique username and a valid URL address!");
        errorMessage.setVisible(true);
    }

}
