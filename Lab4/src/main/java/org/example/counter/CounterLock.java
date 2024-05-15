package org.example.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class CounterLock implements ICounter  {
    private int value = 0;
    private Lock locker = new ReentrantLock();

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void increment() {
        try {
            locker.lock();
            this.value++;
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void decrement() {
        try {
            locker.lock();
            this.value--;
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void resetCounter() {
        this.value = 0;
    }
}

