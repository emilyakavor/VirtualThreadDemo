import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/*
 *   How many platform threads will be created for 10 million virtual thread?
 */
public class VTPerformance {

    private static final int NO_OF_VT = 10000000;

    public static void main(String [] args) throws InterruptedException {
        Set<String> poolNames = ConcurrentHashMap.newKeySet();
        Set<String> pThreadsNames = ConcurrentHashMap.newKeySet();
        List<Thread> threads = IntStream.range(0, NO_OF_VT).mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
            String poolName = readPoolName();
            poolNames.add(poolName);
            String workerName = readWorkerName();
            pThreadsNames.add(workerName);
        })).toList();
        Instant begin = Instant.now();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        Instant end = Instant.now();

        System.out.println("# No. of VTs = " + NO_OF_VT);
        System.out.println("# Time = " + Duration.between(begin, end).toMillis() + "ms");
        System.out.println("# Platform threads: " + pThreadsNames.size());

    }


    private static String readPoolName() {
        String name = Thread.currentThread().toString();
        int i1 = name.indexOf("@ForkJoinPool");
        int i2 = name.indexOf("worker");
        return name.substring(i1, i2);
    }

    private static String readWorkerName() {
        String name = Thread.currentThread().toString();
        int i1 = name.indexOf("worker");
        return name.substring(i1);
    }


}
