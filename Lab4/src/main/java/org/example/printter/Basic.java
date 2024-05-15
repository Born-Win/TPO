package org.example.printter;

public class Basic {
    public static void main(String[] args) {
        Thread thread1 = createThread('-');

        Thread thread2 = createThread('|');

        thread1.start();
        thread2.start();

        System.out.println();
    }

    private static Thread createThread(char ch) {
        return new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.print(ch);
            }
        });
    }
}
