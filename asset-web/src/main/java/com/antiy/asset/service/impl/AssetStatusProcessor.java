package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.AbstractProcessor;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.SpringUtil;

/**
 * @author zhangyajun
 * @create 2019-01-17 9:01
 **/
@Component
public class AssetStatusProcessor extends AbstractProcessor {
    private static final Logger LOGGER = LogUtils.get(AssetSoftwareServiceImpl.class);

    @Resource
    private SchemeDao           schemeDao;
    @Resource
    private AssetDao            assetDao;

    @Override
    public Integer changeStatus(SchemeRequest schemeRequest, Scheme scheme) throws Exception {
        TransactionTemplate transactionTemplate = (TransactionTemplate) SpringUtil.getBean("transactionTemplate");
        Integer targetStatus = AssetStatusJumpEnum.getNextStatus(schemeRequest.getAssetStatus(),
            schemeRequest.getIsAgree());
        if (targetStatus != -1) {
            // 修改状态
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ids", new Object[] { scheme.getAssetId() });
            map.put("targetStatus", targetStatus);
            // TODO 暂时获取不到用户ID，先屏蔽
            // map.put("modifyUser", LoginUserUtil.getLoginUser().getId());
            map.put("gmtModified", System.currentTimeMillis());
            return transactionTemplate.execute(transactionStatus -> {
                try {
                    // TODO 调用工作流,推动流程start
                    // 开始调用工作流接口,推动流程
                    // TODO 调用工作流end

                    schemeDao.insert(scheme);
                    assetDao.changeStatus(map);
                    super.process(AssetOperationTableEnum.ASSET.getMsg(), schemeRequest, scheme);
                } catch (Exception e) {
                    LOGGER.error("保存信息失败", e);
                }
                return scheme.getId();
            });
        } else {
            throw new BusinessException("修改状态失败：无法获取下一个状态");
        }
    }
}
