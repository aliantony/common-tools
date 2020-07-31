package com.boot.current;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar2 {
    private int n;
    //非公平锁也能实现，重点是i++放条件里面，让外面循环自旋
    //空平锁可以提高性能，非公平锁可能增加循环次数
    private Lock lock = new ReentrantLock(true);
    private boolean permit = true;

    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n;) {
            lock.lock();
            try {
                if (permit) {
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    permit = false;
                    i ++;
                }
            } finally {
                lock.unlock();
            }
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n;) {
            lock.lock();
            try {
                if (!permit) {
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printBar.run();
                    permit = true;
                    i++;
                }
            } finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        FooBar2 f = new FooBar2(5);
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
