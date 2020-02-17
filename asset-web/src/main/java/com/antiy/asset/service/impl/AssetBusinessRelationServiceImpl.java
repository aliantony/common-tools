package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetBusinessRelationDao;
import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.service.IAssetBusinessRelationService;
import com.antiy.asset.vo.query.AssetBusinessRelationQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class AssetBusinessRelationServiceImpl extends BaseServiceImpl<AssetBusinessRelation> implements IAssetBusinessRelationService {

        private Logger logger = LogUtils.get(this.getClass());

        @Resource
        private AssetBusinessRelationDao assetBusinessRelationDao;
        @Resource
        private BaseConverter<AssetBusinessRelationRequest, AssetBusinessRelation>  requestConverter;
        @Resource
        private BaseConverter<AssetBusinessRelation, AssetBusinessRelationResponse> responseConverter;

        @Override
        public String saveAssetBusinessRelation(AssetBusinessRelationRequest request) throws Exception {
            AssetBusinessRelation assetBusinessRelation = requestConverter.convert(request, AssetBusinessRelation.class);
            assetBusinessRelationDao.insert(assetBusinessRelation);
            return assetBusinessRelation.getStringId();
        }

        @Override
        public String updateAssetBusinessRelation(AssetBusinessRelationRequest request) throws Exception {
            AssetBusinessRelation assetBusinessRelation = requestConverter.convert(request, AssetBusinessRelation.class);
            return assetBusinessRelationDao.update(assetBusinessRelation).toString();
        }

        @Override
        public List<AssetBusinessRelationResponse> queryListAssetBusinessRelation(AssetBusinessRelationQuery query) throws Exception {
            List<AssetBusinessRelation> assetBusinessRelationList = assetBusinessRelationDao.findQuery(query);
            //TODO
            return responseConverter.convert(assetBusinessRelationList,AssetBusinessRelationResponse.class);
        }

        @Override
        public PageResult<AssetBusinessRelationResponse> queryPageAssetBusinessRelation(AssetBusinessRelationQuery query) throws Exception {
                return new PageResult<AssetBusinessRelationResponse>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.queryListAssetBusinessRelation(query));
        }

        @Override
        public AssetBusinessRelationResponse queryAssetBusinessRelationById(QueryCondition queryCondition) throws Exception{
             ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
             AssetBusinessRelationResponse assetBusinessRelationResponse = responseConverter
                .convert(assetBusinessRelationDao.getById(queryCondition.getPrimaryKey()), AssetBusinessRelationResponse.class);
             return assetBusinessRelationResponse;
        }

        @Override
        public String deleteAssetBusinessRelationById(BaseRequest baseRequest) throws Exception{
             ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
                return assetBusinessRelationDao.deleteById(baseRequest.getStringId()).toString();
        }
}
