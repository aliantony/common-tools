package com.antiy.asset.runner;

import java.text.SimpleDateFormat;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.antiy.asset.dao.AssetRunRecordDao;
import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.asset.task.RunDayTask;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2020-03-10 14:23
 **/
@Component
public class StartRunDayMonitor implements ApplicationRunner {
    private Logger            logger = LogUtils.get(this.getClass());

    @Resource
    private AssetRunRecordDao runRecordDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long currTime = System.currentTimeMillis();
        // 记录启动时间
        AssetRunRecord assetRunRecord = new AssetRunRecord();
        assetRunRecord.setUniqueId(SnowFlakeUtil.getSnowId());
        assetRunRecord.setStartTime(currTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        logger.info("记录系统启动时间： " + sdf.format(currTime));
        runRecordDao.insert(assetRunRecord);

        // 定时任务
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new RunDayTask(runRecordDao), 5, 1, TimeUnit.HOURS);
    }
}
