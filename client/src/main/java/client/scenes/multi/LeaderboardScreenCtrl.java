package client.scenes.multi;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.multi.MultiPlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    private Button emojiButton1;
    @FXML
    private Button emojiButton2;
    @FXML
    private Button emojiButton3;
    @FXML
    private Button emojiButton4;

    @FXML
    private Text title;
    @FXML
    private Button leave;
    @FXML
    private Button playAgain;

    @FXML
    private Label position1;
    @FXML
    private Label username1;
    @FXML
    private Label score1;

    @FXML
    private Label position2;
    @FXML
    private Label username2;
    @FXML
    private Label score2;

    @FXML
    private Label position3;
    @FXML
    private Label username3;
    @FXML
    private Label score3;

    @FXML
    private Label position4;
    @FXML
    private Label username4;
    @FXML
    private Label score4;

    @FXML
    private Label position5;
    @FXML
    private Label username5;
    @FXML
    private Label score5;

    @FXML
    private Label position6;
    @FXML
    private Label username6;
    @FXML
    private Label score6;

    @FXML
    private Label userPosition;
    @FXML
    private Label userUsername;
    @FXML
    private Label userScore;
    @FXML
    private Line line;

    @FXML
    private List<Label> usernameLabels;
    @FXML
    private List<Label> scoreLabels;
    @FXML
    private List<Label> positionLabels;
    @FXML
    private List<Label> userLabels;

    @FXML
    private GridPane reactions;

    /**
     * initializes IntermediateLeaderboardScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server    is the server variable.
     * @param multiCtrl is the multiplayer controller variable
     */
    @Inject
    public LeaderboardScreenCtrl(ServerUtils server, MultiplayerCtrl multiCtrl) {
        this.multiCtrl = multiCtrl;
        this.server = server;
    }

    /**
     * initializes the javafx components.
     * <p>
     * initializes and populates the arrayLists of labels/buttons.
     */
    @FXML
    protected void initialize() {
        usernameLabels = new ArrayList<>();
        fillList(usernameLabels, username1, username2, username3, username4, username5, username6);

        scoreLabels = new ArrayList<>();
        fillList(scoreLabels, score1, score2, score3, score4, score5, score6);

        positionLabels = new ArrayList<>();
        fillList(positionLabels, position1, position2, position3, position4, position5, position6);

        userLabels = new ArrayList<>();
        userLabels.add(userUsername);
        userLabels.add(userScore);
        userLabels.add(userPosition);

        multiCtrl.initializeEmojiButtons(emojiButton1, emojiButton2, emojiButton2, emojiButton4);
    }

    /**
     * Is called by multiCtrl.showIntermediateGameOver();.
     * <p>
     * Sets the scene to fit intermediate leaderboard/game over.
     * <p>
     * Sets the layout of emoji buttons depending on the usage of the scene
     * <p>
     * Sets the leave / returnHome button depending on the usage of the scene
     * <p>
     * Makes all username & score labels invisible, so that they can be set visible when filled
     * <p>
     * Calls fillLeaderboard(), which populates leaderboard + makes used labels visible.
     *
     * @param players   the list of players in the game, in descending score order.
     * @param gameState the state of the game, is either LEADERBOARD or GAME_OVER.
     */
    public void setScene(List<MultiPlayer> players, String gameState) {
        if (("LEADERBOARD").equals(gameState)) {
            title.setText("INTERMEDIATE LEADERBOARD");
            //emojiIntermediateLayout();

            //make top-left leave button visible + returnHome invisible
            playAgain.setVisible(false);
            playAgain.setDisable(true);
            leave.setDisable(false);
            leave.setVisible(true);
        }
        if (("GAME_OVER").equals(gameState)) {
            title.setText("GAME OVER!");
            //emojiGameOverLayout();

            //returnHome visible + make top-left leave button invisible
            playAgain.setVisible(true);
            playAgain.setDisable(false);
            leave.setDisable(true);
            leave.setVisible(false);
        }
        //set username & score labels invisible
        setLabelsVisible(usernameLabels, false);
        setLabelsVisible(scoreLabels, false);
        setLabelsVisible(positionLabels, true);
        setLabelsVisible(userLabels, false);
        line.setVisible(false);

        fillLeaderboard(players);
    }

    /**
     * Populates the leaderboard.
     * <p>
     * Displays (setVisible(true)) the labels that are filled.
     *
     * @param players    the list of players in the game, in descending score order.
     */
    private void fillLeaderboard(List<MultiPlayer> players) {
        //keep track of whether the user is shown in leaderboard.
        boolean userFound = false;
        if (players.size() <= 6) {
            //iterate from sorted players list and set username & score labels.
            for (int i = 0; i < players.size(); i++) {
                MultiPlayer player = players.get(i);
                String username = player.getUsername();
                String score = Integer.toString(player.getScore());
                //set the labels as visible and display the value.
                displayLabel(usernameLabels.get(i), username);
                displayLabel(scoreLabels.get(i), score);
            }
        } else {
            //iterate from sorted players list and set username & score labels.
            for (int i = 0; i < 6; i++) {
                MultiPlayer player = players.get(i);
                String username = player.getUsername();
                String score = Integer.toString(player.getScore());
                //set the labels as visible and display the value.
                displayLabel(usernameLabels.get(i), username);
                displayLabel(scoreLabels.get(i), score);
                //check if the player is displayed (in top-6).
                if (username.equals(multiCtrl.getUsername())) {
                    userFound = true;
                }
            }
            //if the player is outside top-6.
            if (!userFound) {
                displayUserStats(players);
            }
        }
    }

    /**
     * Sets the text of the provided label as provided text.
     * <p>
     * Makes the label visible.
     *
     * @param label    the label to set text of.
     * @param text the text to se the label with.
     */
    public void displayLabel(Label label, String text) {
        label.setText(text);
        label.setVisible(true);
    }

    /**
     * Sets the user labels (yellow ones at the bottom).
     * <p>
     * Makes the user labels visible.
     * Makes the line (pure design element) visible.
     *
     * @param players    the list of players in the game, in descending score order.
     */
    public void displayUserStats(List<MultiPlayer> players) {
        setLabelsVisible(userLabels, true);
        line.setVisible(true);
        MultiPlayer user = null;
        for (int i = 0; i < players.size(); i++) {
            if (multiCtrl.getUsername().equals(players.get(i).getUsername())) {
                user = players.get(i);
            }
        }
        displayLabel(userUsername, multiCtrl.getUsername());
        displayLabel(userScore, Integer.toString(user.getScore()));
    }

    /**
     * Sets all labels in the labelList invisible.
     *
     * @param labelList the list of labels.
     * @param value the boolean value to pass to setVisible().
     */
    public void setLabelsVisible(List<Label> labelList, boolean value) {
        labelList.forEach(label -> label.setVisible(value));
    }

    /**
     * Sets the emoji layout to be displayed in intermediate leaderboard screen.
     */
    public void emojiIntermediateLayout() {
        emojiButton3.setLayoutX(359);
        emojiButton3.setLayoutY(80);
        emojiButton2.setLayoutX(474);
        emojiButton2.setLayoutY(80);
        emojiButton4.setLayoutX(359);
        emojiButton4.setLayoutY(250);
        emojiButton1.setLayoutX(474);
        emojiButton1.setLayoutY(250);
    }

    /**
     * Sets the emoji layout to be displayed in game over screen.
     */
    public void emojiGameOverLayout() {
        emojiButton3.setLayoutX(359);
        emojiButton3.setLayoutY(61);
        emojiButton2.setLayoutX(474);
        emojiButton2.setLayoutY(61);
        emojiButton4.setLayoutX(359);
        emojiButton4.setLayoutY(175);
        emojiButton1.setLayoutX(474);
        emojiButton1.setLayoutY(175);
    }

    /**
     * Sets the scene and title to home if the yes button is clicked.
     * <p>
     * Tied to leave button at intermediate leaderboard screen.
     */
    public void leave() {
        multiCtrl.promptLeave();
    }

    /**
     * Establishes a new queue connection for our client.
     * If his username already exist if the present queue state, they
     * are redirected to the home screen and prompted to change their username.
     * <p>
     * Is tied to playAgain button displayed at game-over screen.
     */
    public void playAgain() {
        multiCtrl.enterNewQueue();
    }

    /**
     * Fills the passed list of labels with passed labels.
     *
     * @param objectList the labelList to be passed.
     * @param a          label1.
     * @param b          label2.
     * @param c          label3.
     * @param d          label4.
     * @param e          label5.
     * @param f          label6.
     */
    public void fillList(List<Label> objectList, Label a, Label b, Label c, Label d, Label e, Label f) {
        objectList.add(a);
        objectList.add(b);
        objectList.add(c);
        objectList.add(d);
        objectList.add(e);
        objectList.add(f);
    }

    /**
     * @return List of username labels.
     */
    public List<Label> getUsernameLabels() {
        return usernameLabels;
    }

    /**
     * @return List of score labels.
     */
    public List<Label> getScoreLabels() {
        return scoreLabels;
    }

    /**
     * @return List of position labels.
     */
    public List<Label> getPositionLabels() {
        return positionLabels;
    }

    /**
     * @return List of labels used when user is !inTop-6.
     */
    public List<Label> getUserLabels() {
        return userLabels;
    }

    /**
     * Getter for the reaction section on the leaderboard screen.
     *
     * @return  GridPane reference to the particular instance on the leaderboard scene.
     */
    public GridPane getReactions() {
        return reactions;
    }
}
