package commons.misc;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Objects;

/**
 * Class ServerAddress that includes the address as a string.
 */
public class ServerAddress {
    private String address;

    /**
     * Default constructor for ServerAddress, for object mapper.
     */
    public ServerAddress() {
    }

    /**
     * Constructor for ServerAddress.
     *
     * @param address the address of the server
     */
    public ServerAddress(String address) {
        this.address = address;
    }

    /**
     * getter for the address.
     *
     * @return the address of the server
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter for the address.
     *
     * @param address the new address of the server
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Checks if two instances are equal.
     *
     * @param o the object that needs to be checked for equality
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Generates the hashcode of the instance.
     *
     * @return the hashcode of the entry
     */
    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    /**
     * Generates string value of the instance.
     *
     * @return String version of the entry
     */
    @Override
    public String toString() {
        return "ServerAddress{" +
                "address='" + address +
                '}';
    }

}
