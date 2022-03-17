package commons;

import java.util.List;

public abstract class GameState {

    private final long id;
    private long nextPhase;
    private int roundNumber;
    private List<AbstractQuestion> questionList;
    private List<Response> submittedAnswers;
    private List<Activity> activityList;
    /**
     * The state attribute is a String from : transition, intermittent leaderboard, question, game over.
     */
    private String state;

    public GameState(long id, long nextPhase, int roundNumber, List<AbstractQuestion> questionList, List<Response> submittedAnswers, List<Activity> activityList, String state) {
        this.id = id;
        this.nextPhase = nextPhase;
        this.roundNumber = roundNumber;
        this.questionList = questionList;
        this.submittedAnswers = submittedAnswers;
        this.activityList = activityList;
        this.state = state;
    }

    public long getNextPhase() {
        return nextPhase;
    }

    public void setNextPhase(long nextPhase) {
        this.nextPhase = nextPhase;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public List<AbstractQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<AbstractQuestion> questionList) {
        this.questionList = questionList;
    }

    public List<Response> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(List<Response> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }
}
