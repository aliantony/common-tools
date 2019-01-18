package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 安全设备详情表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSafetyEquipmentServiceImpl extends BaseServiceImpl<AssetSafetyEquipment>
                                             implements IAssetSafetyEquipmentService {

    @Resource
    private AssetSafetyEquipmentDao                                           assetSafetyEquipmentDao;
    @Resource
    AesEncoder                                                                aesEncoder;
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
        return aesEncoder.decode(assetSafetyEquipment.getId().toString(),LoginUserUtil.getLoginUser().getPassword());
    }

    @Override
    public Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
        AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
        assetSafetyEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
        return assetSafetyEquipmentDao.update(assetSafetyEquipment);
    }

    @Override
    public List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
        List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSafetyEquipmentList, AssetSafetyEquipmentResponse.class);
    }

    @Override
    public PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSafetyEquipment(query));
    }

    @Override
    public AssetSafetyEquipmentResponse findSafetyEquipmentById(String id) throws Exception {
        return responseConverter.convert(assetSafetyEquipmentDao.getById(id), AssetSafetyEquipmentResponse.class);
    }
}
