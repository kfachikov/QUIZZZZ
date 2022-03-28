package commons.misc;

/**
 * Message class for communicating between client and server.
 */
public class ActivityImageMessage {
    private final String imageBase64;
    private final long key;

    /**
     * Default constructor.
     */
    public ActivityImageMessage() {
        this.imageBase64 = null;
        this.key = -1;
    }

    /**
     * Constructor for ActivityImageMessage.
     *
     * @param imageBase64 Base64 encoded image
     * @param key         Key of the activity
     */
    public ActivityImageMessage(String imageBase64, long key) {
        this.imageBase64 = imageBase64;
        this.key = key;
    }

    /**
     * Getter for the Base64 encoded image.
     *
     * @return Base64 encoded image
     */
    public String getImageBase64() {
        return imageBase64;
    }

    /**
     * Getter for the key of the activity.
     *
     * @return Key of the activity
     */
    public long getKey() {
        return key;
    }
}
