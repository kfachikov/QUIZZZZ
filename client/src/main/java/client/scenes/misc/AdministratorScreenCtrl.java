package client.scenes.misc;

import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 *
 */
public class AdministratorScreenCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ActivityImageUtils activityImageUtils;

    @FXML
    private Text description;

    @FXML
    private Button selectFileButton;

    /**
     * initializes AdministratorScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server             is the server variable
     * @param mainCtrl           is the main controller variable
     * @param activityImageUtils is the utilities class responsible for setting an image of an activity.
     */
    @Inject
    public AdministratorScreenCtrl(
            ServerUtils server,
            MainCtrl mainCtrl,
            ActivityImageUtils activityImageUtils
    ) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.activityImageUtils = activityImageUtils;
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
     *
     * @throws IOException IO Exception that's being thrown.
     */
    public void chooseFile() throws IOException {
        File selectedFile = fileSelection();
        setDescription(selectedFile);
        List<Activity> addedActivities = server.importActivities(extractFile(selectedFile));
        activityImageUtils.addActivitiesImages(selectedFile.getPath(), addedActivities);
    }

    /**
     * Sets the text prompt on the screen as "You have imported file.json".
     *
     * @param selectedFile the file selected by the user
     */
    public void setDescription(File selectedFile) {
        description.setText("You have imported " + selectedFile.getName());
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

    /**
     * Stringifies the contents of the file.
     *
     * @param selectedFile the file selected by the user
     * @return String of the contents of the file.
     * @throws IOException IO Exception that's being thrown
     */
    public String extractFile(File selectedFile) throws IOException {
        return Files.readString(selectedFile.toPath());
    }
}
