package com.boot.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wq
 * created on 2020-07-31
 * @version 1.0.0
 * @program common-tools
 * @description
 */
public class FooBar8 {
    private int n;
    Lock lock = new ReentrantLock();
    Condition fooTurn = lock.newCondition();
    Condition barTurn = lock.newCondition();
    boolean flag = false;

    public FooBar8(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (flag) {
                    fooTurn.await();
                }
                flag = true;
                printFoo.run();
                barTurn.signal();
            } finally {
                lock.unlock();
            }

        }
    }

    public void bar(Runnable printBar) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (!flag) {
                    barTurn.await();
                }
                flag = false;
                printBar.run();
                fooTurn.signal();
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        FooBar8 f = new FooBar8(5);
        Thread t1 = new Thread(() -> {
            try {
                f.foo(() -> System.out.print("foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                f.bar(() -> System.out.print("bar"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
