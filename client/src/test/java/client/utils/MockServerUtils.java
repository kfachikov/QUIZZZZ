package client.utils;

import commons.MultiUserQueue;
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
    public List<MultiUserQueue> getQueueUsers() {
        call("getQueueUsers");
        return (List<MultiUserQueue>) returnValue;
    }

    @Override
    public MultiUserQueue addQueueUser(MultiUserQueue user) {
        call("addQueueUser");
        param = user;
        return (MultiUserQueue) returnValue;
    }

    @Override
    public MultiUserQueue deleteQueueUser(MultiUserQueue user) {
        call("deleteQueueUser");
        param = user;
        return (MultiUserQueue) returnValue;
    }
}
