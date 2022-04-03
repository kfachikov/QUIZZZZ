package client.scenes.misc;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 */
public class HelpScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private Button next;

    @FXML
    private Button goBack;

    @FXML
    private Text title;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    /**
     * initializes HelpScreenCtrl by connecting it to backend and frontend mainCtrl.
     *
     * @param server   is the server variable
     * @param mainCtrl is the main controller variable
     */
    @Inject
    public HelpScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * sets title and scene to Home.
     */
    public void returnHome() {
        mainCtrl.showHome();
    }

    /**
     * sets the first scene.
     */
    public void setScene1() {
        //set texts
        title.setText("SOLO");
        text1.setText("Do you want to test your knowledge on daily energy consumptions?");
        text2.setText("Play a SOLO game and beat the best score to " +
                        "display your username on the top of the all time leaderboard!");
        text2.setLayoutY(346.0);
        text3.setText("Answer with speed and accuracy to get the most points! Each question has only 8 seconds!");
        //set circles
        circle2.setFill(Paint.valueOf("ffffff"));
        circle3.setFill(Paint.valueOf("ffffff"));
        //set buttons
        goBack.setOnAction(event -> mainCtrl.showHome());
        next.setText("NEXT");
        next.setOnAction(event -> setScene2());
    }

    /**
     * sets the second scene.
     */
    public void setScene2() {
        //set texts
        title.setText("MULTIPLAYER");
        text1.setText("Do you want to compare your knowledge to your friends?");
        text2.setText("Join a lobby altogether and see who is the most knowledgeable!");
        text3.setText("Send instantaneous reactions during the game for everyone to see! User jokers for extra fun!");
        text2.setLayoutY(346.0);
        //set circles
        circle2.setFill(Paint.valueOf("b50000"));
        circle3.setFill(Paint.valueOf("ffffff"));
        //set buttons
        goBack.setOnAction(event -> setScene1());
        next.setText("NEXT");
        next.setOnAction(event -> setScene3());
    }

    /**
     * sets the third scene.
     */
    public void setScene3() {
        //set texts
        title.setText("JOKER CARDS");
        text1.setText("DOUBLE POINTS — " +
                "Double the points for that question. (Use before time runs out)");
        text2.setText("REMOVE INCORRECT — " +
                "Remove an incorrect answer from answer choices. (Disabled in guess questions)");
        text3.setText("SHORTEN TIME — " +
                "Put opponents into rush by decreasing their question time.");
        text2.setLayoutY(364.0);
        //set circles
        circle2.setFill(Paint.valueOf("b50000"));
        circle3.setFill(Paint.valueOf("b50000"));
        //set buttons
        goBack.setOnAction(event -> setScene2());
        next.setText("HOME");
        next.setOnAction(event -> {
            //set scene as home
            mainCtrl.showHome();
            //prep home screen to be ready as default
            setScene1();
        });
    }
}
