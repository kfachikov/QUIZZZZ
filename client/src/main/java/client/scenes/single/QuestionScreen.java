package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.SingleplayerGameStatePollingService;
import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.misc.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

/**
 * Parent class used for the shared functionality of the different question screen
 * controllers.
 */
public abstract class QuestionScreen {

    /*
    Utils class instance, which would contain the whole single-player game logic.
    In addition, it would hold the single-player and the game state instances of the current game.
     */
    public final SinglePlayerUtils singlePlayerUtils;

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    final ActivityImageUtils activityImageUtils;


    @FXML
    private Button firstAnswer;

    @FXML
    private Button secondAnswer;

    @FXML
    private Button thirdAnswer;


    /**
     * initializes MoreExpensiveQuestionScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server             is the server variable
     * @param mainCtrl           is the main controller variable
     * @param pollingService     is the shared single-player game polling service
     * @param activityImageUtils is the utilities class responsible for fetching an image of an activity.
     * @param singlePlayerUtils  is the shared single-player utility instance
     */
    @Inject
    public QuestionScreen(ServerUtils server,
                          MainCtrl mainCtrl,
                          SingleplayerGameStatePollingService pollingService,
                          ActivityImageUtils activityImageUtils,
                          SinglePlayerUtils singlePlayerUtils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.activityImageUtils = activityImageUtils;
        this.singlePlayerUtils = singlePlayerUtils;
    }

    /**
     * Sets the score of the current scene to the actual player's score.
     *
     * @param score Actual player's score calculated after answer submitted
     *              is compared.
     */
    public abstract void setScore(long score);

    /**
     * Getter for the window objects - used for changing the background.
     *
     * @return A reference to the particular AnchorPane object.
     */
    public abstract AnchorPane getWindow();


    /**
     * sets the scene and title to home if the yes button is clicked.
     */
    public void returnHome() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Leave the game");
        alert.setContentText("Are you sure you want to leave the game?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.get() == yesButton) {
            singlePlayerUtils.stopPollingService();
            mainCtrl.showHome();
            firstAnswer.setDisable(false);
            secondAnswer.setDisable(false);
            thirdAnswer.setDisable(false);
        }

    }

    /**
     * Declare getTime() method existence in subclasses.
     *
     * @return ProgressBar instance of the particular one in the desired question controller.
     */
    public abstract ProgressBar getTime();

    /**
     * Getter for the utility instance.
     *
     * @return SinglePlayerUtils instance consisting of information about current game state and current player.
     */
    public SinglePlayerUtils getSinglePlayerUtils() {
        return singlePlayerUtils;
    }

    /**
     * Getter method for getting the image of an activity.
     *
     * @param activity Activity to get an image from.
     * @return JavaFX image of the activity.
     */
    public Image getActivityImage(Activity activity) {
        long key = activity.getKey();

        return activityImageUtils.getActivityImage(key);
    }

}
