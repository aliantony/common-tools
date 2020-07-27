package com.boot.current;

import java.util.concurrent.CountDownLatch;

class Foo2 {
    private CountDownLatch s1 = new CountDownLatch(1);
    private CountDownLatch s2 = new CountDownLatch(1);

    public Foo2() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            s1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
            s1.await();
            printSecond.run();
            s2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
            s2.await();
            printThird.run();
    }
}