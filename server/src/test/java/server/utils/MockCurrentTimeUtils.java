package server.utils;

import java.util.ArrayList;

/**
 * Mock class for testing other classes that use CurrentTimeUtils.
 */
public class MockCurrentTimeUtils extends CurrentTimeUtils {

    /**
     * Current time indicated by this utilities class.
     */
    public long currentTime;

    /**
     * Methods that were called on this instance.
     */
    public ArrayList<String> calledMethods = new ArrayList<>();

    /**
     * Getter for the current time in milliseconds.
     *
     * @return Current time in milliseconds.
     */
    @Override
    public long getTime() {
        calledMethods.add("getTime");
        return currentTime;
    }
}
