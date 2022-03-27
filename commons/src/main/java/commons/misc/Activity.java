package commons.misc;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Activity class/instance is an entity-type/entity in the database. Stored since they are requested to stay after restart in Backlog.
 */
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
     * Example: 00-shower
     */
    private String id;

    /**
     * title field consists of information about the activity.
     * Example: Taking a hot shower for 6 minutes
     */
    private String title;

    /**
     * source field consisting of URL string of the resource where the information was gathered.
     * Example: https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower
     */
    private String source;

    /**
     * image field consisting of string of the file name of the image.
     * is called an "image_path" in json file format, therefore mapped to the shorter "image" name
     * Example: 00/shower.png
     */
    @JsonProperty("image_path")
    private String image;

    /**
     * consumption field consisting of long of the consumption of the activity in wh.
     * is called an "consumptio_in_wh" in json file format, therefore mapped to the shorter "consumption" name
     */
    @JsonProperty("consumption_in_wh")
    private Long consumption;

    /**
     * Default constructor for Activity. Used in the JSON parsing.
     */
    @SuppressWarnings("unused")
    public Activity() {
        // for object mapper
    }

    /**
     * Constructs an Activity object.
     *
     * @param id          Id (e. g. "00-shower")
     * @param title       Title
     * @param source      Source for the information
     * @param image       Image path
     * @param consumption Consumption in watt-hours
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
     *
     * @return The row attribute of a particular entry.
     */
    public Long getKey() {
        return key;
    }

    /**
     * Sets key of an entity.
     *
     * @param key Long value to be set as the key value.
     */
    public void setKey(Long key) {
        this.key = key;
    }

    /**
     * Get the identifying attribute, consisting of group number and the activity itself.
     *
     * @return The id attribute of a particular entry.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id field of an entry.
     *
     * @param id String value to be set as an id attribute.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the description of an activity.
     *
     * @return The title attribute of a particular entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title field of an entry.
     *
     * @param title String value to be set as a title attribute.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the source, where the information for the particular activity has been gathered from.
     *
     * @return The source attribute of a particular entry.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets source field of an entry.
     *
     * @param source String value to be set as a source attribute.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the image showing the activity "in action".
     *
     * @return The image attribute of a particular entry.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image field of an entry.
     *
     * @param image String value to be set as an image attribute.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get the consumption, stored in watt-hours of a particular activity.
     *
     * @return The title attribute of a particular entry.
     */
    public Long getConsumption() {
        return consumption;
    }

    /**
     * Sets consumption field of an entry.
     *
     * @param consumption String value to be set as a consumption attribute.
     */
    public void setConsumption(Long consumption) {
        this.consumption = consumption;
    }

    /**
     * Checks whether two activities are equal.
     *
     * @param o instance to be checked for equality.
     * @return true/false
     */
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