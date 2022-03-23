package client.scenes.misc;

import client.utils.HomeUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 */
public class HomeScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final HomeUtils homeUtils;

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
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl, HomeUtils homeUtils) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.homeUtils = homeUtils;
    }

    /**
     * Method to be executed once the instance of the home controller is created.
     * Would set the corresponding fields in the home utility class,
     * so functionality can be extracted there.
     */
    public void initialize() {
        homeUtils.setHomeUtilsAttributes(usernameField, serverURL, errorMessage);
    }

    /**
     * runs showHelp() function.
     */
    public void helpMe() {
        mainCtrl.showHelp();
    }

    /**
     * Calls the HomeUtils.enterSinglePlayerMode() which checks whether everything
     * required is fulfilled - present username and valid server, and behaves
     * accordingly.
     */
    public void playSolo() {
        homeUtils.enterSinglePlayerMode();
    }


    /**
     * Calls the HomeUtils.enterMultiPlayerMode() which checks whether everything
     * required is fulfilled - present and unique username, and valid server,
     * and behaves appropriately.
     */
    public void playMulti() {
        homeUtils.enterMultiPlayerMode();
    }

    /**
     * shows the administrator panel.
     */
    public void showAdministratorPanel() {
        mainCtrl.showAdministrator();
    }


}
