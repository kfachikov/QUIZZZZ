package client.utils;

import commons.misc.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility class responsible for managing adding and retrieving images of activities.
 */
public class ActivityImageUtils {

    private final ServerUtils serverUtils;

    /**
     * Constructor for ActivityImageUtils.
     *
     * @param serverUtils ServerUtils singleton instance.
     */
    public ActivityImageUtils(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
    }


    public void addActivitiesImages(
            String activitiesPath,
            List<Activity> addedActivities) {

    }

    /**
     * Construct an activity id from the given image path.
     *
     * @param imagePath Some image path, e.g. C:/Users/User/Bank/00/shower.png
     * @return Id string of the activity, e.g. 00-shower
     */
    public String getActivityId(String imagePath) {
        Path path = new File(imagePath).toPath();
        int nameCount = path.getNameCount();
        String activityName = path.getName(nameCount - 1).toString();
        String activityGroup = path.getName(nameCount - 2).toString();

        return activityGroup + "-" + removeExtension(activityName);
    }

    /**
     * Reads an image at the given file path and returns base64 encoding of the image.
     *
     * @param imagePath File path of the image.
     * @return Base64 encoding of the image found at the given path.
     */
    public String readImage(String imagePath) {
        File file = new File(imagePath);
        byte[] bytes = new byte[(int) file.length()];

        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(bytes);
            stream.close();
        } catch (IOException e) {
            System.err.println("Unknown error occured when reading the image.");
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Get paths of all images from the given location on the JSON file in the activity bank.
     *
     * @param activitiesPathString Path of the JSON file in the activity bank.
     * @return List of all image paths.
     */
    public List<String> getAllImagePaths(String activitiesPathString) {
        ArrayList<String> imagePaths = new ArrayList<>();
        File activitiesFile = new File(activitiesPathString);
        Path activitiesPath = activitiesFile.toPath();
        Path activitiesBankFolder = activitiesPath.getParent();
        try {
            Stream<Path> paths = Files.find(
                    activitiesBankFolder,
                    2,
                    ((path, basicFileAttributes) -> {
                        return basicFileAttributes.isRegularFile() && !path.endsWith(".json");
                    })
            );
            paths.forEach((path) -> imagePaths.add(path.toString()));
        } catch (IOException e) {
            return imagePaths;
        }
        return imagePaths;
    }

    /**
     * Remove extension from a file name.
     *
     * @param filename File name.
     * @return File name with extension removed.
     */
    private String removeExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        return filename.substring(0, lastIndex);
    }
}
