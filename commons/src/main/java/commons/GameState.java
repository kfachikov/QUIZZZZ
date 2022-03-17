package commons;

import java.util.List;

public abstract class GameState {

    private double timeLeft;
    private int roundNumber;
    private List<AbstractQuestion> questionList;
    private List<String> submittedAnswers;
    private List<Activity> activityList;
    /**
     * The state attribute is a String from : transition, intermittent leaderboard, question, game over.
     */
    private String state;

    public GameState(double timeLeft, int roundNumber, List<AbstractQuestion> questionList, List<String> submittedAnswers, List<Activity> activityList, String state) {
        this.timeLeft = timeLeft;
        this.roundNumber = roundNumber;
        this.questionList = questionList;
        this.submittedAnswers = submittedAnswers;
        this.activityList = activityList;
        this.state = state;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
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

    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(List<String> submittedAnswers) {
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

}
