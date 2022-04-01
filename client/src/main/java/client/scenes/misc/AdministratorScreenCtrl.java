package client.scenes.misc;

import client.services.ActivityLoaderService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 *
 */
public class AdministratorScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ActivityImageUtils activityImageUtils;
    private final ActivityLoaderService activityLoaderService;

    @FXML
    private Text description;

    @FXML
    private Button selectFileButton;

    /**
     * initializes AdministratorScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server                is the server variable
     * @param mainCtrl              is the main controller variable
     * @param activityImageUtils    is the utilities class responsible for setting an image of an activity.
     * @param activityLoaderService is the service for loading the activities
     */
    @Inject
    public AdministratorScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl,
            ActivityImageUtils activityImageUtils,
            ActivityLoaderService activityLoaderService
    ) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.activityImageUtils = activityImageUtils;
        this.activityLoaderService = activityLoaderService;
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * Provides functionality to the Import Activities button.
     * Pops up local file directory for the user to choose a .json file of activities (JsonArray).
     * Sets the text on the screen as "You have imported file.json".
     * Passes the file (in String) to the ServerUtils' method ImportActivities, which sends the file to server using POST.
     */
    public void chooseFile() {
        File selectedFile = fileSelection();
        activityLoaderService.start(selectedFile);
        activityLoaderService.setOnSucceeded(event -> {
            List<Activity> loadedActivities = activityLoaderService.getValue();
            setDescription(
                    "You have imported " + loadedActivities.size() + " activities from " + selectedFile.getName()
            );
        });
    }

    /**
     * Sets the text prompt on the screen.
     *
     * @param text new text to be set.
     */
    public void setDescription(String text) {
        description.setText(text);
    }

    /**
     * Pops up local file directory for the user to choose a .json file of activities (JsonArray).
     *
     * @return the selected file
     */
    public File fileSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }
}
