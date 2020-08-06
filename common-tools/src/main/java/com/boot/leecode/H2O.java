package com.boot.leecode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class H2O {
    private Semaphore semaphoreH;
    private Semaphore semaphoreO;
    private CyclicBarrier cy = new CyclicBarrier(3, () -> {
        semaphoreH.release(2);
        semaphoreO.release(1);
    });

    public H2O() {
        semaphoreH = new Semaphore(2);
        semaphoreO = new Semaphore(1);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        semaphoreH.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        try {
            cy.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        semaphoreO.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
		releaseOxygen.run();
        try {
            cy.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        H2O h2 = new H2O();
        for (int i = 1; i <=4 ; i++) {
           Thread h1 = new Thread(() -> {
               try {
                   h2.hydrogen(() -> System.out.println("H"));
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           });
           h1.setName("t" + i);
           h1.start();
        }

        for (int i = 1; i <=2 ; i++) {
            Thread O1 = new Thread(() -> {
                try {
                    h2.oxygen(() -> System.out.println("O"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            O1.setName("t" + i);
            O1.start();
        }
    }
}