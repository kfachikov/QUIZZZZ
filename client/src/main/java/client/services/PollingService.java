package client.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Supplier;

public class PollingService<T> extends Service<T> {

    private Supplier<T> poll;
    private long periodMs;

    /**
     * Start the polling service with the given poll action.
     * <p>
     * Sets the default polling rate to 500ms.
     *
     * @param poll Supplier to be called every 500ms.
     */
    public void start(Supplier<T> poll) {
        start(poll, 500);
    }

    /**
     * Start the polling service with the given supplier and period.
     *
     * @param poll     Supplier to be called repeatedly
     * @param periodMs Period between polls.
     */
    public void start(Supplier<T> poll, long periodMs) {
        if (isRunning()) {
            stop();
        }
        this.poll = poll;
        this.periodMs = periodMs;
        super.start();

        System.out.println("Started polling service with " + poll + " every " + periodMs);
    }

    /**
     * Stop the service.
     */
    public void stop() {
        this.cancel();
        this.reset();
    }

    /**
     * Create the polling task.
     *
     * @return Polling task.
     */
    @Override
    protected Task<T> createTask() {
        return new Task<T>() {
            @Override
            protected T call() {
                T value;
                updateValue(null);
                while (true) {
                    value = poll.get();
                    System.out.println("Old value was: " + getValue() + ", new value: " + value);
                    updateValue(value);
                    try {
                        Thread.sleep(periodMs);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return value;
            }
        };
    }
}
