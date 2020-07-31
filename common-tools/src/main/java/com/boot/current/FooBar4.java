package com.boot.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @program common-tools
 * @description 
 * @author wq 无锁实现交替打印
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar4 {
    private int n;

    public FooBar4(int n) {
        this.n = n;
    }

    CyclicBarrier cb = new CyclicBarrier(2);
    volatile boolean fin = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(!fin);
            printFoo.run();
            fin = false;
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
            }
            printBar.run();
            fin = true;
        }
    }

    public static void main(String[] args) {
        FooBar4 f = new FooBar4(5);
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
