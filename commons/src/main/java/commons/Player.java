package commons;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Player {
    @Id
    private String username;
    private long score;

    public Player() {
    }

    public Player(String username, long score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
