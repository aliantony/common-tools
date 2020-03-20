package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetStatusMonitorDao;
import com.antiy.asset.entity.AssetStatusMonitor;
import com.antiy.asset.service.IAssetStatusMonitorService;
import com.antiy.asset.vo.query.AssetStatusMonitorQuery;
import com.antiy.asset.vo.request.AssetStatusMonitorRequest;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;
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
 * @since 2020-03-06
 */
@Service
public class AssetStatusMonitorServiceImpl extends BaseServiceImpl<AssetStatusMonitor> implements IAssetStatusMonitorService{

        private Logger logger = LogUtils.get(this.getClass());

        @Resource
        private AssetStatusMonitorDao assetStatusMonitorDao;
        @Resource
        private BaseConverter<AssetStatusMonitorRequest, AssetStatusMonitor>  requestConverter;
        @Resource
        private BaseConverter<AssetStatusMonitor, AssetStatusMonitorResponse> responseConverter;

        @Override
        public String saveAssetStatusMonitor(AssetStatusMonitorRequest request) throws Exception {
            AssetStatusMonitor assetStatusMonitor = requestConverter.convert(request, AssetStatusMonitor.class);
            assetStatusMonitorDao.insert(assetStatusMonitor);
            return assetStatusMonitor.getStringId();
        }

        @Override
        public String updateAssetStatusMonitor(AssetStatusMonitorRequest request) throws Exception {
            AssetStatusMonitor assetStatusMonitor = requestConverter.convert(request, AssetStatusMonitor.class);
            return assetStatusMonitorDao.update(assetStatusMonitor).toString();
        }

        @Override
        public List<AssetStatusMonitorResponse> queryListAssetStatusMonitor(AssetStatusMonitorQuery query) throws Exception {
            List<AssetStatusMonitor> assetStatusMonitorList = assetStatusMonitorDao.findQuery(query);

            return responseConverter.convert(assetStatusMonitorList,AssetStatusMonitorResponse.class);
        }

        @Override
        public PageResult<AssetStatusMonitorResponse> queryPageAssetStatusMonitor(AssetStatusMonitorQuery query) throws Exception {
                return new PageResult<AssetStatusMonitorResponse>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.queryListAssetStatusMonitor(query));
        }

        @Override
        public AssetStatusMonitorResponse queryAssetStatusMonitorById(QueryCondition queryCondition) throws Exception{
             ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
             AssetStatusMonitorResponse assetStatusMonitorResponse = responseConverter
                .convert(assetStatusMonitorDao.getById(queryCondition.getPrimaryKey()), AssetStatusMonitorResponse.class);
             return assetStatusMonitorResponse;
        }

        @Override
        public String deleteAssetStatusMonitorById(BaseRequest baseRequest) throws Exception{
             ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
                return assetStatusMonitorDao.deleteById(baseRequest.getStringId()).toString();
        }

    @Override
    public AssetStatusMonitorResponse queryBasePerformance(String primaryKey) {
        AssetStatusMonitorResponse assetStatusMonitorResponse= assetStatusMonitorDao.queryBasePerformance(primaryKey);
        return assetStatusMonitorResponse;
    }

    @Override
    public PageResult<AssetStatusMonitorResponse> queryMonitor(AssetStatusMonitorQuery assetStatusMonitorQuery) {
        ParamterExceptionUtils.isNull(assetStatusMonitorQuery.getAssetId(),"请传入参数资产id");
        ParamterExceptionUtils.isNull(assetStatusMonitorQuery.getAssetId(),"请传入参数监控类型");
        /**
         * 设置最近的监控时间
         */
        Long lastTime = assetStatusMonitorDao.maxMonitorGmtModified(assetStatusMonitorQuery);
        assetStatusMonitorQuery.setGmtModified(lastTime);
        int count= assetStatusMonitorDao.countMonitor(assetStatusMonitorQuery);
        if(count>0){
            List<AssetStatusMonitorResponse> assetStatusMonitorResponses=assetStatusMonitorDao.queryMonitor(assetStatusMonitorQuery);
            return  new PageResult<>(assetStatusMonitorQuery.getPageSize(),count,assetStatusMonitorQuery.getCurrentPage(),assetStatusMonitorResponses);
        }
        return new PageResult<>(assetStatusMonitorQuery.getPageSize(),0,assetStatusMonitorQuery.getCurrentPage(), Lists.newArrayList());
    }
}