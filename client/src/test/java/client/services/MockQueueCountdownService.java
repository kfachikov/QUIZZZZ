package client.services;

import client.utils.ServerUtils;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MockQueueCountdownService extends QueueCountdownService {

    public Queue<Object> returnValues;

    public List<String> calledMethods;

    public MockQueueCountdownService(ServerUtils server) {
        super(server);
        this.calledMethods = new ArrayList<>();
        this.returnValues = new LinkedList<>();
    }

    private void call(String method) {
        calledMethods.add(method);
    }

    @Override
    public void stop() {
        call("stop");
    }

    @Override
    protected Task<Long> createTask() {
        call("createTask");
        return (Task<Long>) returnValues.poll();
    }

    @Override
    public LongProperty getCount() {
        call("getCount");
        return (LongProperty) returnValues.poll();
    }

    @Override
    public Timeline getTimeline() {
        call("getTimeline");
        return (Timeline) returnValues.poll();
    }

    @Override
    public boolean cancel() {
        call("cancel");
        return (Boolean) returnValues.poll();
    }

    @Override
    public void reset() {
        call("reset");
    }

    @Override
    public void start() {
        call("start");
    }
}
