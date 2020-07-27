package com.boot.current;

import java.util.concurrent.SynchronousQueue;

class Foo5 {
    private SynchronousQueue<Integer> q1 = new SynchronousQueue();
    private SynchronousQueue<Integer> q2 = new SynchronousQueue();
    public Foo5() {

    }

    public  void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        q1.put(1);
    }

    public  void second(Runnable printSecond) throws InterruptedException {
        q1.take();
        printSecond.run();
        q2.put(1);
    }

    public  void third(Runnable printThird) throws InterruptedException {
        q2.take();
        printThird.run();

    }
}