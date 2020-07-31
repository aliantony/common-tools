package com.boot.current;

import java.util.concurrent.BrokenBarrierException;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar7 {
    private int n;
    Object lock = new Object();
    private boolean flag = false;
    public FooBar7(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                if (flag){
                    lock.wait();
                }
                flag = true;
                printFoo.run();
                lock.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                if (!flag) {
                    lock.wait();
                }
                flag = false;
                printBar.run();
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        FooBar7 f = new FooBar7(5);
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
