package BWJSAL.utils;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Utilities related to testing multithreaded code.
 */
public class ThreadUtils {
    /**
     * Runs each runnable in a new thread. This method blocks until all runnables are complete.
     * Before any runnables are run, we also wait until the allocated thread has ran at least once.
     * This is done to increase the randomness in the order of thread execution.
     */
    public static void startMultipleThreadsAndWaitUntilComplete(final List<Runnable> runnables)
            throws Exception {
        final Semaphore competingThreadsStarted = new Semaphore(0); // Number of threads for runnables started.
        final Semaphore competingThreadsToRelease = new Semaphore(0); // Acquired by runnable threads. Will be released
                                                                      // once all runnables have been run once.
        final Semaphore competingThreadsCompleted = new Semaphore(0); // Number of runnable threads completed.

        for (int i = 0; i < runnables.size(); i++) {
            final int runnableIndex = i;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Notify semaphore that this thread has been started.
                        competingThreadsStarted.release(1);

                        // Once all threads have notified the competingThreadsStarted semaphore,
                        // competingThreadsToRelease will be released and we will continue.
                        competingThreadsToRelease.acquire(1);

                        // Increases randomness of thread execution order.
                        Thread.sleep(1);

                        runnables.get(runnableIndex).run();

                        // thread has completed running provided runnable.
                        competingThreadsCompleted.release(1);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // Only proceed once all threads have at least started running once.
        competingThreadsStarted.acquire(runnables.size());

        // Release all threads.
        competingThreadsToRelease.release(runnables.size());

        // Wait until all threads have completed before returning.
        competingThreadsCompleted.acquire(runnables.size());
    }
}
