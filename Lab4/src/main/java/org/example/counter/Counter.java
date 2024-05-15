package org.example.counter;


public class Counter implements ICounter {
    private int value = 0;

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void increment() {
        this.value++;
    }

    @Override
    public void decrement() {
        this.value--;
    }

    @Override
    public void resetCounter() {
        this.value = 0;
    }
}
