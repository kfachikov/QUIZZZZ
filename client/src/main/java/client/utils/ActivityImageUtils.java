package client.utils;

import commons.misc.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

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
        } catch (IOException e) {
            System.err.println("Unknown error occured when reading the image.");
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
}
