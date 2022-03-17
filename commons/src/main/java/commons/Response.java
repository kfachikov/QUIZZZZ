package commons;

import java.util.Objects;

public class Response {
    private long gameId;
    private double timeSubmitted;
    private int roundNumber;
    private String playerUsername;
    private String answerChoice;

    public Response(long gameId, double timeSubmitted, int roundNumber, String playerUsername, String answerChoice) {
        this.gameId = gameId;
        this.timeSubmitted = timeSubmitted;
        this.roundNumber = roundNumber;
        this.playerUsername = playerUsername;
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

    public String getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response response = (Response) o;
        return gameId == response.gameId && Double.compare(response.timeSubmitted, timeSubmitted) == 0 && roundNumber == response.roundNumber && playerUsername.equals(response.playerUsername) && answerChoice.equals(response.answerChoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, timeSubmitted, roundNumber, playerUsername, answerChoice);
    }

    @Override
    public String toString() {
        return "Response{" +
                "gameId=" + gameId +
                ", timeSubmitted=" + timeSubmitted +
                ", roundNumber=" + roundNumber +
                ", playerUsername='" + playerUsername + '\'' +
                ", answerChoice='" + answerChoice + '\'' +
                '}';
    }

}



