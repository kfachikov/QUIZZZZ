package commons.misc;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Objects;

/**
 * The Player class is the parent class for SinglePlayer and MultiPlayer.
 */
public abstract class Player {

    private String username;
    private int score;

    public Player() {
    }

    /**
     * Constructor for Singleplayer and Multiplayer.
     *
     * @param username the name of the user
     * @param score    the score of the user
     */
    public Player(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * getter for the username.
     *
     * @return the username of the entry
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for the username.
     *
     * @param username the new username of the entry
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for the score of the entry.
     *
     * @return the score of the entry
     */
    public int getScore() {
        return score;
    }

    /**
     * setter for the of the entry.
     *
     * @param score new score of the entry
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Checks if two instances are equal.
     *
     * @param o the object that needs to be checked for equality
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Generates the hashcode of the instance.
     *
     * @return the hashcode of the entry
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, score);
    }

    /**
     * Generates string value of the instance.
     *
     * @return String version of the entry
     */
    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }

}
