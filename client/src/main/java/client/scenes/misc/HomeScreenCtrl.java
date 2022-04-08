package client.scenes.misc;

import client.utils.HomeUtils;
import client.utils.InputPreloadUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 */
public class HomeScreenCtrl {


    private final InputPreloadUtils inputPreloadUtils;
    private final MainCtrl mainCtrl;
    private final HomeUtils homeUtils;

    @FXML
    private Text count;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField serverURL;

    @FXML
    private Label errorMessage;

    /**
     * initializes HomeScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param inputPreloadUtils is the utility class for preloading username and address.
     * @param mainCtrl          is the main controller variable
     * @param homeUtils         is the home screen utility class
     */
    @Inject
    public HomeScreenCtrl(InputPreloadUtils inputPreloadUtils, MainCtrl mainCtrl, HomeUtils homeUtils) {
        this.mainCtrl = mainCtrl;
        this.inputPreloadUtils = inputPreloadUtils;
        this.homeUtils = homeUtils;
    }

    /**
     * Method to be executed once the instance of the home controller is created.
     * Would set the corresponding fields in the home utility class,
     * so functionality can be extracted there.
     */
    public void initialize() {
        var usernameAddressPair = inputPreloadUtils.readInput();
        usernameField.setText(usernameAddressPair.getKey());
        serverURL.setText(usernameAddressPair.getValue());
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
        inputPreloadUtils.saveInput(usernameField.getText(), serverURL.getText());
    }


    /**
     * Calls the HomeUtils.enterMultiPlayerMode() which checks whether everything
     * required is fulfilled - present and unique username, and valid server,
     * and behaves appropriately.
     */
    public void playMulti() {
        homeUtils.enterMultiPlayerMode();
        inputPreloadUtils.saveInput(usernameField.getText(), serverURL.getText());
    }

    /**
     * shows the administrator panel.
     */
    public void showAdministratorPanel() {
        homeUtils.enterAdministrationPanel();
        inputPreloadUtils.saveInput(usernameField.getText(), serverURL.getText());
    }

}
