package commons.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * An abstract class containing some shared functionality of the different question types.
 */
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
/*
Required as otherwise the JSON stringifier would create a key-value pair
of correct answer, because of the getter declared.

This would result in impossible parsing, as the question classes do not have
"correctAnswer" field.
 */
@JsonIgnoreProperties(value = {"correctAnswer"})
public abstract class AbstractQuestion {

    /**
     * Constructor for the abstract question.
     */
    protected AbstractQuestion() {
    }

    public abstract String getCorrectAnswer();

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

    /**
     * Debug string representation of the question.
     *
     * @return Automatically constructed representation of the question.
     */
    public String debugString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}

