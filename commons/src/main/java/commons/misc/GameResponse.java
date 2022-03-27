package commons.misc;

import java.util.Objects;

/**
 * The GameResponse class will be used in the response controller to send or receive the answers of the player.
 */
public class GameResponse {

    private long gameId;
    private double timeSubmitted;
    private int roundNumber;
    private String playerUsername;
    private String answerChoice;

    private GameResponse() {

    }

    /**
     * Constructor for the response instance.
     *
     * @param gameId         the id of the game in which the response was generated
     * @param timeSubmitted  the time it took the player to answer
     * @param roundNumber    the number of the current round
     * @param playerUsername the name of the player
     * @param answerChoice   the final answer choice of the player
     */
    public GameResponse(long gameId, double timeSubmitted, int roundNumber,
                        String playerUsername, String answerChoice) {
        this.gameId = gameId;
        this.timeSubmitted = timeSubmitted;
        this.roundNumber = roundNumber;
        this.playerUsername = playerUsername;
        this.answerChoice = answerChoice;
    }

    /**
     * getter for the timeSubmitter.
     *
     * @return timeSubmitted
     */
    public double getTimeSubmitted() {
        return timeSubmitted;
    }

    /**
     * setter for the timeSubmitted.
     *
     * @param timeSubmitted the new timeSubmitted of the entry
     */
    public void setTimeSubmitted(double timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    /**
     * getter for the round number.
     *
     * @return round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * setter for the round number.
     *
     * @param roundNumber the new round number
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * getter for the answer choice.
     *
     * @return the answer choice
     */
    public String getAnswerChoice() {
        return answerChoice;
    }

    /**
     * setter for the answer choice.
     *
     * @param answerChoice the new answer choice.
     */
    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    /**
     * getter for the game id.
     *
     * @return the game id
     */
    public long getGameId() {
        return gameId;
    }

    /**
     * setter for the game id.
     *
     * @param gameId the new game id
     */
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    /**
     * getter for the player's name.
     *
     * @return the player's name
     */
    public String getPlayerUsername() {
        return playerUsername;
    }

    /**
     * Checks whether two instances are equal.
     *
     * @param o object that needs to be checked for equality
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameResponse response = (GameResponse) o;
        return gameId == response.gameId && Double.compare(response.timeSubmitted, timeSubmitted) == 0 &&
                roundNumber == response.roundNumber && playerUsername.equals(response.playerUsername) &&
                answerChoice.equals(response.answerChoice);
    }

    /**
     * Generates hashcode for the entry.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(gameId, timeSubmitted, roundNumber, playerUsername, answerChoice);
    }

    /**
     * Generates string version of the entry.
     *
     * @return string version of the entry
     */
    @Override
    public String toString() {
        return "GameResponse{" +
                "gameId=" + gameId +
                ", timeSubmitted=" + timeSubmitted +
                ", roundNumber=" + roundNumber +
                ", playerUsername='" + playerUsername + '\'' +
                ", answerChoice='" + answerChoice + '\'' +
                '}';
    }

}



