package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public abstract class AbstractQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String baseTitle;
    public String imageFilename;
    public long consumptionWh;

    @SuppressWarnings("unused")
    AbstractQuestion() {
        // for object mapper
    }

    public AbstractQuestion(String baseTitle, String imageFilename, long consumptionWh) {
        this.baseTitle = baseTitle;
        this.imageFilename = imageFilename;
        this.consumptionWh = consumptionWh;
    }

    @Override
    public boolean equals(Object obj) {
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

