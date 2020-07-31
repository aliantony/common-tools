package com.boot.current;

import java.util.concurrent.Semaphore;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar {
    private int n;
    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            s1.acquire();
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                s2.release();
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            s2.acquire();
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                s1.release();
        }

    }

    public static void main(String[] args) {
        FooBar f = new FooBar(3);
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
