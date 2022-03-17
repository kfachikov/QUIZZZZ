package commons;

import java.util.List;
import java.util.Objects;

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

    /**
     * Constructor for the state of the game.
     * @param timeLeft the time left during a round.
     * @param roundNumber the round number of the game.
     * @param questionList the list of question for a game.
     * @param submittedAnswers the answers submitted by players during game.
     * @param activityList the list of activities used for the game.
     * @param state the status of the game.
     */
    public GameState(double timeLeft, int roundNumber, List<AbstractQuestion> questionList, List<String> submittedAnswers, List<Activity> activityList, String state) {
        this.timeLeft = timeLeft;
        this.roundNumber = roundNumber;
        this.questionList = questionList;
        this.submittedAnswers = submittedAnswers;
        this.activityList = activityList;
        this.state = state;
    }

    /**
     * Getter for the time left.
     * @return a double representing the time left during a game.
     */
    public double getTimeLeft() {
        return timeLeft;
    }

    /**
     * Getter for the round number.
     * @return an int representing the round number of the game.
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Getter for the answer submitted.
     * @return a list of Strings representing the answer given by the players.
     */
    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }

    /**
     * Getter for the questions used during a game.
     * @return a list of AbstractQuestion representing the questions needed for the game.
     */
    public List<AbstractQuestion> getQuestionList() {
        return questionList;
    }

    /**
     * Getter for the activities used during a game.
     * @return a list of Activity representing the activities needed for the game.
     */
    public List<Activity> getActivityList() {
        return activityList;
    }

    /**
     * Getter for the status of the game.
     * @return a String representing the status of the game.
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for the time left.
     * @param timeLeft the time left during a game.
     */
    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Setter for the round number.
     * @param roundNumber the round number of the game.
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * Setter for the questions used during a game.
     * @param questionList the list of question for a game.
     */
    public void setQuestionList(List<AbstractQuestion> questionList) {
        this.questionList = questionList;
    }

    /**
     * Setter for the answer submitted.
     * @param submittedAnswers the answers submitted by players during game.
     */
    public void setSubmittedAnswers(List<String> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    /**
     * Setter for the activities used during a game.
     * @param activityList the list of activities used for the game.
     */
    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    /**
     * Setter for the status of the game.
     * @param state the status of the game.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Compares the two entities.
     * @param o the instance that is compared to.
     * @return true if the two entities are equal, otherwise it will be returned false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Double.compare(gameState.timeLeft, timeLeft) == 0 && roundNumber == gameState.roundNumber && Objects.equals(questionList, gameState.questionList) && Objects.equals(submittedAnswers, gameState.submittedAnswers) && Objects.equals(activityList, gameState.activityList) && Objects.equals(state, gameState.state);
    }

    /**
     * Computes the object's hash code.
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(timeLeft, roundNumber, questionList, submittedAnswers, activityList, state);
    }
}
