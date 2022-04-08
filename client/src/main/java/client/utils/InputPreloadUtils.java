package client.utils;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for preloading the text of username and server address.
 */
public class InputPreloadUtils {

    /**
     * Save input of the user for the next time they run the application.
     *
     * @param username      Username of the user to save.
     * @param serverAddress Server address of the target server to save.
     */
    public void saveInput(String username, String serverAddress) {
        try (PrintWriter printWriter = new PrintWriter("home.cfg")) {
            printWriter.println(username);
            printWriter.println(serverAddress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read saved input of username and server address.
     *
     * @return Saved input of username and server address, as a pair.
     */
    public Pair<String, String> readInput() {
        try (Scanner scanner = new Scanner(new File("home.cfg"))) {
            String username = scanner.nextLine();
            String serverAddress = scanner.nextLine();
            return new Pair<>(username, serverAddress);
        } catch (FileNotFoundException | NoSuchElementException ignored) {
        }
        return new Pair<>("", "");
    }
}
