package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetStatusTaskDao;
import com.antiy.asset.entity.AssetStatusTask;
import com.antiy.asset.service.IAssetStatusTaskService;
import com.antiy.asset.vo.query.AssetStatusTaskQuery;
import com.antiy.asset.vo.request.AssetStatusTaskRequest;
import com.antiy.asset.vo.response.AssetStatusTaskResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 资产状态任务表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-05-22
 */
@Service
public class AssetStatusTaskServiceImpl extends BaseServiceImpl<AssetStatusTask> implements IAssetStatusTaskService {

    private Logger                                                  logger = LogUtils.get(this.getClass());

    @Resource
    private AssetStatusTaskDao                                      assetStatusTaskDao;
    @Resource
    private BaseConverter<AssetStatusTaskRequest, AssetStatusTask>  requestConverter;
    @Resource
    private BaseConverter<AssetStatusTask, AssetStatusTaskResponse> responseConverter;

    @Override
    public String saveAssetStatusTask(AssetStatusTaskRequest request) throws Exception {
        AssetStatusTask assetStatusTask = requestConverter.convert(request, AssetStatusTask.class);
        assetStatusTaskDao.insert(assetStatusTask);
        return assetStatusTask.getStringId();
    }

    @Override
    public String updateAssetStatusTask(AssetStatusTaskRequest request) throws Exception {
        AssetStatusTask assetStatusTask = requestConverter.convert(request, AssetStatusTask.class);
        return assetStatusTaskDao.update(assetStatusTask).toString();
    }

    @Override
    public List<AssetStatusTaskResponse> queryListAssetStatusTask(AssetStatusTaskQuery query) throws Exception {
        List<AssetStatusTask> assetStatusTaskList = assetStatusTaskDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetStatusTaskList, AssetStatusTaskResponse.class);
    }

    @Override
    public PageResult<AssetStatusTaskResponse> queryPageAssetStatusTask(AssetStatusTaskQuery query) throws Exception {
        return new PageResult<AssetStatusTaskResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetStatusTask(query));
    }

    @Override
    public AssetStatusTaskResponse queryAssetStatusTaskById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetStatusTaskResponse assetStatusTaskResponse = responseConverter.convert(
            assetStatusTaskDao.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())),
            AssetStatusTaskResponse.class);
        return assetStatusTaskResponse;
    }

    @Override
    public String deleteAssetStatusTaskById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetStatusTaskDao.deleteById(baseRequest.getStringId()).toString();
    }
}
