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
@JsonIgnoreProperties(value = { "correctAnswer" })
public abstract class AbstractQuestion {

    /**
     * Constructor for the abstract question.
     */
    protected AbstractQuestion() {
    }

    /**
     * Getter for the correct answer.
     *
     * @return the correct answer.
     *
     */
    public abstract String getCorrectAnswer();

    /**
     * Checker for the equality of two Abstract Questions.
     *
     * @param obj the object to be checked for equality.
     *
     * @return true/false
     *
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Generator for the hashcode.
     *
     * @return hashcode of this instance.
     *
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Convertor to string.
     *
     * @return String version of this instance.
     *
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}

