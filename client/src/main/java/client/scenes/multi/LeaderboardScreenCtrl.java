package client.scenes.multi;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.multi.MultiPlayer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

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
    private VBox leaderboard;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane chatMessages;

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
        multiCtrl.initializeEmojiButtons(emojiButton1, emojiButton2, emojiButton3, emojiButton4);
        scrollPane = new ScrollPane();
    }

    /**
     * Is called by multiCtrl.showIntermediateGameOver();.
     * <p>
     * Sets the scene to fit intermediate leaderboard/game over.
     * <p>
     * Sets the leave / returnHome button depending on the usage of the scene
     * <p>
     * Calls fillLeaderboard(), which populates leaderboard + makes used labels visible.
     *
     * @param players   the list of players in the game, in descending score order.
     * @param gameState the state of the game, is either LEADERBOARD or GAME_OVER.
     */
    public void setScene(List<MultiPlayer> players, String gameState) {
        if (("LEADERBOARD").equals(gameState)) {
            title.setText("INTERMEDIATE LEADERBOARD");

            //make top-left leave button visible + returnHome invisible
            playAgain.setVisible(false);
        }
        if (("GAME_OVER").equals(gameState)) {
            title.setText("GAME OVER!");

            //returnHome visible + make top-left leave button invisible
            playAgain.setVisible(true);
            playAgain.setText("PLAY AGAIN");
            playAgain.setOnAction(event -> playAgain());
        }
        fillLeaderboard(players);
    }

    /**
     * Populates the leaderboard.
     * <p>
     * Displays the labels that are filled.
     *
     * @param players the list of players in the game, in descending score order.
     */
    private void fillLeaderboard(List<MultiPlayer> players) {
        leaderboard.getChildren().clear();
        int position = 1;
        for (MultiPlayer entry : players) {
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
                if (Objects.equals(entry.getUsername(), multiCtrl.getUsername())) {
                    node.getStyleClass().add("highlighted");
                }
            }

            leaderboard.getChildren().add(gridPane);
        }
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
     * Getter for the reaction section on the leaderboard screen.
     *
     * @return GridPane reference to the particular instance on the leaderboard scene.
     */
    public GridPane getChatMessages() {
        return chatMessages;
    }

    public void showBarChart(List<MultiPlayer> players) {
        leaderboard.getParent().setVisible(false);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Player");
        yAxis.setLabel("Score");
        barChart.setLayoutX(104.0);
        barChart.setLayoutY(103.0);
        barChart.setPrefWidth(305.0);
        barChart.setPrefHeight(371.0);

        XYChart.Series entries = new XYChart.Series();
        for (int i = 0; i < players.size(); i++) {
            entries.getData().add(new XYChart.Data(players.get(i).getUsername(), players.get(i).getScore()));
        }
        barChart.getData().add(entries);
        barChart.toFront();
    }
}
