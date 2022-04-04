package commons.multi;


import commons.misc.Player;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The MultiPlayer class is used to represent the current player of the multiplayer game.
 */
public class MultiPlayer extends Player {

    private boolean timeJoker;
    private boolean incorrectAnswerJoker;
    private boolean pointsDoubledJoker;
    private boolean currentlyUsingDoublePoints;

    /*
    Field to track the number of times a player is affected by "Time Attack" joker.
    One for the whole game.
     */
    private int timerRate;

    public MultiPlayer() {
        super();
    }

    /**
     * Constructor for the player who is playing multiplayer game.
     *
     * @param username             the player's username.
     * @param score                the player's score.
     * @param timeJoker            the joker for reducing the time
     * @param incorrectAnswerJoker the joker for eliminating an incorrect answer
     * @param pointsDoubledJoker   the joker for doubling the points.
     */
    public MultiPlayer(String username, int score, boolean timeJoker,
                       boolean incorrectAnswerJoker, boolean pointsDoubledJoker) {
        super(username, score);
        this.timeJoker = timeJoker;
        this.incorrectAnswerJoker = incorrectAnswerJoker;
        this.pointsDoubledJoker = pointsDoubledJoker;
    }

    /**
     * getter for the time joker.
     *
     * @return timeJoker
     */
    public boolean getTimeJoker() {
        return timeJoker;
    }

    /**
     * getter for the IncorrectAnswerJoker.
     *
     * @return IncorrectAnswerJoker
     */
    public boolean getIncorrectAnswerJoker() {
        return incorrectAnswerJoker;
    }

    /**
     * getter for the PointsDoubledJoker.
     *
     * @return PointsDoubledJoker
     */
    public boolean getPointsDoubledJoker() {
        return pointsDoubledJoker;
    }

    /**
     * setter for the TimeJoker.
     *
     * @param timeJoker the new TimeJoker
     */
    public void setTimeJoker(boolean timeJoker) {
        this.timeJoker = timeJoker;
    }

    /**
     * setter for the IncorrectAnswerJoker.
     *
     * @param incorrectAnswerJoker the new IncorrectAnswerJoker
     */
    public void setIncorrectAnswerJoker(boolean incorrectAnswerJoker) {
        this.incorrectAnswerJoker = incorrectAnswerJoker;
    }

    /**
     * setter for the PointsDoubledJoker.
     *
     * @param pointsDoubledJoker the new PointsDoubledJoker
     */
    public void setPointsDoubledJoker(boolean pointsDoubledJoker) {
        this.pointsDoubledJoker = pointsDoubledJoker;
    }

    /**
     * Getter for whether the player is currently using the "Double Points" joker.
     *
     * @return  Boolean value
     */
    public boolean isCurrentlyUsingDoublePoints() {
        return currentlyUsingDoublePoints;
    }

    /**
     * Setter for whether a player is currently using the "Double Points" joker.
     *
     * @param currentlyUsingDoublePoints    Value to be set to the field
     *                                      corresponding the whether the player is
     *                                      using the "Double Points" joker this round.
     */
    public void setCurrentlyUsingDoublePoints(boolean currentlyUsingDoublePoints) {
        this.currentlyUsingDoublePoints = currentlyUsingDoublePoints;
    }

    /**
     * Getter for the number of times a player have been (or have to be) affected
     * by "Time Attack" jokers submitted by any of their opponents.
     *
     * @return  Integer value.
     */
    public int getTimerRate() {
        return timerRate;
    }

    /**
     * Increments the `timerRate` instance by 1.
     */
    public void incrementTimerRate() {
        timerRate++;
    }

    /**
     * Checks wheter two instances are equal.
     *
     * @param o the object that needs to be checked for equality
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Generates hashcode for the instance.
     *
     * @return hashcode for the instance
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

