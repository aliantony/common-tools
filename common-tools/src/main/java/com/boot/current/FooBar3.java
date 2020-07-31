package com.boot.current;

/**
 * @program common-tools
 * @description 
 * @author wq 无锁实现交替打印
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar3 {
    private int n;
    private volatile boolean permit = true;

    public FooBar3(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n;) {
                if (permit) {
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    permit = false;
                    i ++;
                }
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n;) {
            if (!permit) {
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printBar.run();
                permit = true;
                i ++;
            }
        }

    }

    public static void main(String[] args) {
        FooBar3 f = new FooBar3(5);
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
