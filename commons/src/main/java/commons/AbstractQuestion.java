package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public abstract class AbstractQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String baseTitle;
    public String imageFilename;
    public long consumptionWh;
    @Transient
    private Object obj;

    public Object getObj() {
        return obj;
    }

    @SuppressWarnings("unused")
    AbstractQuestion() {
        // for object mapper
    }

    /**
     * @param baseTitle title for the activity
     * @param imageFilename file name
     * @param consumptionWh consuption in wh
     */
    public AbstractQuestion(String baseTitle, String imageFilename, long consumptionWh) {
        this.baseTitle = baseTitle;
        this.imageFilename = imageFilename;
        this.consumptionWh = consumptionWh;
    }

    @Override
    public boolean equals(Object obj) {
        this.obj = obj;
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}

