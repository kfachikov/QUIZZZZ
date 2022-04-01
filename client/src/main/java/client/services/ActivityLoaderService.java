package client.services;

import commons.misc.Activity;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for loading activities.
 */
public class ActivityLoaderService extends Service<List<Activity>> {
    /**
     * Invoked after the Service is started on the JavaFX Application Thread.
     *
     * @return the Task to execute
     */
    @Override
    protected Task<List<Activity>> createTask() {
        return new Task<>() {
            @Override
            protected List<Activity> call() {
                ArrayList<Activity> loadedActivities = new ArrayList<>();

                return loadedActivities;
            }
        };
    }
}
