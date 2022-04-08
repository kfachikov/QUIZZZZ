package client.utils;

import client.scenes.misc.MainCtrl;
import commons.queue.QueueUser;
import commons.single.SinglePlayer;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import javax.inject.Inject;

/**
 * Class for home utilities.
 */
public class HomeUtils {

    private static final int FORBIDDEN = 403;
    private static final int BAD_REQUEST = 400;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private TextField usernameField;
    private TextField serverURL;
    private Label errorMessage;

    /**
     * Constructor for instantiating a HomeUtils instance using Spring injection.
     *
     * @param mainCtrl Main controller tackles all scene and controller changes.
     * @param server   A Server instance to be set to the serverUtils field.
     */
    @Inject
    public HomeUtils(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Sets the current server to the entered one.
     * Creates a SinglePlayer instance from the username entered and a default score - 0.
     * <p>
     * TODO: A simple GET method should be create so the server is validated somehow.
     */
    public void enterSinglePlayerMode() {
        try {
            setDefault();
            server.setCurrentServer(getServer());
            /* The following line is useless, as the SinglePlayer instances would not be stored anywhere.
               Still there could be some sort of POST/GET request to the server,
               which would be used to check whether the server is valid.

               The missing username in this case could be handled in the getSinglePlayer() method, which
               could check whether the textField is null or empty.
               The catch clause should then be changed according to the Exception thrown by
               getSinglePlayer().
            */
            // SinglePlayer singlePlayer = server.addSinglePlayer(getSinglePlayer());
            server.checkServer();
            mainCtrl.showPrep(getSinglePlayer());
        } catch (WebApplicationException e) {
            switch (e.getResponse().getStatus()) {
                default:
                    unknownError();
            }
        } catch (ProcessingException e) {
            serverInvalid();
        } catch (NullPointerException e) {
            usernameMissing();
        }
    }

    /**
     * Sends a POST request to the server, adding the user to the queue,
     * and then switches the scene to the queue.
     * <p>
     * If a player tries to enter multiplayer queue without entering username, or using an already existing one,
     * WebApplicationException is thrown and handled accordingly.
     * <p>
     * If the server entered is not a valid (running) one, then ProcessingException is thrown.
     */
    public void enterMultiPlayerMode() {
        try {
            setDefault();
            String username = usernameField.getText();
            server.setCurrentServer(getServer());
            QueueUser user = server.addQueueUser(new QueueUser(username));
            mainCtrl.showQueue(user, server.getCurrentServer());
        } catch (WebApplicationException e) {
            switch (e.getResponse().getStatus()) {
                case FORBIDDEN:
                    usernameNotUnique();
                    break;
                case BAD_REQUEST:
                    usernameMissing();
                    break;
                default:
                    unknownError();
            }
        } catch (ProcessingException e) {
            serverInvalid();
        }
    }

    /**
     * Method to check whether the server entered is a valid one and proceed
     * with redirecting the user to the page they want to visit.
     */
    public void enterAdministrationPanel() {
        server.setCurrentServer(getServer());
        try {
            /*
            A GET/POST request should be sent over to the server, so that a
            "validation" process occur.
             */
            mainCtrl.showAdministrator();
        } catch (ProcessingException e) {
            /*
            Exception thrown should be specified in regard to exception returned from the server.
             */
            serverInvalid();
        }
    }

    /**
     * Setter for all attributes corresponding to the fields to be altered on the home screen.
     *
     * @param usernameField is the field where the client would enter their username
     * @param serverURL     is the field where the client would enter the server they want to join to
     * @param errorMessage  is the message which should be shown once an error occur
     */
    public void setHomeUtilsAttributes(TextField usernameField, TextField serverURL, Label errorMessage) {
        this.usernameField = usernameField;
        this.serverURL = serverURL;
        this.errorMessage = errorMessage;
    }

    /**
     * Getter for a singleplayer player.
     *
     * @return a new SingleUser object that contains its username and score.
     */
    public SinglePlayer getSinglePlayer() throws NullPointerException {
        String user = usernameField.getText();
        if ("".equals(user)) {
            throw new NullPointerException();
        } else {
            return new SinglePlayer(user, 0);
        }
    }

    /**
     * @return the server address entered in server address field.
     */
    public String getServer() {
        return serverURL.getText();
    }


    /**
     * Reusable method to be executed once an invalid server is entered.
     * <p>
     * Sets server field background to red to pull the attention of the client.
     * Removes the background of the username field, as the current problem is somewhere else.
     */
    private void serverInvalid() {
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("f2dede")).toString().substring(2));
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Invalid URL address!");
        errorMessage.setVisible(true);
    }

    /**
     * Reusable method to be executed once the username entered in not unique - already exist in queue.
     * <p>
     * Sets username field background to red to pull the attention of the client.
     * Removes the background of the server field, as the current problem is somewhere else.
     */
    private void usernameNotUnique() {
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
     * <p>
     * Sets username field background to red to pull the attention of the client.
     * Removes the background of the server field, as the current problem is somewhere else.
     */
    private void usernameMissing() {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("f2dede")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Username missing!");
        errorMessage.setVisible(true);
    }

    /**
     * Reusable method to be executed once a user tries to join a game and an unknown error arises.
     */
    private void unknownError() {
        usernameField.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        serverURL.setStyle("-fx-control-inner-background: #" + (Paint.valueOf("FFFFFF")).toString().substring(2));
        errorMessage.setText("Make sure you have entered a unique username and a valid URL address!");
        errorMessage.setVisible(true);
    }

}
