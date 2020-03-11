package com.antiy.asset.task;

import org.slf4j.Logger;

import com.antiy.asset.dao.AssetRunRecordDao;
import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2020-03-10 17:10
 **/
public class RunDayTask implements Runnable {
    private Logger            logger = LogUtils.get(this.getClass());

    private AssetRunRecordDao runRecordDao;

    public RunDayTask(AssetRunRecordDao dao) {
        this.runRecordDao = dao;
    }

    public AssetRunRecordDao getRunRecordDao() {
        return runRecordDao;
    }

    public void setRunRecordDao(AssetRunRecordDao runRecordDao) {
        this.runRecordDao = runRecordDao;
    }

    @Override
    public void run() {
        try {
            AssetRunRecord assetRunRecord = runRecordDao.getLatestRunRecordByStartTime();
            if (assetRunRecord != null) {
                Long startTime = assetRunRecord.getStartTime();
                Long currTime = System.currentTimeMillis();
                assetRunRecord.setStopTime(currTime);
                assetRunRecord.setDifferenceValue(currTime - startTime);
                runRecordDao.update(assetRunRecord);
            }
        } catch (Exception e) {
            logger.error("获取系统运数据错误");
        }
    }
}
