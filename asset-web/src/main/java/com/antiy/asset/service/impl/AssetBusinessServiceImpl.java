package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetBusinessDao;
import com.antiy.asset.dao.AssetBusinessRelationDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetBusiness;
import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.*;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
@Service
public class AssetBusinessServiceImpl extends BaseServiceImpl<AssetBusiness> implements IAssetBusinessService {

        private Logger logger = LogUtils.get(this.getClass());

        @Resource
        private AssetBusinessDao assetBusinessDao;
        @Resource
        private BaseConverter<AssetBusinessRequest, AssetBusiness>  requestConverter;
        @Resource
        private BaseConverter<AssetBusiness, AssetBusinessResponse> responseConverter;
        @Resource
        private BaseConverter<Asset,AssetResponse> assetConverter;

        @Resource
        private BaseConverter<AssetBusinessRelationRequest,AssetBusinessRelation> relationRequestConverter;
        @Resource
        private AssetBusinessRelationDao assetBusinessRelationDao;
        @Resource
        private AssetDao assetDao;
        @Transactional(rollbackFor = Exception.class)
        @Override
        public String saveAssetBusiness(AssetBusinessRequest request) throws Exception {
            String name = request.getName();
            AssetBusiness business= assetBusinessDao.getByName(name);
            if(business!=null){
                throw new  BusinessException("业务名不能重复！");
            }
            AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
            assetBusiness.setUniqueId(SnowFlakeUtil.getSnowId());
            assetBusiness.setGmtCreate(System.currentTimeMillis());
            assetBusiness.setGmtModified(System.currentTimeMillis());
            assetBusinessDao.insert(assetBusiness);
            List<AssetBusinessRelationRequest> list = request.getAssetRelaList();
            List<AssetBusinessRelation> assetRelationList=new ArrayList<>();
            for(AssetBusinessRelationRequest itme:list){

                Asset asset = assetDao.getById(itme.getAssetId());
                if(!(AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())||
                        AssetStatusEnum.IN_CHANGE.getCode().equals(asset.getAssetStatus())||
                        AssetStatusEnum.WAIT_RETIRE_CHECK.getCode().equals(asset.getAssetStatus())||
                   AssetStatusEnum.RETIRE_DISAGREE.getCode().equals(asset.getAssetStatus()))){
                    throw  new BusinessException("资产状态不合符流程！");
                }
                AssetBusinessRelation assetBusinessRelation=new AssetBusinessRelation();
                assetBusinessRelation.setAssetId(itme.getAssetId());
                assetBusinessRelation.setBusinessInfluence(itme.getBusinessInfluence());
                assetBusinessRelation.setGmtCreate(System.currentTimeMillis());
                assetBusinessRelation.setAssetBusinessId(assetBusiness.getId());
                assetBusinessRelation.setUniqueId(assetBusiness.getUniqueId());
                assetBusinessRelation.setGmtCreate(System.currentTimeMillis());
                assetBusinessRelation.setGmtModified(System.currentTimeMillis());
                assetRelationList.add(assetBusinessRelation);
            }
            assetBusinessRelationDao.insertBatch(assetRelationList);
            return assetBusiness.getStringId();
        }
        @Transactional(rollbackFor = Exception.class)
        @Override
        public String updateAssetBusiness(AssetBusinessRequest request) throws Exception {
            ParamterExceptionUtils.isNull(request.getUniqueId(),"唯一键不能为空！");
            ParamterExceptionUtils.isNull(request.getId(),"业务id不能为空！");
            String name = request.getName();
            AssetBusiness business= assetBusinessDao.getByName(name);
            if(business!=null && !business.getUniqueId().equals(request.getUniqueId())){
                throw new  BusinessException("业务名不能重复！");
            }
            AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
            String uniqueId=assetBusiness.getUniqueId();
            assetBusiness.setUniqueId(null);
            assetBusiness.setGmtModified(System.currentTimeMillis());
            Integer assetBusinessId = assetBusinessDao.update(assetBusiness);
            assetBusiness.setUniqueId(uniqueId);
            assetBusinessRelationDao.deleteByUniqueId(uniqueId);
            List<AssetBusinessRelationRequest> assetRelaList = request.getAssetRelaList();
            List<AssetBusinessRelation> assetBusinessRelationList = relationRequestConverter.convert(assetRelaList, AssetBusinessRelation.class);
            assetBusinessRelationList.forEach(t->{
                t.setGmtModified(System.currentTimeMillis());
                t.setGmtCreate(System.currentTimeMillis());
                t.setUniqueId(request.getUniqueId());
                t.setAssetBusinessId(request.getId());
            });
            assetBusinessRelationDao.insertBatch(assetBusinessRelationList);
            return assetBusinessId.toString();
        }

        @Override
        public List<AssetBusinessResponse> queryListAssetBusiness(AssetBusinessQuery query) throws Exception {
            List<AssetBusiness> assetBusinessList = assetBusinessDao.findQuery(query);
            return responseConverter.convert(assetBusinessList,AssetBusinessResponse.class);
        }

        @Override
        public PageResult<AssetBusinessResponse> queryPageAssetBusiness(AssetBusinessQuery query) throws Exception {
                return new PageResult<AssetBusinessResponse>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.queryListAssetBusiness(query));
        }

        @Override
        public AssetBusinessResponse queryAssetBusinessById(QueryCondition queryCondition) throws Exception{
             ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
             AssetBusinessResponse assetBusinessResponse = responseConverter
                .convert(assetBusinessDao.getById(queryCondition.getPrimaryKey()), AssetBusinessResponse.class);
             return assetBusinessResponse;
        }

        @Override
        public String deleteAssetBusinessById(BaseRequest baseRequest) throws Exception{
             ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
                return assetBusinessDao.deleteById(baseRequest.getStringId()).toString();
        }

    @Override
    public PageResult<AssetResponse> queryAsset(AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception {
        List<String> areaId = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        String[] strings = areaId.toArray(new String[0]);
        assetAddOfBusinessQuery.setAreaIds(strings);
        Integer count=assetDao.countQueryAsset(assetAddOfBusinessQuery);
        if(count>0){
            List<AssetResponse> assetList=assetDao.queryAsset(assetAddOfBusinessQuery);
            return  new PageResult<>(assetAddOfBusinessQuery.getPageSize(),count,assetAddOfBusinessQuery.getCurrentPage(),assetList);
        }
       return  new PageResult<>(assetAddOfBusinessQuery.getPageSize(),0,assetAddOfBusinessQuery.getCurrentPage(), Lists.newArrayList());
    }
    @Override
    public List<AssetBusinessRelationResponse> queryAssetByBusinessId(AssetAddOfBusinessQuery assetAddOfBusinessQuery) {
        List<String> areaIdsOfCurrentUser = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        String[] strings = areaIdsOfCurrentUser.toArray(new String[0]);
        assetAddOfBusinessQuery.setAreaIds(strings);
        List<AssetBusinessRelationResponse> assetList=assetBusinessRelationDao.queryAssetByBusinessId(assetAddOfBusinessQuery);
        return  assetList;

    }

    @Override
    public AssetBusinessResponse getByUniqueId(String uniqueId) {
        AssetBusiness assetBusiness=assetBusinessDao.getByUniqueId(uniqueId);
        AssetBusinessResponse assetBusinessResponse=responseConverter.convert(assetBusiness,AssetBusinessResponse.class);
        return assetBusinessResponse;
    }

    @Override
    public Integer updateStatusByUniqueId(List<String> uniqueId) {

        return assetBusinessDao.updateStatusByUniqueId(uniqueId);
    }
}
