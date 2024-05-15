package org.example.counter;

public class Main {
    public static void main(String[] args) {
        ICounter[] counters = {new Counter(), new CounterLock(), new CounterSync(), new CounterSyncBlock()};
        for (int i = 0; i < counters.length; i++) {
            long startTime = System.currentTimeMillis();
            counters[i].resetCounter();
            runTwoThreads(counters[i]);
            long finishTime = System.currentTimeMillis();
            if (i == 0) System.out.println("Async result: " + counters[i].getValue());
            else System.out.println(counters[i] +" result: " + counters[i].getValue());
            System.out.println("Time:" + (finishTime-startTime));
        }
    }

    private static void runTwoThreads(ICounter counter) {
        try {
            Thread incThread = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    counter.increment();
                }
            });
            Thread decThread = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    counter.decrement();
                }
            });
            incThread.start();
            decThread.start();
            incThread.join();
            decThread.join();
        } catch (InterruptedException ex) {
        }
    }
}
