package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.common.utils.LoginUserUtil;
import org.slf4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.AbstractProcessor;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
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
    private AssetSoftwareRelationDao             assetSoftwareRelationDao;
    @Resource
    private TransactionTemplate                  transactionTemplate;

    @Override
    public Integer changeStatus(SchemeRequest schemeRequest) {
        Scheme scheme = requestConverter.convert(schemeRequest, Scheme.class);
        scheme.setGmtCreate(System.currentTimeMillis());
        // 修改软件状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", new Object[] { schemeRequest.getAssetId() });
        map.put("targetStatus",
            AssetStatusJumpEnum.getNextStatus(schemeRequest.getAssetStatus(), schemeRequest.getIsAgree()));
        map.put("modifyUser", LoginUserUtil.getLoginUser().getId());
        map.put("gmtModified", System.currentTimeMillis());
        Integer count = transactionTemplate.execute(transactionStatus -> {
            int row = 0;
            try {
                schemeDao.insert(scheme);
                super.saveOperationHistory(schemeRequest);
                row = assetSoftwareRelationDao.changeSoftwareStatus(map);
            } catch (Exception e) {
                LOGGER.error("保存信息失败", e);
            }
            return row;
        });
        return count;
    }
}
