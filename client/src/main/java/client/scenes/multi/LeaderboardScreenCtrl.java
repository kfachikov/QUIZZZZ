package client.scenes.multi;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.misc.Player;
import commons.multi.MultiPlayer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;

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
    private GridPane chatMessages;

    @FXML
    private HBox barChart;

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
        fillLeaderboard(players);
        if (("LEADERBOARD").equals(gameState)) {
            title.setText("INTERMEDIATE LEADERBOARD");

            //make top-left leave button visible + returnHome invisible
            playAgain.setVisible(false);
            fillBarChart(this.barChart, players);

            barChart.setVisible(true);
        }
        if (("GAME_OVER").equals(gameState)) {
            title.setText("GAME OVER!");

            //returnHome visible + make top-left leave button invisible
            playAgain.setVisible(true);
            playAgain.setText("PLAY AGAIN");
            playAgain.setOnAction(event -> playAgain());

            barChart.setVisible(false);
            HBox gameOverChart = new HBox();
            gameOverChart.setPadding(new Insets(5, 0, 0, 0));
            fillBarChart(gameOverChart, players);
            leaderboard.getChildren().add(gameOverChart);
        }
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

    /**
     * Populate the bar chart visible below the leaderboard.
     *
     * @param barChart Bar chart to fill.
     * @param players  Players in the game.
     */
    public void fillBarChart(HBox barChart, List<MultiPlayer> players) {
        List<MultiPlayer> sorted = new ArrayList<>(players);
        var comp = Comparator.comparing(Player::getScore);
        sorted.sort(comp);
        Collections.reverse(sorted);

        barChart.getChildren().clear();

        if (sorted.size() == 0) {
            return;
        }

        // These are deliberately doubles, to allow for easier division later
        double minScore = sorted.stream().min(comp).get().getScore();
        double maxScore = sorted.stream().max(comp).get().getScore();

        // For aesthetic purposes, we do scale the graph depending on scores differently.
        minScore *= 0.3;
        minScore -= 15;

        final double chartHeight = 121;
        final double chartWidth = 305 - sorted.size() * 5;
        barChart.setSpacing(5);

        for (MultiPlayer player : sorted) {
            Region bar = new Region();
            bar.getStyleClass().add("leaderboard-bar");

            double portion = (player.getScore() - minScore) / (maxScore - minScore);
            double height = chartHeight * portion;
            double width = chartWidth / sorted.size();
            bar.setPrefHeight(height);
            bar.setMaxHeight(height);
            bar.setMinHeight(height);

            bar.setMinWidth(0);
            bar.setPrefWidth(width);

            if (Objects.equals(player.getUsername(), multiCtrl.getUsername())) {
                bar.getStyleClass().add("highlighted");
            }

            barChart.getChildren().add(bar);
        }
    }
}
