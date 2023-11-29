import java.util.List;
import java.util.stream.IntStream;

public class StackHeapMovement {

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Hello and welcome!\n");

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
            if (i == 0) {
                System.out.println("Stated virtual thread on " + Thread.currentThread());
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            if (i == 0) {
                System.out.println("Resuming virtual thread on " + Thread.currentThread());
            }

        })).toList();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

    }

}