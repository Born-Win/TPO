package org.example.counter;

public class CounterSyncBlock implements ICounter {
    private int value = 0;
    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void increment() {
        synchronized(this) {
            this.value++;
        }
    }

    @Override
    public void decrement() {
        synchronized(this) {
            this.value--;
        }
    }

    @Override
    public void resetCounter() {
        this.value = 0;
    }
}
