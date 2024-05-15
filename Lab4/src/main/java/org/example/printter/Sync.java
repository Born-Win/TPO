package org.example.printter;

public class Sync {
    private static final Object lock = new Object();
    private static volatile char currentChar = '-';

    public static void main(String[] args) {
        Thread thread1 = createThread('-');
        Thread thread2 = createThread('|');

        thread1.start();
        thread2.start();
    }

    private static Thread createThread(char ch) {
        return new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (ch != currentChar) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(ch);
                    currentChar = (ch == '-') ? '|' : '-';
                    lock.notify();
                }
            }
        });
    }
}