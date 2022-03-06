package server.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Activity {

    /**
     * key field would be the primary key of our entities, as the JSON file we parse have key id which contains different information.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    /**
     * id field consists of information about the group that contributed it and the activity itself in the format "XX-{activity}".
     * Examples: 00-shower, 01-driving
     */
    private String id;
    private String title;
    private String source;

    @JsonProperty("image_path")
    private String image;

    @JsonProperty("consumption_in_wh")
    private Long consumption;

    @SuppressWarnings("unused")
    public Activity() {
        // for object mapper
    }

    /**
     * Constructs an Activity object.
     * @param id
     * @param title
     * @param source
     * @param image
     * @param consumption
     */
    public Activity(String id, String title, String source, String image, Long consumption) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.image = image;
        this.consumption = consumption;
    }

    /**
     * Get the primary-key attribute of an entity.
     * @return The row attribute of a particular entry.
     */
    public Long getKey() {
        return key;
    }

    /**
     * Sets row field - primary key in table.
     * @param key Long value to be set as row attribute.
     */
    public void setKey(Long key) {
        this.key = key;
    }

    /**
     * Get the identifying attribute, consisting of group number and the activity itself.
     * @return The id attribute of a particular entry.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id field of an entry.
     * @param id String value to be set as an id attribute.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the description of an activity.
     * @return The title attribute of a particular entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title field of an entry.
     * @param title String value to be set as a title attribute.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the source, where the information for the particular activity has been gathered from.
     * @return The source attribute of a particular entry.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets source field of an entry.
     * @param source String value to be set as a source attribute.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the image showing the activity "in action".
     * @return The image attribute of a particular entry.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image field of an entry.
     * @param image String value to be set as an image attribute.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get the consumption, stored in watt-hours of a particular activity.
     * @return The title attribute of a particular entry.
     */
    public Long getConsumption() {
        return consumption;
    }

    /**
     * Sets consumption field of an entry.
     * @param consumption String value to be set as a consumption attribute.
     */
    public void setConsumption(Long consumption) {
        this.consumption = consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return Objects.equals(key, activity.key) &&
                Objects.equals(id, activity.id) &&
                Objects.equals(title, activity.title) &&
                Objects.equals(source, activity.source) &&
                Objects.equals(image, activity.image) &&
                Objects.equals(consumption, activity.consumption);
    }
}