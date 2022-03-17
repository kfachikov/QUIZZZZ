
package commons;

import javax.persistence.*;

import java.util.Objects;

@Entity
public class SinglePlayerLeaderboardScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private int score;



    public SinglePlayerLeaderboardScore(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


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


    @Override
    public int hashCode() {
        return Objects.hash(id, username, score);
    }

    @Override
    public String toString() {
        return "SinglePlayerLeaderboardScore{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                '}';
    }


}
