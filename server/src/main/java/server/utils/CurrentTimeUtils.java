package server.utils;

import java.util.Date;

/**
 * Utility class for getting the current time.
 * <p>
 * Separated out for the sake of testability.
 */
public class CurrentTimeUtils {
    /**
     * Getter for the current time in milliseconds.
     *
     * @return Current time in milliseconds.
     */
    public long getTime() {
        return new Date().getTime();
    }
}
