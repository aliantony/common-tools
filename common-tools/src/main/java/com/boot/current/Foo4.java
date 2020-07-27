package com.boot.current;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Foo4 {
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    public Foo4() {

    }

    public  void first(Runnable printFirst) throws InterruptedException {
        lock.lock();
        printFirst.run();
        c1.signal();
        lock.unlock();
    }

    public  void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        c1.await();
        printSecond.run();
        c2.signal();
        lock.unlock();
    }

    public  void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        c2.await();
        printThird.run();
        lock.unlock();
    }
}