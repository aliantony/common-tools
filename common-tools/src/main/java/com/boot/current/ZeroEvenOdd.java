package com.boot.current;

import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private Object lock = new Object();
    private int state = 1;
    private boolean isZero = true;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            synchronized (lock) {
                while (!isZero) {
                    lock.wait();
                    if (state > n)return;
                }
                printNumber.accept(0);
                isZero = false;
                lock.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            synchronized (lock) {
                while (state % 2 != 0 || isZero) {
                    lock.wait();
                    if (state > n)return;
                }
                printNumber.accept(state);
                state++;
                isZero = true;
                lock.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            synchronized (lock) {
                while (state % 2 != 1 || isZero) {
                    lock.wait();
                    if (state > n)return;
                }
                printNumber.accept(state);
                state++;
                isZero = true;
                lock.notifyAll();
            }
        }

    }

    public static void main(String[] args) {
        ZeroEvenOdd fo = new ZeroEvenOdd(7);
        Thread t1 = new Thread(() -> {
            try {
                fo.zero((i) -> System.out.print(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                fo.even((i) -> System.out.print(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                fo.odd((i) -> System.out.print(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}