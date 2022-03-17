package commons;

public class Response {
    private long gameId;
    private double timeSubmitted;
    private int roundNumber;
    private long playerId;
    private String answerChoice;

    public Response(long gameId, double timeSubmitted, int roundNumber, int playerId, String answerChoice) {
        this.gameId = gameId;
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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    public long getGameId() {
        return gameId;
    }
}

