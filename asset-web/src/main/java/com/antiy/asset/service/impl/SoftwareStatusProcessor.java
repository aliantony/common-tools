package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.common.exception.BusinessException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.AbstractProcessor;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.SoftwareStatusJumpEnum;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2019-01-19 9:01
 **/
@Service
public class SoftwareStatusProcessor extends AbstractProcessor {
    private static final Logger LOGGER = LogUtils.get(AssetSoftwareServiceImpl.class);

    @Resource
    private SchemeDao           schemeDao;
    @Resource
    private AssetSoftwareDao    assetSoftwareDao;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public Integer changeStatus(SchemeRequest schemeRequest, Scheme scheme) throws Exception {
        Integer targetStatus = SoftwareStatusJumpEnum.getNextStatus(schemeRequest.getAssetStatus(),
            schemeRequest.getIsAgree());
        // 修改状态
        if (targetStatus != -1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", schemeRequest.getAssetId());
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
                    assetSoftwareDao.changeStatusById(map);
                    super.process(AssetOperationTableEnum.SOFTWARE.getMsg(), schemeRequest, scheme);
                } catch (Exception e) {
                    LOGGER.error("保存信息失败", e);
                }
                return scheme.getId();
            });
        }else {
            throw new BusinessException("修改状态失败：无法获取下一个状态");
        }

    }
}
