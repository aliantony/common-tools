package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetLinkedCountResponse;
import com.antiy.asset.vo.response.IpPortResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@Service
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation>
                                          implements IAssetLinkRelationService {

    private static final Logger                                         logger = LogUtils.get();

    @Resource
    private AssetLinkRelationDao                                        assetLinkRelationDao;
    @Resource
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;
    @Resource
    private AssetDao                                                    assetDao;
    @Resource
    private RedisUtil                                                   redisUtil;

    @Override
    public Boolean saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        checkAssetIp(request, assetLinkRelation);
        assetLinkRelationDao.insert(assetLinkRelation);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_INSERT.getName(),
            DataTypeUtils.stringToInteger(request.getAssetId()), assetDao.getById(request.getAssetId()).getNumber(),
            assetLinkRelation, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_INSERT.getName() + "{}", assetLinkRelation);
        return StringUtils.isNotBlank(assetLinkRelation.getStringId()) ? true : false;
    }

    /**
     * 检查IP信息
     * @param request
     * @param assetLinkRelation
     */
    public void checkAssetIp(AssetLinkRelationRequest request, AssetLinkRelation assetLinkRelation) {
        assetLinkRelation.setAssetId(request.getAssetId());
        assetLinkRelation.setParentAssetId(request.getParentAssetId());
        // 1.校验子资产IP是否可用
        Integer count = assetLinkRelationDao.checkIp(request.getAssetId(), request.getAssetIp(),
            request.getAssetPort());
        if (count > 0) {
            throw new BusinessException("所选设备IP已存在绑定关系，请勿重复设置");
        }
        // 2.校验父资产IP是否可用
        count = assetLinkRelationDao.checkIp(request.getParentAssetId(), request.getParentAssetIp(),
            request.getParentAssetPort());
        if (count > 0) {
            throw new BusinessException("关联设备IP已存在绑定关系，请勿重复设置");
        }

        // 3.组装通联关系
        assetLinkRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLinkRelation.setGmtCreate(System.currentTimeMillis());
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinekedRelationPage(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (assetLinkRelationResponseList.size() <= 0) {
            return new PageResult<>(assetLinkRelationQuery.getPageSize(), 0, assetLinkRelationQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(assetLinkRelationQuery.getPageSize(), assetLinkRelationResponseList.size(),
            assetLinkRelationQuery.getCurrentPage(), this.queryLinekedRelationList(assetLinkRelationQuery));
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }

    @Override
    public List<IpPortResponse> queryUseableIp(UseableIpRequest useableIpRequest) {
        return assetLinkRelationDao.queryUseableIp(useableIpRequest.getAssetId());
    }

    @Override
    public String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        String assetId = assetLinkRelationDao.getById(baseRequest.getStringId()).getAssetId();
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_DELETE.getName(),
            DataTypeUtils.stringToInteger(assetId), assetDao.getById(assetId).getNumber(), baseRequest,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_DELETE.getName() + "{}", baseRequest.getStringId());

        return assetLinkRelationDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public PageResult<AssetLinkedCountResponse> queryAssetLinkedCountPage(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<String> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode().toString());
        statusList.add(AssetStatusEnum.NET_IN.getCode().toString());
        assetLinkRelationQuery.setStatusList(statusList);
        // 区域条件
        if (CollectionUtils.isEmpty(assetLinkRelationQuery.getAreaIds())) {
            assetLinkRelationQuery.setAreaIds(Arrays.asList(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList()))));
        }
        List<AssetLinkedCountResponse> assetLinkedCountResponseList = this
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkedCountResponseList)) {
            return new PageResult<>(assetLinkRelationQuery.getPageSize(), 0, assetLinkRelationQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(assetLinkRelationQuery.getPageSize(),
            this.queryAssetLinkedCount(assetLinkRelationQuery), assetLinkRelationQuery.getCurrentPage(),
            assetLinkedCountResponseList);
    }

    private Integer queryAssetLinkedCount(AssetLinkRelationQuery assetLinkRelationQuery) {
        return assetLinkRelationDao.queryAssetLinkedCount(assetLinkRelationQuery);
    }

    @Override
    public List<AssetLinkedCountResponse> queryAssetLinkedCountList(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<AssetLinkedCount> assetResponseList = assetLinkRelationDao
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        assetResponseList.stream().forEach(assetLinkedCount -> {
            String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(assetLinkedCount.getAreaId()));
            try {
                SysArea sysArea = redisUtil.getObject(newAreaKey, SysArea.class);
                if (!Objects.isNull(sysArea)) {
                    assetLinkedCount.setAreaName(sysArea.getFullName());
                }
            } catch (Exception e) {
                LogUtils.info(logger, "{}  获取区域失败", RespBasicCode.BUSSINESS_EXCETION);
            }
        });
        return BeanConvert.convert(assetResponseList, AssetLinkedCountResponse.class);
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinkedAssetListByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao
            .queryLinkedAssetListByAssetId(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }

    public Integer queryLinkedCountAssetByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        return assetLinkRelationDao.queryLinkedCountAssetByAssetId(assetLinkRelationQuery);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinkedAssetPageByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinkedAssetListByAssetId(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkRelationResponseList)) {
            return new PageResult<>(assetLinkRelationQuery.getPageSize(), 0, assetLinkRelationQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(assetLinkRelationQuery.getPageSize(),
            this.queryLinkedCountAssetByAssetId(assetLinkRelationQuery), assetLinkRelationQuery.getCurrentPage(),
            assetLinkRelationResponseList);
    }
}
