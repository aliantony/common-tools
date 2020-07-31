package com.boot.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar6 {
    private int n;
    CountDownLatch latch = null;
    CyclicBarrier by = null;

    public FooBar6(int n) {
        this.n = n;
        latch = new CountDownLatch(1);
        by = new CyclicBarrier(2);
    }

    public void foo(Runnable printFoo) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            printFoo.run();
            latch.countDown(); // printFoo方法完成调用countDown
            by.await(); // 等待printBar方法执行完成
        }
    }

    public void bar(Runnable printBar) throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            latch.await();// 等待printFoo方法先执行
            printBar.run();
            // 保证下一次依旧等待printFoo方法先执行
            latch = new CountDownLatch(1);
            by.await();// 等待printFoo方法执行完成
        }
    }

    public static void main(String[] args) {
        FooBar6 f = new FooBar6(5);
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
