package commons;

import java.util.List;
import java.util.Objects;

public class SinglePlayerState extends GameState {

    public static final String QUESTION_STATE = "QUESTION";
    public static final String TRANSITION_STATE = "TRANSITION";
    public static final String GAME_OVER_STATE = "GAME_OVER";

    SinglePlayer player;

    public SinglePlayerState(long id, double nextPhase, int roundNumber, List<AbstractQuestion> questionList, List<String> submittedAnswers, List<Activity> activityList, String state, SinglePlayer player) {
        super(id, nextPhase, roundNumber, questionList, submittedAnswers, activityList, state);
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
