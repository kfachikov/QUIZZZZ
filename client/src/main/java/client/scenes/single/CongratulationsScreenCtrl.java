package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

/**
 *
 */
public class CongratulationsScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /*
    The following field contains the game state and the player instance of the game just finished.
     */
    private SinglePlayerUtils singlePlayerUtils;

    @FXML
    private Text position;

    @FXML
    private Text points;

    @FXML
    private Circle question1;

    @FXML
    private Circle question2;

    @FXML
    private Circle question3;

    @FXML
    private Circle question4;

    @FXML
    private Circle question5;

    @FXML
    private Circle question6;

    @FXML
    private Circle question7;

    @FXML
    private Circle question8;

    @FXML
    private Circle question9;

    @FXML
    private Circle question10;

    @FXML
    private Circle question11;

    @FXML
    private Circle question12;

    @FXML
    private Circle question13;

    @FXML
    private Circle question14;

    @FXML
    private Circle question15;

    @FXML
    private Circle question16;

    @FXML
    private Circle question17;

    @FXML
    private Circle question18;

    @FXML
    private Circle question19;

    @FXML
    private Circle question20;

    /**
     * initializes CongratulationsScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     * @param singlePlayerUtils is the shared single-player utility instance
     */
    @Inject
    public CongratulationsScreenCtrl(ServerUtils server, MainCtrl mainCtrl, SinglePlayerUtils singlePlayerUtils) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.singlePlayerUtils = singlePlayerUtils;
    }

    /*
    I believe the following method is useless for now. Perhaps once the questionColor()
    and showPosition() functionality is implemented.

    In addition, this is a method to initialize the functionality of the scene, so the functionality of
    each circle to be clicked would be implemented here.
     */
    /**
     * shows correct/incorrect answers, the points the user scored and the position the user acquired immediately when
     * arriving to this scene.
     */
    public void initialize() {
        /*
        Actions handlers on circular "buttons" should be established here.
         */
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * Establishes a new game on the server.
     */
    public void playSoloGame() {
        /*
        The player will begin the new game with the same username, but his points should be set
        to the default value - 0;
         */
        SinglePlayer newPlayer = new SinglePlayer(
                singlePlayerUtils.getSinglePlayerState().getPlayer().getUsername(), 0);
        singlePlayerUtils.setSinglePlayerAttributes(server.startSinglePlayerGame(newPlayer));
        mainCtrl.playSoloGame(singlePlayerUtils);
    }

    /*
    The following method would be implemented later, as it would require some additional set up in the MainCtrl.
     */
    /**
     * redirects the user to the answer corresponding to the button clicked by the user.
     */
    public void revealAnswer() {
        var id = -1;
        for (int i = 0; i <= 20; i++) {
            if (i == id) {
                //mainCtrl.showSoloGameQuestion(singlePlayer, server.startSinglePlayerGame(singlePlayer));
            }
        }
    }

    /*
    The following method would not be working as a "constant" boolean value is used - namely `correct`.
     */
    /**
     * reveals if the answers picked by the user were correct or incorrect.
     */
    public void questionColor() {
        var correct = false;
        var id = -1;
        for (int i = 0; i <= 20; i++) {
            if (i == id && correct) {
                question1.setStyle("-fx-control-inner-background: #" +
                        (Paint.valueOf("#65cf77")).toString().substring(2));
            }
            if (i == id && !correct) {
                question1.setStyle("-fx-control-inner-background: #" +
                        (Paint.valueOf("#ff4400")).toString().substring(2));
            }
        }
    }

    /**
     * Sets the score and position of the player and post the player to the leaderboard database.
     */
    public void setPoints() {
        String name = singlePlayerUtils.getSinglePlayerState().getPlayer().getUsername();
        int score = singlePlayerUtils.getSinglePlayerState().getPlayer().getScore();
        SinglePlayerLeaderboardScore player = new SinglePlayerLeaderboardScore(name, score);

        points.setText(String.valueOf(score));
        server.postLeaderboardEntry(player);

        var place = 1;

        List<SinglePlayerLeaderboardScore> leaderboardScores = server.getLeaderboardEntries();
        while (leaderboardScores.get(place - 1).getScore() > score) {
            place ++;
        }
        position.setText("" + place + "");
    }
}
