package com.antiy.asset.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.entity.AssetSoftwareRelationMapper;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.InstallStatus;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.response.AssetResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LoginUserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> 资产软件关系信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl<AssetSoftwareRelation>
                                              implements IAssetSoftwareRelationService {

    @Resource
    private AssetSoftwareRelationDao                                            assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationResponse> responseConverter;
    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareResponse>                 responseSoftConverter;

    @Override
    public Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        assetSoftwareRelationDao.insert(assetSoftwareRelation);
        return assetSoftwareRelation.getId();
    }

    @Override
    public Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        // TODO 添加修改人信息
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        assetSoftwareRelation.setGmtModified(System.currentTimeMillis());
        return assetSoftwareRelationDao.update(assetSoftwareRelation);
    }

    @Override
    public List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao.findQuery(query);
        List<AssetSoftwareRelationResponse> assetSoftwareRelationResponse = responseConverter
            .convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class);
        return assetSoftwareRelationResponse;
    }

    @Override
    public PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSoftwareRelation(query));
    }

    @Override
    public List<AssetSoftwareResponse> getSoftByAssetId(Integer assetId) {
        List<AssetSoftware> assetSoftwareRelationList = assetSoftwareRelationDao.getSoftByAssetId(assetId);
        List<AssetSoftwareResponse> assetSoftwareRelationResponse = responseSoftConverter
            .convert(assetSoftwareRelationList, AssetSoftwareResponse.class);
        return assetSoftwareRelationResponse;
    }

    @Override
    public Integer countAssetBySoftId(Integer id) {
        return assetSoftwareRelationDao.countAssetBySoftId(id);
    }

    @Override
    public List<String> findOS() throws Exception {
        return assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
    }

    @Override
    public Integer changeSoftwareStatus(Map<String, Object> map) throws Exception {
        return assetSoftwareRelationDao.changeSoftwareStatus(map);
    }

    @Override
    public Integer installArtificial(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        return assetSoftwareRelationDao.installArtificial(assetSoftwareRelation);
    }

    @Override
    public Integer installAauto(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        // TODO 下发智甲安装
        return assetSoftwareRelationDao.installAauto(assetSoftwareRelation);
    }

    @Override
    public Integer installSoftware(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> relationList = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        relationList.stream().forEach(relation -> {
            if (relation.getInstallType().equals(InstallType.AUTOMATIC.getCode())) {
                relation.setInstallStatus(InstallStatus.INSTALLING.getCode());
            }
        });
        assetSoftwareRelationDao.installSoftware(relationList);
        return null;
    }
}
