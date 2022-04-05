
package client.scenes.single;

import client.scenes.misc.MainCtrl;
import client.utils.ServerUtils;
import client.utils.SinglePlayerUtils;
import com.google.inject.Inject;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerLeaderboardScore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
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
    private Label position7;
    @FXML
    private Label username7;
    @FXML
    private Label score7;

    @FXML
    private Label position8;
    @FXML
    private Label username8;
    @FXML
    private Label score8;

    @FXML
    private Label position9;
    @FXML
    private Label username9;
    @FXML
    private Label score9;

    @FXML
    private Label position10;
    @FXML
    private Label username10;
    @FXML
    private Label score10;

    @FXML
    private List<Label> usernameLabels;
    @FXML
    private List<Label> scoreLabels;
    @FXML
    private List<Label> positionLabels;

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
     * initializes the javafx components.
     * <p>
     * initializes and populates the arrayLists of labels/buttons.
     */
    @FXML
    protected void initialize() {
        usernameLabels = new ArrayList<>();
        fillList(usernameLabels, username1, username2, username3, username4, username5, username6, username7, username8,
                username9, username10);

        scoreLabels = new ArrayList<>();
        fillList(scoreLabels, score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);

        positionLabels = new ArrayList<>();
        fillList(positionLabels, position1, position2, position3, position4, position5, position6, position7, position8,
                position9, position10);
    }

    /**
     * Calls fillLeaderboard(), which populates leaderboard + makes used labels visible.
     */
    public void setUp() {
        setLabelsVisible(usernameLabels, false);
        setLabelsVisible(scoreLabels, false);
        setLabelsVisible(positionLabels, true);

        List<SinglePlayerLeaderboardScore> players = server.getLeaderboardEntry();
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
     * Fills the passed list of labels with passed labels.
     *
     * @param objectList the labelList to be passed.
     * @param a          label1.
     * @param b          label2.
     * @param c          label3.
     * @param d          label4.
     * @param e          label5.
     * @param f          label6.
     * @param g          label7.
     * @param h          label8.
     * @param i          label9.
     * @param j          label10.
     */
    public void fillList(List<Label> objectList, Label a, Label b, Label c, Label d, Label e, Label f, Label g, Label h,
                         Label i, Label j) {
        objectList.add(a);
        objectList.add(b);
        objectList.add(c);
        objectList.add(d);
        objectList.add(e);
        objectList.add(f);
        objectList.add(g);
        objectList.add(h);
        objectList.add(i);
        objectList.add(j);
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
     * @param players    the list of players in the game, in descending score order.
     */
    private void fillLeaderboard(List<SinglePlayerLeaderboardScore> players) {
        if (players.size() <= 10) {
            //iterate from sorted players list and set username & score labels.
            for (int i = 0; i < players.size(); i++) {
                SinglePlayerLeaderboardScore player = players.get(i);
                String username = player.getUsername();
                String score = Integer.toString(player.getScore());
                //set the labels as visible and display the value.
                displayLabel(usernameLabels.get(i), username);
                displayLabel(scoreLabels.get(i), score);
                displayLabel(positionLabels.get(i), String.valueOf(i + 1));
            }
        } else {
            //iterate from sorted players list and set username & score labels.
            for (int i = 0; i < 10; i++) {
                SinglePlayerLeaderboardScore player = players.get(i);
                String username = player.getUsername();
                String score = Integer.toString(player.getScore());
                //set the labels as visible and display the value.
                displayLabel(usernameLabels.get(i), username);
                displayLabel(scoreLabels.get(i), score);
                displayLabel(positionLabels.get(i), String.valueOf(i + 1));
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
     * Sets all labels in the labelList invisible.
     *
     * @param labelList the list of labels.
     * @param value the boolean value to pass to setVisible().
     */
    public void setLabelsVisible(List<Label> labelList, boolean value) {
        labelList.forEach(label -> label.setVisible(value));
    }
}