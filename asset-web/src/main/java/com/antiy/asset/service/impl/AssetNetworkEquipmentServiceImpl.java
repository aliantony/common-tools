package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 网络设备详情表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetNetworkEquipmentServiceImpl extends BaseServiceImpl<AssetNetworkEquipment>
                                              implements IAssetNetworkEquipmentService {

    @Resource
    private AssetNetworkEquipmentDao                                            assetNetworkEquipmentDao;
    @Resource
    AssetLinkRelationDao                                                        linkRelationDao;
    @Resource
    private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment>  requestConverter;
    @Resource
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter;
    private static Logger logger = LogUtils.get(AssetNetworkEquipmentServiceImpl.class);
    @Override
    public Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        LogHandle.log(request, AssetEventEnum.ASSET_INSERT.getName(),  AssetEventEnum.ASSET_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger,  AssetEventEnum.ASSET_INSERT.getName() + " {}", request.toString());
        return assetNetworkEquipment.getId();
    }

    @Override
    public Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
        LogHandle.log(request, AssetEventEnum.ASSET_MODIFY.getName(), AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", request.toString());
        return assetNetworkEquipmentDao.update(assetNetworkEquipment);
    }

    @Override
    public List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {

        List<AssetNetworkEquipment> assetNetworkEquipment = assetNetworkEquipmentDao
            .findListAssetNetworkEquipment(query);
        List<AssetNetworkEquipmentResponse> assetNetworkCardResponse = responseConverter.convert(assetNetworkEquipment,
            AssetNetworkEquipmentResponse.class);
        return assetNetworkCardResponse;
    }

    public Integer findCountAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return assetNetworkEquipmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkEquipment(query), query.getCurrentPage(),
            this.findListAssetNetworkEquipment(query));
    }

    @Override
    public List<SelectResponse> queryPortById(AssetNetworkEquipmentQuery query) {
        Integer portAmount = assetNetworkEquipmentDao.findPortAmount();
        List<Integer> portList;
        List<SelectResponse> selectResponseList = null;
        if (portAmount != null) {
            portList = new ArrayList<>();

            // 还原网络设备端口
            if (portAmount > 1) {
                for (int i = 1; i <= portAmount; i++) {
                    portList.add(i);
                }

                // 排除已占用的端口
                List<Integer> usePortList = linkRelationDao.findUsePort(query.getAssetId());
                for (Integer usePort : usePortList) {
                    portList.remove(usePort);
                }
            }

            selectResponseList = new ArrayList<>();
            for (Integer unUsePort : portList) {
                SelectResponse selectResponse = new SelectResponse();
                selectResponse.setValue(DataTypeUtils.integerToString(unUsePort));
                selectResponseList.add(selectResponse);
            }

        }
        return selectResponseList;
    }
}
