package memoryperformance;

public class VirtualThreadMem {
    private static class Task implements Runnable {
        public void run() {
            try {
                Thread.sleep(600_000); // Sleep for 10 minutes
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Create 10, 000 platform threads
        for (int counter = 0; counter < 10_000; ++counter) {
            Thread.ofVirtual().start(new Task());
        }

        Thread.sleep(600_000);
    }
}
