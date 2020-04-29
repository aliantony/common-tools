package com.antiy.asset.service.impl;

import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liulusheng
 * @since 2020/4/23
 */
public class A {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test() throws InterruptedException {
        long time = System.currentTimeMillis();
        Runnable runnable = () -> {
            System.out.println(sdf.format(new Date(time)));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        int i = 0;
        while (true) {
            Thread thread=new Thread(runnable);
            thread.start();
            System.out.println(i++);
            if (i >= 100) {
                break;
            }
        }

    }
}
