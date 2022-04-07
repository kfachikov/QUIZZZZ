
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PrepScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /*
    The following field is required to store the `SinglePlayer` instance,
    consisting of the username entered on the `Main Screen` and a default score - 0.
     */
    private SinglePlayer singlePlayer;

    private SinglePlayerUtils singlePlayerUtils;

    @FXML
    private VBox leaderboard;

    /**
     * initializes PrepScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     * @param singlePlayerUtils is the shared single-player utility instance.
     */
    @Inject
    public PrepScreenCtrl(ServerUtils server, MainCtrl mainCtrl, SinglePlayerUtils singlePlayerUtils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.singlePlayerUtils = singlePlayerUtils;
    }

    /**
     * Calls fillLeaderboard(), which populates leaderboard + makes used labels visible.
     */
    public void setUp() {
        List<SinglePlayerLeaderboardScore> players = server.getLeaderboardEntries();
        fillLeaderboard(players);
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
        /*
        The single-player game logic utility should keep reference to the player and the corresponding game
        they are currently playing. Thus, they are passed as arguments here.
         */
        singlePlayerUtils.setSinglePlayerAttributes(server.startSinglePlayerGame(singlePlayer));
        mainCtrl.playSoloGame(singlePlayerUtils);
    }

    /**
     * Getter for the single-player instance create once the client has been redirected to the preparation screen.
     *
     * @return  SinglePlayer instance.
     */
    public SinglePlayer getSinglePlayer() {
        return singlePlayer;
    }

    /**
     * Setter for the player - would be used to store his username and a default score (0).
     *
     * @param singlePlayer a Player instance to set for the field singlePlayer.
     */
    public void setSinglePlayer(SinglePlayer singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /**
     * Populates the leaderboard.
     * <p>
     * Displays (setVisible(true)) the labels that are filled.
     *
     * @param entries the list of entries.
     */
    private void fillLeaderboard(List<SinglePlayerLeaderboardScore> entries) {
        List<SinglePlayerLeaderboardScore> sorted = new ArrayList<>(entries);
        Collections.sort(sorted);
        Collections.reverse(sorted);

        leaderboard.getChildren().clear();

        int position = 1;
        for (SinglePlayerLeaderboardScore entry : sorted) {
            GridPane gridPane = new GridPane();
            gridPane.setPrefWidth(leaderboard.getPrefWidth());

            gridPane.getColumnConstraints().addAll(
                    new ColumnConstraints(60),
                    new ColumnConstraints(135),
                    new ColumnConstraints(95)
            );

            Label positionLabel = new Label(String.valueOf(position++));
            positionLabel.setPrefWidth(60);
            gridPane.add(positionLabel, 0, 0);

            Label usernameLabel = new Label(entry.getUsername());
            usernameLabel.setPrefWidth(125);
            usernameLabel.setMaxWidth(125);
            gridPane.add(usernameLabel, 1, 0);

            Label scoreLabel = new Label(String.valueOf(entry.getScore()));
            scoreLabel.setPrefWidth(90);
            gridPane.add(scoreLabel, 2, 0);

            for (Node node : gridPane.getChildren()) {
                node.getStyleClass().add("label");
            }

            leaderboard.getChildren().add(gridPane);
        }
    }

}