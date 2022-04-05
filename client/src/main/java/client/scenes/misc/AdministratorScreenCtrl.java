package client.scenes.misc;

import client.services.ActivityLoaderService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Activity;
import jakarta.ws.rs.BadRequestException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.LongStringConverter;

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

    @FXML
    private ProgressBar importProgressBar;

    @FXML
    private TextArea helpText;

    @FXML
    private TableView activityTable;

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
     * Set up the administrator screen to default values, if it is not running.
     */
    public void setup() {
        if (!activityLoaderService.isRunning()) {
            description.setText("Select a .json file to import activities and generate questions.");
            helpText.setVisible(false);
            importProgressBar.setVisible(false);
            selectFileButton.setDisable(false);
            activityLoaderService.reset();
        }
        fillTable();
    }

    /**
     * Called when clicked on "?" text in bottom left.
     * <p>
     * Toggles visibility of the help text.
     */
    public void helpMe() {
        helpText.setVisible(!helpText.isVisible());
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
        if (selectedFile == null) {
            return;
        }

        selectFileButton.setDisable(true);
        importProgressBar.setVisible(true);
        description.setText("Activities are being imported...");

        importActivities(selectedFile);
    }

    /**
     * Helper method for importing activities from the selected file.
     *
     * @param selectedFile File that the user selected.
     */
    public void importActivities(File selectedFile) {
        importProgressBar.progressProperty().bind(activityLoaderService.progressProperty());
        activityLoaderService.setOnSucceeded(event -> {
            List<Activity> loadedActivities = activityLoaderService.getValue();
            setDescription(
                    "You have imported " + loadedActivities.size() + " activities from " + selectedFile.getName()
            );
        });
        activityLoaderService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof BadRequestException) {
                description.setText("Failed to import: " + selectedFile.getName() + " is malformed.");
            }
            activityLoaderService.cancel();
            importProgressBar.setVisible(false);
            selectFileButton.setDisable(false);
            activityLoaderService.reset();
        });

        activityLoaderService.start(selectedFile);
        fillTable();
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

    public void fillTable() {
        List<Activity> activities = server.getActivities();

        activityTable.setEditable(true);

        TableColumn<Activity, String> activityName = new TableColumn<>("title");
        activityName.setCellValueFactory(new PropertyValueFactory<>("title"));
        activityName.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Activity, Long> activityConsumption = new TableColumn<>("consumption (wh)");
        activityConsumption.setCellValueFactory(new PropertyValueFactory<>("consumption"));
        activityConsumption.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));


        activityTable.getColumns().add(activityName);
        activityTable.getColumns().add(activityConsumption);
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        activityName.setPrefWidth(442.0);
        activityConsumption.setPrefWidth(215.0);

        for (Activity activity : activities) {
            activityTable.getItems().add(activity);
        }
    }
}
