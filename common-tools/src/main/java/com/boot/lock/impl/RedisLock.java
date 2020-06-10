package com.boot.lock.impl;

import com.boot.lock.DistributedLock;
import com.boot.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisLock implements DistributedLock {
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);
    private static StringRedisTemplate redisTemplate;
    private String lockKey;
    private int expireMilliseconds = 15000;
    private int timeoutMilliseconds = 15000;
    private boolean locked = false;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

    public RedisLock(String lockKey, int timeoutMilliseconds) {
        this.lockKey = lockKey;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public RedisLock(String lockKey, int expireMilliseconds, int timeoutMilliseconds) {
        this.lockKey = lockKey;
        this.expireMilliseconds = expireMilliseconds;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public String getLockKey() {
        return this.lockKey;
    }

    @Override
    public synchronized boolean acquire() {
        int timeout = this.timeoutMilliseconds;
        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        }

        try {
            while(timeout >= 0) {
                long expires = System.currentTimeMillis() + (long)this.expireMilliseconds + 1L;
                String expiresStr = String.valueOf(expires);
                if (redisTemplate.opsForValue().setIfAbsent(this.lockKey, expiresStr)) {
                    this.locked = true;
                    log.info("[1] 成功获取分布式锁!");
                    return true;
                }

                String redisValueTime = (String)redisTemplate.opsForValue().get(this.lockKey);
                if (redisValueTime != null && Long.parseLong(redisValueTime) < System.currentTimeMillis()) {
                    String oldValueStr = (String)redisTemplate.opsForValue().getAndSet(this.lockKey, expiresStr);
                    if (oldValueStr != null && oldValueStr.equals(redisValueTime)) {
                        this.locked = true;
                        log.info("[2] 成功获取分布式锁!");
                        return true;
                    }
                }

                timeout -= 100;
                Thread.sleep(100L);
            }
        } catch (Exception var7) {
            log.error("获取锁出现异常, 必须释放: {}", var7.getMessage());
        }

        return false;
    }

    @Override
    public synchronized void release() {
        if (redisTemplate == null) {
            redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        }
        try {
            if (this.locked) {
                String redisValueTime = redisTemplate.opsForValue().get(this.lockKey);
                if (redisValueTime != null && Long.parseLong(redisValueTime) > System.currentTimeMillis()) {
                    redisTemplate.delete(this.lockKey);
                    this.locked = false;
                    log.info("[3] 成功释放分布式锁!");
                }
            }
        } catch (Exception var2) {
            log.error("释放锁出现异常, 必须释放: {}", var2.getMessage());
        }

    }
}
