package commons.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ConsumptionQuestion.class, name = "consumption"),
    @JsonSubTypes.Type(value = GuessQuestion.class, name = "guess"),
    @JsonSubTypes.Type(value = InsteadQuestion.class, name = "instead"),
    @JsonSubTypes.Type(value = MoreExpensiveQuestion.class, name = "moreExpensive")
})
public abstract class AbstractQuestion {

    /**
     * Constructor for the abstract question.
     */
    protected AbstractQuestion () {
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

