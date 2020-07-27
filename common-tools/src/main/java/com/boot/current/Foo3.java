package com.boot.current;

class Foo3 {
    private Integer flag = 1;
    public Foo3() {

    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            flag++;
            notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
           while (flag != 2) {
              wait();
           }
           printSecond.run();
           flag++;
           notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (flag != 3) {
           wait();
        }
        printThird.run();
    }
}