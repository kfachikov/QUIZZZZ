package client.scenes.single;

import javafx.scene.layout.AnchorPane;

public interface QuestionScreen {

    public void setScore(long score);

    public AnchorPane getWindow();

    public boolean compareAnswer();
}
