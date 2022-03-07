package client.utils;

import commons.QueueUser;
import commons.SingleUser;

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
    public SingleUser addUser(SingleUser user) {
        call("addUser");
        param = user;
        return (SingleUser) returnValue;
    }

    @Override
    public List<QueueUser> getQueueUsers() {
        call("getQueueUsers");
        return (List<QueueUser>) returnValue;
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
