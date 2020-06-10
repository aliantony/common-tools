package com.boot.lock;

public interface DistributedLock {
    boolean acquire();
    void release();
}
