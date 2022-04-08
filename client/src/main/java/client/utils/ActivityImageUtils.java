package client.utils;

import commons.misc.Activity;
import commons.misc.ActivityImageMessage;
import jakarta.ws.rs.NotFoundException;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Utility class responsible for managing adding and retrieving images of activities.
 */
public class ActivityImageUtils {

    private final ServerUtils serverUtils;
    private final HashMap<Long, Image> activityImageCache;

    /**
     * Constructor for ActivityImageUtils.
     *
     * @param serverUtils ServerUtils singleton instance.
     */
    @Inject
    public ActivityImageUtils(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
        this.activityImageCache = new HashMap<>();
    }

    /**
     * GET mapping for activity image.
     * <p>
     * Caches results internally.
     *
     * @param key Key of the activity in the repository.
     * @return JavaFX image of the activity image.
     */
    public Image getActivityImage(long key) {

        if (activityImageCache.containsKey(key)) {
            return activityImageCache.get(key);
        } else {
            String imageBase64;
            try {
                ActivityImageMessage message = serverUtils
                        .getActivityImage(key);
                imageBase64 = message.getImageBase64();
            } catch (NotFoundException e) {
                System.err.println(e);
                e.printStackTrace();
                return null;
            }

            byte[] decodedImage = Base64.getDecoder().decode(imageBase64);
            InputStream imageInputStream = new ByteArrayInputStream(decodedImage);

            Image image = new Image(imageInputStream);
            activityImageCache.put(key, image);
            return image;
        }
    }

    /**
     * Convert list of activities to a map from activity IDs to activity keys.
     *
     * @param activityList List of activities.
     * @return Map of Activity.id to Activity.key
     */
    public Map<String, Long> listToMap(List<Activity> activityList) {
        Map<String, Long> activityMap = new HashMap<>();
        for (Activity activity : activityList) {
            activityMap.put(activity.getId(), activity.getKey());
        }
        return activityMap;
    }

    /**
     * Adds an activity image to the server database.
     *
     * @param key         Key of the newly imported activity.
     * @param imageBase64 Base64 representation of the image.
     */
    public void addActivityImage(long key, String imageBase64) {
        ActivityImageMessage message = new ActivityImageMessage(imageBase64, key);

        serverUtils.addActivityImage(message);
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
    public String getImageBase64(String imagePath) {
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

    /**
     * Adds the image in the selected .jpg file in AdminPanel as the new image of the activity.
     *
     * @param key the key of the activity to change the image of.
     * @return the new image that replaced the old one.
     */
    public Image selectImageFile(Long key) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String imageBase64 = getImageBase64(selectedFile.getPath());
        ActivityImageMessage message = new ActivityImageMessage(imageBase64, key);
        serverUtils.addActivityImage(message);
        byte[] decodedImage = Base64.getDecoder().decode(imageBase64);
        InputStream imageInputStream = new ByteArrayInputStream(decodedImage);
        Image image = new Image(imageInputStream);
        activityImageCache.remove(key);
        return image;
    }
}
