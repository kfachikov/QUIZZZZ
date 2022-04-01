package client.services;

import client.utils.ActivityImageUtils;
import client.utils.ServerUtils;
import commons.misc.Activity;
import jakarta.ws.rs.NotFoundException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service responsible for loading activities.
 */
public class ActivityLoaderService extends Service<List<Activity>> {
    private final ServerUtils server;
    private final ActivityImageUtils activityImageUtils;

    private File selectedFile;

    /**
     * Constructor for ActivityLoaderService.
     *
     * @param server             ServerUtils instance for adding the activities.
     * @param activityImageUtils ActivityImageUtils instance for adding images for activities.
     */
    @Inject
    public ActivityLoaderService(ServerUtils server, ActivityImageUtils activityImageUtils) {
        this.server = server;
        this.activityImageUtils = activityImageUtils;
    }

    /**
     * Start the service with the given file selected.
     *
     * @param selectedFile JSON file that the user selected.
     */
    public void start(File selectedFile) {
        this.selectedFile = selectedFile;
        super.start();
    }

    /**
     * Stringifies the contents of the file.
     *
     * @param selectedFile the file selected by the user
     * @return String of the contents of the file.
     * @throws IOException IO Exception that's being thrown
     */
    public String extractFile(File selectedFile) throws IOException {
        return Files.readString(selectedFile.toPath());
    }

    /**
     * Invoked after the Service is started on the JavaFX Application Thread.
     *
     * @return the Task to execute
     */
    @Override
    protected Task<List<Activity>> createTask() {
        return new ActivityLoaderTask(selectedFile);
    }

    private class ActivityLoaderTask extends Task<List<Activity>> {
        private final File selectedFile;

        /**
         * Constructor for ActivityLoaderTask.
         *
         * @param selectedFile File that was selected.
         */
        public ActivityLoaderTask(File selectedFile) {
            this.selectedFile = selectedFile;
        }

        @Override
        protected List<Activity> call() throws IOException {
            String fileContents = extractFile(selectedFile);

            List<Activity> importedActivities = server.importActivities(fileContents);
            ArrayList<Activity> loadedActivities = new ArrayList<>(importedActivities);

            long maxProgress = importedActivities.size() + 3;
            long workDone = 0;
            updateProgress(workDone++, maxProgress);

            String selectedFilePath = selectedFile.getPath();

            List<String> imagePaths = activityImageUtils.getAllImagePaths(selectedFilePath);
            updateProgress(workDone++, maxProgress);

            Map<String, Long> idKeyMap = activityImageUtils.listToMap(importedActivities);
            updateProgress(workDone++, maxProgress);

            for (String imagePath : imagePaths) {
                String id = activityImageUtils.getActivityId(imagePath);
                String imageBase64 = activityImageUtils.getImageBase64(imagePath);

                if (idKeyMap.containsKey(id)) {
                    long key = idKeyMap.get(id);
                    try {
                        activityImageUtils.addActivityImage(key, imageBase64);
                    } catch (NotFoundException ignored) {
                        // Skip if something goes wrong
                    }
                }
                updateProgress(workDone++, maxProgress);
            }

            return loadedActivities;
        }
    }
}
