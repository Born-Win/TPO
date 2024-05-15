package org.example.counter;

public class CounterSync implements ICounter{
    private int value = 0;

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public synchronized void increment() {
        this.value++;
    }

    @Override
    public synchronized void decrement() {
        this.value--;
    }

    @Override
    public void resetCounter() {
        this.value = 0;
    }
}
