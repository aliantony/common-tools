package com.boot.current;

/**
 * @program common-tools
 * @description 
 * @author wq 无锁实现交替打印
 * created on 2020-07-31
 * @version  1.0.0
 */
public class FooBar5 {
    private int n;

    public FooBar5(int n) {
        this.n = n;
    }

    volatile boolean fin = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(!fin);
            printFoo.run();
            fin = false;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(fin);
            printBar.run();
            fin = true;
        }
    }

    public static void main(String[] args) {
        FooBar5 f = new FooBar5(5);
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
