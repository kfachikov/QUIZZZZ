package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class QueueState {
    public List<QueueUser> users;
    public boolean gameStarting;
    public long msToStart;

    public QueueState() {
        this.users = new ArrayList<>();
        this.gameStarting = false;
        this.msToStart = 3000;
    }

    public QueueState(List<QueueUser> users) {
        this.users = users;
        this.gameStarting = false;
        this.msToStart = 3000;
    }

    public QueueState(List<QueueUser> users, boolean gameStarting, long msToStart) {
        this.users = users;
        this.gameStarting = gameStarting;
        this.msToStart = msToStart;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
