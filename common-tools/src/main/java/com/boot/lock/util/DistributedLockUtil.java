package com.boot.lock.util;

import com.boot.lock.DistributedLock;
import com.boot.lock.impl.RedisLock;

public class DistributedLockUtil {
    public DistributedLockUtil() {
    }

    public static DistributedLock getDistributedLock(String lockKey) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey);
    }

    public static DistributedLock getDistributedLock(String lockKey, int expireMilliseconds) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey, expireMilliseconds);
    }

    public static DistributedLock getDistributedLock(String lockKey, int expireMilliseconds, int timeoutMilliseconds) {
        lockKey = assembleKey(lockKey);
        return new RedisLock(lockKey, timeoutMilliseconds, expireMilliseconds);
    }

    private static String assembleKey(String lockKey) {
        return String.format("sync_lock_%s", lockKey);
    }
}
