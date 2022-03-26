package client.scenes.multi;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.multi.MultiPlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * The scene right now in default is intermediate leaderboard design.
 */
public class LeaderboardScreenCtrl {
    private ServerUtils server;
    private MultiplayerCtrl multiCtrl;

    @FXML
    private Button angry;

    @FXML
    private Button crying;

    @FXML
    private Button laughing;

    @FXML
    private Button surprised;

    private List<Button> emojis;

    @FXML
    private Text title;

    @FXML
    private Button leave;

    @FXML
    private Button returnHome;

    @FXML
    private Line line;

    /**
     * initializes IntermediateLeaderboardScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param multiCtrl is the multiplayer controller variable
     */
    @Inject
    public LeaderboardScreenCtrl(ServerUtils server, MultiplayerCtrl multiCtrl) {
        this.multiCtrl = multiCtrl;
        this.server = server;

        emojis = new ArrayList<>();
        emojis.add(angry);
        emojis.add(crying);
        emojis.add(laughing);
        emojis.add(surprised);
    }

    /**
     * Prepares the leaderboard screen to be intermediate leaderboard.
     * Sets the title.
     * Changes the places of the emojis so that the right-hand side of the screen is properly divided.
     * Disables and invisible-s the playAgain button.
     * @param players
     */
    public void turnIntermediate(List<MultiPlayer> players) {
        title.setText("Quizzz: INTERMEDIATE LEADERBOARD");

        laughing.setLayoutX(359);
        laughing.setLayoutY(80);

        crying.setLayoutX(474);
        crying.setLayoutY(80);

        surprised.setLayoutX(359);
        surprised.setLayoutY(250);

        angry.setLayoutX(474);
        angry.setLayoutY(250);

        returnHome.setVisible(false);
        returnHome.setDisable(true);

        leave.setVisible(true);
        leave.setDisable(false);

        fillLeaderboard(players);
    }

    private void fillLeaderboard(List<MultiPlayer> players) {
        boolean userFound = false;
        for (int i = 0; i < 6; i++) {

        }
        if (!userFound) {

        }
    }

    /**
     * Prepares the leaderboard screen to be game over screen.
     * Sets the title.
     * Pushes the emojis so that the right-hand side of the screen is properly divided.
     * Disables and invisible-s the playAgain button.
     * @param players
     */
    public void turnFinal(List<MultiPlayer> players) {
        title.setText("Quizzz: GAME OVER!");

        laughing.setLayoutX(359);
        laughing.setLayoutY(61);

        crying.setLayoutX(474);
        crying.setLayoutY(61);

        surprised.setLayoutX(359);
        surprised.setLayoutY(175);

        angry.setLayoutX(474);
        angry.setLayoutY(175);

        returnHome.setVisible(true);
        returnHome.setDisable(false);

        leave.setDisable(true);
        leave.setVisible(false);
    }

    /**
     * sets the scene and title to home if the yes button is clicked.
     * tied to leave button at intermediate leaderboard screen.
     */
    public void leave() {
        multiCtrl.leave();
    }

    /**
     * returns to the home screen.
     * is tied to returnHome button displayed at game-over screen.
     */
    public void returnHome() {
        multiCtrl.returnHome();
    }

    /**
     * activates when a player presses angry emoji.
     */
    public void angryEmoji() {
        multiCtrl.angryEmoji();
    }

    /**
     * activates when a player presses crying emoji.
     */
    public void cryingEmoji() {
        multiCtrl.cryingEmoji();
    }

    /**
     * activates when a player presses laughing emoji
     */
    public void laughingEmoji() {
        multiCtrl.laughingEmoji();
    }

    /**
     * activates when a player presses surprised emoji
     */
    public void surprisedEmoji() {
        multiCtrl.surprisedEmoji();
    }
}
