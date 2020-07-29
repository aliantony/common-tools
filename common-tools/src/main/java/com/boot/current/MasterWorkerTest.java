package com.boot.current;

import java.util.Random;

/**
 * @author wq
 * created on --
 * @version ..
 * @program common-tools
 * @description
 */
public class MasterWorkerTest {
    public static void main(String[] args) {
        MasterDemo master = new MasterDemo(new WorkerDemo(), 10);
        for (int i = 0; i < 100; i++) {
            TaskDemo task = new TaskDemo();
            task.setId(i);
            task.setName("任务" + i);
            task.setPrice(new Random().nextInt(10000));
            master.submit(task);
        }

        master.execute();

        while (true) {
            if (master.isComplete()) {
                System.out.println("执行的结果为: " + master.getResult());
                break;
            }
        }
    }
}
