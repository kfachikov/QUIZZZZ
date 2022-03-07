package client.scenes;

import commons.MultiUserQueue;

import java.util.ArrayList;
import java.util.List;

public class MockMainCtrl extends MainCtrl {

    public List<String> calledMethods;
    public Object param;

    public MockMainCtrl() {
        this.calledMethods = new ArrayList<>();
    }

    private void call(String method) {
        calledMethods.add(method);
    }

    /**
     * sets the title and the scene as home.
     */
    @Override
    public void showHome() {
        call("showHome");
    }

    /**
     * sets the title and the scene as prep.
     */
    @Override
    public void showPrep() {
        call("showHome");
    }

    /**
     * sets the title and the scene as help.
     */
    @Override
    public void showHelp() {
        call("showHelp");
    }

    /**
     * Sets the current scene to the queue screen, starts the queue polling
     * service and initializes the queue scene controller with
     * the MultiUserQueue instance of the person joining the queue.
     *
     * @param user MultiUserQueue which is joining the queue
     */
    @Override
    public void showQueue(MultiUserQueue user) {
        this.param = user;
        call("showQueue");
    }
}
