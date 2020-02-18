package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetBusinessDao;
import com.antiy.asset.dao.AssetBusinessRelationDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetBusiness;
import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.common.base.*;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
        private AssetBusinessRelationDao assetBusinessRelationDao;
        @Resource
        private AssetDao assetDao;
        @Override
        public String saveAssetBusiness(AssetBusinessRequest request) throws Exception {
            String name = request.getName();
            AssetBusiness business= assetBusinessDao.getByName(name);
            if(business!=null){
                throw new  BusinessException("业务名不能重复！");
            }
            AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
            assetBusinessDao.insert(assetBusiness);
            List<AssetBusinessRelationRequest> list = request.getAssetRelaList();
            List<AssetBusinessRelation> assetRelationList=new ArrayList<>();
            for(AssetBusinessRelationRequest itme:list){
                AssetBusinessRelation data= assetBusinessRelationDao.getByAssetId(itme.getAssetId());
                if(data!=null){
                    throw new  BusinessException("不能重复关联资产！");
                }
                Asset asset = assetDao.getById("2");
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

                assetRelationList.add(assetBusinessRelation);
            }
            assetBusinessRelationDao.insertBatch(assetRelationList);
            return assetBusiness.getStringId();
        }

        @Override
        public String updateAssetBusiness(AssetBusinessRequest request) throws Exception {
            AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
            return assetBusinessDao.update(assetBusiness).toString();
        }

        @Override
        public List<AssetBusinessResponse> queryListAssetBusiness(AssetBusinessQuery query) throws Exception {
            List<AssetBusiness> assetBusinessList = assetBusinessDao.findQuery(query);
            //TODO
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
}
