package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.services.SingleplayerGameStatePollingService;
import client.utils.ActivityImageUtils;
import client.utils.PromptLeaveScreen;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.misc.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 * Parent class used for the shared functionality of the different question screen
 * controllers.
 */
public abstract class QuestionScreen implements PromptLeaveScreen {

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
     * Sets the scene and title to home if the yes button is clicked.
     */
    public void returnHome() {
        promptLeave();
    }

    /**
     * Clean up when leaving a screen.
     * <p>
     * Should handle the switching of the screen too.
     */
    @Override
    public void onLeave() {
        singlePlayerUtils.stopPollingService();
        mainCtrl.showHome();
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
