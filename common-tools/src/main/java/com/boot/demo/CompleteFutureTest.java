package com.boot.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program common-tools
 * @description Future会阻塞或轮循，主线程被迫等待
 * @author wq
 * created on 2020-08-06
 * @version  1.0.0
 */
public class CompleteFutureTest {
    public static void main(String[] args) throws InterruptedException {
        //创建异步执行任务
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompleteFutureTest::fetchPrice);
        //主线程设置好回调后，不再关心异步任务的执行。
        //执行成功的回调
        cf.thenAccept((result) -> System.out.println("price: " + result));
        //执行异常的回调
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        //多个CompletableFuture可以串行执行
        CompletableFuture<String> codeCf = CompletableFuture.supplyAsync(() -> CompleteFutureTest.getCode("中国石油"));
        CompletableFuture<Double> priceCf = codeCf.thenApplyAsync((code) -> getPrice(code));
        priceCf.thenAccept((result) -> System.out.println("financil price: " + result));
        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭

        //并行化示例，只要一个返回就执行后续的打印结果
        CompletableFuture<String> sinaCf = CompletableFuture.supplyAsync(() -> CompleteFutureTest.getCode("中国石油"));
        CompletableFuture<String> wyCf = CompletableFuture.supplyAsync(() -> CompleteFutureTest.getCode("中国石油"));
        CompletableFuture<Object> anyCf = CompletableFuture.anyOf(sinaCf, wyCf);
        CompletableFuture<Double> price = anyCf.thenApplyAsync((code) -> getPrice((String)code));
        TimeUnit.SECONDS.sleep(3);


    }
    public static double fetchPrice() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < 0.5) {
            throw new RuntimeException("fetch price failed");
        }
        return 5 + Math.random() * 20;
    }
    //获取证券代码
    public static String getCode(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "601857";
    }

    //获取证券价格
    public static Double getPrice(String code) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5 + Math.random() * 20;
    }

}
