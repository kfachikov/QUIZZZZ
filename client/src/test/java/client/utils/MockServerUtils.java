package client.utils;

import commons.QueueState;
import commons.QueueUser;
import commons.SinglePlayer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MockServerUtils extends ServerUtils {

    public List<String> calledMethods;
    public Object returnValue;
    public Object param;

    public MockServerUtils() {
        this.calledMethods = new ArrayList<>();
        this.returnValue = null;
    }

    private void call(String method) {
        calledMethods.add(method);
    }

    @Override
    public SinglePlayer addSinglePlayer(SinglePlayer singlePlayer) {
        call("addUser");
        param = singlePlayer;
        return (SinglePlayer) returnValue;
    }

    @Override
    public QueueState getQueueState() {
        call("getQueueUsers");
        return (QueueState) returnValue;
    }

    @Override
    public QueueUser addQueueUser(QueueUser user) {
        call("addQueueUser");
        param = user;
        return (QueueUser) returnValue;
    }

    @Override
    public QueueUser deleteQueueUser(QueueUser user) {
        call("deleteQueueUser");
        param = user;
        return (QueueUser) returnValue;
    }
}
