package commons;

public class Response {
    private double timeSubmitted;
    private int roundNumber;
    private int playerId;
    private String answerChoice;

    public Response(double timeSubmitted, int roundNumber, int playerId, String answerChoice) {
        this.timeSubmitted = timeSubmitted;
        this.roundNumber = roundNumber;
        this.playerId = playerId;
        this.answerChoice = answerChoice;
    }

    public double getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(double timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

}

