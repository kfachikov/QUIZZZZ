package client.utils;

import commons.MultiUser;
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
    public List<MultiUser> getQueueUsers() {
        call("getQueueUsers");
        return (List<MultiUser>) returnValue;
    }

    @Override
    public MultiUser addQueueUser(MultiUser user) {
        call("addQueueUser");
        param = user;
        return (MultiUser) returnValue;
    }

    @Override
    public MultiUser deleteQueueUser(MultiUser user) {
        call("deleteQueueUser");
        param = user;
        return (MultiUser) returnValue;
    }
}
