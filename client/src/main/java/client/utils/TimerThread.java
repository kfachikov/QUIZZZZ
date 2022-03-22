package client.utils;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.util.Date;

/*
Thread to be instantiated every time a new scene is shown.
 */
public class TimerThread extends Thread {

    private ProgressBar time;
    private long nextPhase;
    private long thisPhase;

    /*
    Used to denote how frequently the thread should execute its task.
    Thus, even with some delays occurring while data is transferred over the network,
    the progress bar would have steady flow and its "start" and "end" would match the
    changing of the scenes.
     */
    private long frequency;

    /**
     * Constructor for TimerThread instance.
     * Sets the explicit reference to the progress bar to be altered, the "time" when the next phase should begin,
     * and the current time - later two are used for the frequency to be computed.
     *
     * @param time      ProgressBar instance of the current QuestionScreen.
     * @param nextPhase Time of nextPhase - changing of scenes.
     */
    public TimerThread(ProgressBar time, long nextPhase) {
        this.time = time;
        this.nextPhase = nextPhase;
        this.thisPhase = new Date().getTime();

        setDefault();
    }

    /**
     * Sets the default behavior of the thread.
     * Progress is zero.
     * Color of "progress" tracker is set to the initially used blue.
     */
    private void setDefault() {
        this.frequency = (nextPhase - thisPhase) / 100;
        time.setProgress(0.0);
        time.setStyle("-fx-accent: #006e8c");
    }

    /**
     * Overridden run() method achieving the required functionality.
     * The Progress bar is updated 100 times on every scene so that the
     * "counting" looks steady.
     */
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            /*
            Changes of any `Node` instance to the UI happen in the JavaFX application Thread.
            Thus, the following method is required, as otherwise, the thread are concurrent,
            resulting in the "frozen" UI experience - the progress bar flashes all around non-stop.

            The Platform.runLater() is used so that the task of the JavaFX application thread
            is executed only when it has time. Thus, no conflicts between the threads would occur.
             */
            Platform.runLater(() -> {
                time.setProgress(finalI / 100.0);
                if (finalI > 70) {
                    time.setStyle("-fx-accent: red");
                }
            });
            try {
                Thread.sleep(frequency);
            } catch (InterruptedException e) {
            }
        }
    }
}
