package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 安全设备详情表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AssetSafetyEquipmentServiceImpl extends BaseServiceImpl<AssetSafetyEquipment>
                                             implements IAssetSafetyEquipmentService {
    private Logger                                                            logger = LogUtils.get(this.getClass());
    @Resource
    private AssetSafetyEquipmentDao                                           assetSafetyEquipmentDao;
    @Resource
    private AesEncoder                                                                aesEncoder;
    @Resource
    private AssetDao                                                          assetDao;
    @Resource
    private BaseConverter<AssetSafetyEquipmentRequest, AssetSafetyEquipment>  requestConverter;
    @Resource
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse> responseConverter;
    @Override
    public String saveAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
        AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
        assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
        assetSafetyEquipmentDao.insert(assetSafetyEquipment);
        // 记录操作日志和运行日志

        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_SAFETY_EQUIPMENT_INSERT.getName(),
            DataTypeUtils.stringToInteger(request.getAssetId()), assetDao.getById(request.getAssetId()).getNumber(),
            assetSafetyEquipment, BusinessModuleEnum.SAFETY,
            BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName() + " {}", assetSafetyEquipment);
        return aesEncoder.decode(assetSafetyEquipment.getStringId(),LoginUserUtil.getLoginUser().getUsername());
    }

    @Override
    public Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
        AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
        assetSafetyEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
        // 记录操作日志和运行日志
        // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_SAFETY_EQUIPMENT_UPDATE.getName(),
        // DataTypeUtils.stringToInteger(request.getAssetId()), assetDao.getById(request.getAssetId()).getNumber(),
        // assetSafetyEquipment, BusinessModuleEnum.SAFETY,
        // BusinessPhaseEnum.NONE));
        // LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName() + " {}", assetSafetyEquipment);
        return assetSafetyEquipmentDao.update(assetSafetyEquipment);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
        List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSafetyEquipmentList, AssetSafetyEquipmentResponse.class);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSafetyEquipment(query));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AssetSafetyEquipmentResponse findSafetyEquipmentById(String id) throws Exception {
        return responseConverter.convert(assetSafetyEquipmentDao.getById(id), AssetSafetyEquipmentResponse.class);
    }
}
