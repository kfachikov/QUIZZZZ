package server.utils;

import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

@Component
public class QueueUtils {

    private QueueState queueState;

    public QueueUtils() {
        this.queueState = new QueueState(
                new ArrayList<>(),
                false,
                Long.MAX_VALUE,
                0
        );
    }


    /**
     * Getter for the current queue state.
     *
     * @return a QueueState instance containing corresponding information -
     * as the QueueState on all clients already residing in the queue.
     */
    public QueueState getQueue() {
        return queueState;
    }

    public QueueUser addUser(QueueUser user) {
        if (isInvalid(user)) {
            return null;
        } else if (containsUser(user)) {
            return null;
        } else {
            getUsers().add(user);
            resetCountdown();
            return user;
        }
    }

    public QueueUser removeUser(QueueUser user) {
        if (isInvalid(user)) {
            return null;
        } else if (!containsUser(user)) {
            return null;
        } else {
            getUsers().remove(user);
            return user;
        }
    }

    public boolean startCountdown() {
        if (queueState.isGameStarting()) {
            return false;
        } else {
            queueState.setGameStarting(true);
            queueState.setStartTimeInMs(new Date().getTime() + 3000);
            return true;
        }
    }

    public void resetCountdown() {
        queueState.setGameStarting(false);
        queueState.setStartTimeInMs(Long.MAX_VALUE);
    }

    public boolean isInvalid(QueueUser user) {
        if (user == null) {
            return true;
        } else if (user.getUsername() == null) {
            return true;
        } else {
            return user.getUsername().isEmpty();
        }
    }

    public boolean containsUser(QueueUser user) {
        return getUsers().contains(user);
    }

    public QueueUser getByUsername(String username) {
        return getUsers().stream()
                .filter((user -> user.getUsername().equals(username)))
                .findAny().orElse(null);
    }

    private List<QueueUser> getUsers() {
        return queueState.getUsers();
    }
}
