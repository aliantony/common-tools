package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.common.encoder.AesEncoder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.AbstractProcessor;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @author zhangyajun
 * @create 2019-01-17 9:01
 **/
@Service
public class AssetStatusProcessor extends AbstractProcessor {
    private static final Logger                    LOGGER = LogUtils.get(AssetSoftwareServiceImpl.class);

    @Resource
    SchemeDao                                      schemeDao;
    @Resource
    AssetDao                                       assetDao;
    @Resource
    protected BaseConverter<SchemeRequest, Scheme> requestConverter;
    @Resource
    private TransactionTemplate                    transactionTemplate;

    @Override
    public Integer changeStatus(SchemeRequest schemeRequest) throws Exception {
        Scheme scheme = requestConverter.convert(schemeRequest, Scheme.class);
        Integer targetStatus = AssetStatusJumpEnum.getNextStatus(schemeRequest.getAssetStatus(),schemeRequest.getIsAgree());
        super.process(AssetOperationTableEnum.ASSET.getMsg(),scheme);

        // 修改资产状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", new Object[] { schemeRequest.getAssetId() });
        map.put("targetStatus", targetStatus);
        map.put("modifyUser", LoginUserUtil.getLoginUser().getId());
        map.put("gmtModified", System.currentTimeMillis());
        Integer schemeId = transactionTemplate.execute(transactionStatus -> {
            try {
                // TODO 调用工作流,推动流程start
                // 开始调用工作流接口,推动流程
                // TODO 调用工作流end
                schemeDao.insert(scheme);
                assetDao.changeStatus(map);
            } catch (Exception e) {
                LOGGER.error("保存信息失败", e);
            }
            return scheme.getId();
        });
        return schemeId;
    }
}
