package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.SinglePlayerLeaderboardScore;
import javafx.fxml.FXML;

import javax.swing.table.TableColumn;

public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final SinglePlayerLeaderboardScore username;
    private final SinglePlayerLeaderboardScore score;

    @FXML
    private TableColumn columnusername;

    @FXML
    private TableColumn columnscore;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl, SinglePlayerLeaderboardScore username, SinglePlayerLeaderboardScore score) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.username = username;
        this.score = score;
    }

    /**
     * shows the leaderboard content when arriving to this scene.
     */
    public void initialize() {
        showLeaderboard();
    }

    /**
     * sets the scene and title to home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * sets the scene and title to single-player game.
     */
    public void playSoloGame() {
        mainCtrl.showSoloGameQuestion();
    }

    /**
     * shows the top 10 players of the solo mode game.
     */
    public void showLeaderboard() {
        for (int i = 0; i <= 10; i++) {
            var player = "topplayer";
        }
    }
}
