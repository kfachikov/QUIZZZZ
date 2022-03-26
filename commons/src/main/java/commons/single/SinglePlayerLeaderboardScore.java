
package commons.single;

import javax.persistence.*;
import java.util.Objects;

/**
 * The SinglePlayerLeaderboardScore class is used for generating the entries in the global SinglePlayer leaderboard.
 */

@Entity
public class SinglePlayerLeaderboardScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private int score;


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
     * @return he score
     */
    public int getScore() {
        return score;
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


}
