package commons.misc;

import javax.persistence.*;

/**
 * JPA Entity for storing images of the activities.
 */
@Entity
public class ActivityImage {

    /**
     * Mirrors the id of the activity.
     * For example: 00-shower
     */
    @Id
    private final String id;

    /**
     * Large Object representing the bytes of the image.
     */
    @Lob
    private final byte[] image;

    /**
     * Default constructor for object mapper.
     */
    public ActivityImage() {
        this.id = null;
        this.image = null;
    }

    /**
     * Constructor for ActivityImage.
     *
     * @param id    Id of the activity, e.g. 00-shower
     * @param image Byte array of the image
     */
    public ActivityImage(String id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    /**
     * Getter for the id of the activity image.
     *
     * @return Id of the activity.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the image of the activity.
     *
     * @return Array of bytes of the image.
     */
    public byte[] getImage() {
        return image;
    }

}
