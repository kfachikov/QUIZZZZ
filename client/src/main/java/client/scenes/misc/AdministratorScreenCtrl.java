package client.scenes.misc;

import client.services.ActivityLoaderService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import commons.misc.Activity;
import jakarta.ws.rs.BadRequestException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.LongStringConverter;

import java.io.File;
import java.util.List;

/**
 * The controller of the Admin Panel screen.
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
    private TableView<Activity> activityTable;

    @FXML
    private CheckBox showImages;

    @FXML
    private Button deleteButton;

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
        if (activityTable.getColumns().size() == 0) {
            setTable();
        }
        fillTable();
        //sets the delete button
        deleteButton.setOnAction(event -> {
            Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
            activityTable.getItems().remove(selectedActivity);
            server.removeActivity(selectedActivity.getKey());
        });
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
     * Further calls the fillTable() method if import is successful.
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
            fillTable();

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

    /**
     * Fills the table with up-to-date list of activities.
     */
    public void fillTable() {
        //delete already existing items
        activityTable.getItems().removeAll();
        //get the up-to-date list of activities from server
        List<Activity> activities = server.getActivities();
        for (Activity activity : activities) {
            activityTable.getItems().add(activity);
        }
    }

    /**
     * Sets the table's columns, sizing and server communication in case of value update.
     */
    public void setTable() {
        TableColumn<Activity, String> activityName = new TableColumn<>("title");
        activityName.setCellValueFactory(new PropertyValueFactory<>("title"));
        activityName.setCellFactory(TextFieldTableCell.forTableColumn());
        activityName.setOnEditCommit(event -> {
            Activity activity = event.getRowValue();
            String oldTitle = activity.getTitle();
            activity.setTitle(event.getNewValue());
            server.changeActivity(activity.getKey(), activity);
            description.setText("You named '" + oldTitle + "' as '" + event.getNewValue() + "'.");
        });
        //create consumption column, make it editable and send server if changed
        TableColumn<Activity, Long> activityConsumption = new TableColumn<>("consumption (wh)");
        activityConsumption.setCellValueFactory(new PropertyValueFactory<>("consumption"));
        activityConsumption.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        activityConsumption.setOnEditCommit(event -> {
            Activity activity = event.getRowValue();
            activity.setConsumption(event.getNewValue());
            server.changeActivity(activity.getKey(), activity);
            description.setText(
                    "You set the consumption of '" + activity.getTitle() + "' as " + activity.getConsumption() + "wh.");
        });
        //add columns to the table
        var columns = activityTable.getColumns();
        columns.add(activityName);
        columns.add(activityConsumption);
        activityTable.setEditable(true);
        //sizing
        activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Creates the image column and adds/removes to the table.
     */
    public void addImageColumn() {
        TableColumn<Activity, Button> activityImage = new TableColumn<>("image");
        activityImage.setCellValueFactory(param -> {
            Activity activity = param.getValue();
            long key = activity.getKey();
            Image image = activityImageUtils.getActivityImage(key);
            ImageView imageView = new ImageView(image);
            Button button = new Button();
            button.setGraphic(imageView);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            //button clicked to change image
            button.setOnAction(event -> {
                System.out.println("Image clicked.");
                Image newImage = activityImageUtils.selectImageFile(activity.getKey());
                imageView.setImage(newImage);
            });
            return new SimpleObjectProperty<>(button);
        });
        if (showImages.isSelected()) {
            activityTable.getColumns().add(activityImage);
        } else {
            activityTable.getColumns().clear();
            activityTable.refresh();
            setTable();
            fillTable();
        }
    }
}
