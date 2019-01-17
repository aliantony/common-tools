package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.AbstractProcessor;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2019-01-17 9:01
 **/
public class SoftwarStatusProcessor extends AbstractProcessor {
    private static final Logger                  LOGGER = LogUtils.get(AssetSoftwareServiceImpl.class);
    @Resource
    private BaseConverter<SchemeRequest, Scheme> requestConverter;

    @Resource
    private SchemeDao                            schemeDao;
    @Resource
    private AssetSoftwareDao                     assetSoftwareDao;
    @Resource
    private TransactionTemplate                  transactionTemplate;

    @Override
    public Integer changeStatus(SchemeRequest schemeRequest) {
        Scheme scheme = requestConverter.convert(schemeRequest, Scheme.class);
        scheme.setGmtCreate(System.currentTimeMillis());

        // 修改资产状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", new Object[] { schemeRequest.getAssetId() });
        map.put("targetStatus", schemeRequest.getTargetStatus());
        Integer count = transactionTemplate.execute(transactionStatus -> {
            int row = 0;
            try {
                // TODO 调用工作流,推动流程start
                // 开始调用工作流接口,推动流程
                // TODO 调用工作流end
                schemeDao.insert(scheme);
                // row = assetSoftwareDao.changeStatus(map);
            } catch (Exception e) {
                LOGGER.error("保存信息失败", e);
            }
            return row;
        });
        return count;
    }
}
