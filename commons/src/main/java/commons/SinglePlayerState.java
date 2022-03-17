package commons;

import java.util.List;
import java.util.Objects;

public class SinglePlayerState extends GameState {

    SinglePlayer player;

    public SinglePlayerState(double timeLeft, int roundNumber, List<AbstractQuestion> questionList, List<String> submittedAnswers, List<Activity> activityList, String state, SinglePlayer player) {
        super(timeLeft, roundNumber, questionList, submittedAnswers, activityList, state);
        this.player = player;
    }

    public SinglePlayer getPlayer() {
        return player;
    }

    public void setPlayer(SinglePlayer player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SinglePlayerState that = (SinglePlayerState) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}
