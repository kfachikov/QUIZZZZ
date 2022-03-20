package commons.multi;

import java.util.List;
import java.util.Objects;

public class Reaction {
    /**
     * The list of reactions that a player is using.
     */
    private List<String> emojis;

    /**
     * Constructor for the emojis.
     *
     * @param emojis names of the reactions.
     */
    public Reaction(List<String> emojis) {
        this.emojis = emojis;
    }

    /**
     * Getter for the list of emojis.
     *
     * @return the list of emojis.
     */
    public List<String> getEmojis() {
        return emojis;
    }

    /**
     * Setter for the list of emojis.
     *
     * @param emojis a list of emojis.
     */
    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    /**
     * Compares two entities.
     *
     * @param o the instance that is compared to.
     * @return true if the two entities are equal, otherwise it will be returned false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reaction reaction = (Reaction) o;
        return Objects.equals(emojis, reaction.emojis);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(emojis);
    }

}
