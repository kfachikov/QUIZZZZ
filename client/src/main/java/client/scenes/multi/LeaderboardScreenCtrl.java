package client.scenes.multi;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.multi.MultiPlayer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Label Position1;
    @FXML
    private Label Username1;
    @FXML
    private Label Score1;

    @FXML
    private Label Position2;
    @FXML
    private Label Username2;
    @FXML
    private Label Score2;

    @FXML
    private Label Position3;
    @FXML
    private Label Username3;
    @FXML
    private Label Score3;

    @FXML
    private Label Position4;
    @FXML
    private Label Username4;
    @FXML
    private Label Score4;

    @FXML
    private Label Position5;
    @FXML
    private Label Username5;
    @FXML
    private Label Score5;

    @FXML
    private Label Position6;
    @FXML
    private Label Username6;
    @FXML
    private Label Score6;

    @FXML
    private Label UserPosition;
    @FXML
    private Label UserUsername;
    @FXML
    private Label UserScore;

    private List<Label> usernameLabels;
    private List<Label> scoreLabels;
    private List<Label> positionLabels;

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

        usernameLabels = new ArrayList<>();
        Username1 = new Label();
        usernameLabels.add(Username1);
        Username2 = new Label();
        usernameLabels.add(Username2);
        Username3 = new Label();
        usernameLabels.add(Username3);
        Username4 = new Label();
        usernameLabels.add(Username4);
        Username5 = new Label();
        usernameLabels.add(Username5);
        Username6 = new Label();
        usernameLabels.add(Username6);

        scoreLabels = new ArrayList<>();
        Score1 = new Label();
        scoreLabels.add(Score1);
        Score2 = new Label();
        scoreLabels.add(Score2);
        Score3 = new Label();
        scoreLabels.add(Score3);
        Score4 = new Label();
        scoreLabels.add(Score4);
        Score5 = new Label();
        scoreLabels.add(Score5);
        Score6 = new Label();
        scoreLabels.add(Score6);

        positionLabels = new ArrayList<>();
        Position1 = new Label();
        positionLabels.add(Position1);
        Position2 = new Label();
        positionLabels.add(Position2);
        Position3 = new Label();
        positionLabels.add(Position3);
        Position4 = new Label();
        positionLabels.add(Position4);
        Position5 = new Label();
        positionLabels.add(Position5);
        Position6 = new Label();
        positionLabels.add(Position6);

        UserPosition = new Label();
        UserUsername = new Label();
        UserScore = new Label();
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

        positionLabels.forEach(positionLabel -> positionLabel.setVisible(false));
        positionLabels.forEach(positionLabel -> System.out.println(positionLabel+" is "+positionLabel.isVisible()));
        usernameLabels.forEach(usernameLabel -> usernameLabel.setVisible(false));
        scoreLabels.forEach(scoreLabel -> scoreLabel.setVisible(false));
        line.setVisible(true);

        fillLeaderboard(players);
    }

    private void fillLeaderboard(List<MultiPlayer> players) {
        boolean userFound = false;
        for (int i = 0; i < players.size(); i++) {
            MultiPlayer player = players.get(i);
            String username = player.getUsername();
            String score = Integer.toString(player.getScore());

            if (username.equals(multiCtrl.getUsername())) {
                userFound = true;
            }

            usernameLabels.get(i).setText(username);
            Username1.setText(username);
            usernameLabels.get(i).setVisible(true);

            scoreLabels.get(i).setText(score);
            Score1.setText(score);
            scoreLabels.get(i).setVisible(true);
        }
        // if the player is outside top-6
        if (!userFound) {
            displayUserStats(players);
            line.setVisible(true);
        }
    }

    public void displayUserStats(List<MultiPlayer> players) {
        UserUsername.setVisible(true);
        UserPosition.setVisible(true);
        UserScore.setVisible(true);

        MultiPlayer user = null;

        for (int i = 0; i < players.size(); i++) {
            if (multiCtrl.getUsername().equals(players.get(i).getUsername())) {
                user = players.get(i);
            }
        }
        UserUsername.setText(multiCtrl.getUsername());
        UserScore.setText(Integer.toString(user.getScore()));
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

        positionLabels.forEach(positionLabel -> positionLabel.setVisible(false));
        usernameLabels.forEach(usernameLabel -> usernameLabel.setVisible(false));
        scoreLabels.forEach(scoreLabel -> scoreLabel.setVisible(false));
        line.setVisible(true);

        fillLeaderboard(players);
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
