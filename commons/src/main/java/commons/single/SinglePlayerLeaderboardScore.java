
package commons.single;

import javax.persistence.*;
import java.util.Objects;

/**
 * The SinglePlayerLeaderboardScore class is used for generating the entries in the global SinglePlayer leaderboard.
 */

@Entity
public class SinglePlayerLeaderboardScore implements Comparable<SinglePlayerLeaderboardScore> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private int score;

    /**
     * this is a default constructor needed for JSON parsing.
     */
    public SinglePlayerLeaderboardScore() {
    }


    /**
     * constructor for the leaderboard entry.
     *
     * @param username Username of the player
     * @param score    Score of the player
     */
    public SinglePlayerLeaderboardScore(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * getter for the id of the entry.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }


    /**
     * getter for the username of the entry.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }


    /**
     * getter for the score of the entry.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public void setUsername() {
        this.username = username;
    }

    public void setScore() {
        this.score = score;
    }

    /**
     * Checks whether two instances are equal.
     *
     * @param o the object that will be checked for equality
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
        SinglePlayerLeaderboardScore that = (SinglePlayerLeaderboardScore) o;
        return score == that.score && id.equals(that.id) && username.equals(that.username);
    }

    /**
     * Generates the hashcode of an instance.
     *
     * @return the hashcode of the entry
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, score);
    }

    /**
     * Generates string version of the instance.
     *
     * @return String version of the entity.
     */
    @Override
    public String toString() {
        return "SinglePlayerLeaderboardScore{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                '}';
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(SinglePlayerLeaderboardScore o) {
        return this.score - o.score;
    }
}
