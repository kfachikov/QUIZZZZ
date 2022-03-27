package client.utils;

import commons.misc.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            List<Activity> addedActivities) throws FileNotFoundException {

    }

    /**
     * Reads an image at the given file path and returns base64 encoding of the image.
     *
     * @param imagePath File path of the image.
     * @return Base64 encoding of the image found at the given path.
     * @throws FileNotFoundException If no image exists at the given location.
     */
    public String readImage(String imagePath) throws FileNotFoundException {
        File file = new File(imagePath);
        FileInputStream stream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        try {
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
        Path activitiesBankFolder = activitiesPath.getRoot();
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
}
