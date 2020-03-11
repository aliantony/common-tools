package com.antiy.asset.runner;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.dao.AssetRunRecordDao;
import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2020-03-10 14:50
 **/
@Component
public class StopRunDayMonitor implements DisposableBean {

    private Logger            logger = LogUtils.get(this.getClass());

    @Resource
    private AssetRunRecordDao runRecordDao;

    @Override
    public void destroy() throws Exception {
        // 获取最新一次启动记录
        AssetRunRecord runRecord = runRecordDao.getLatestRunRecordByStartTime();
        // 记录停止时间
        Long currTime = System.currentTimeMillis();
        AssetRunRecord assetRunRecord = new AssetRunRecord();
        assetRunRecord.setUniqueId(runRecord.getUniqueId());
        assetRunRecord.setStopTime(currTime);
        assetRunRecord.setDifferenceValue(currTime - runRecord.getStartTime());
        logger.info(JSON.toJSONString(assetRunRecord));
        runRecordDao.update(assetRunRecord);
    }
}
