package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetInstallHistory;
import com.antiy.asset.dao.AssetInstallHistoryDao;
import com.antiy.asset.service.IAssetInstallHistoryService;
import com.antiy.asset.vo.request.AssetInstallHistoryRequest;
import com.antiy.asset.vo.response.AssetInstallHistoryResponse;
import com.antiy.asset.vo.query.AssetInstallHistoryQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 安装记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetInstallHistoryServiceImpl extends BaseServiceImpl<AssetInstallHistory>
                                            implements IAssetInstallHistoryService {

    private Logger                                                          logger = LogUtils.get(this.getClass());

    @Resource
    private AssetInstallHistoryDao                                          assetInstallHistoryDao;
    @Resource
    private BaseConverter<AssetInstallHistoryRequest, AssetInstallHistory>  requestConverter;
    @Resource
    private BaseConverter<AssetInstallHistory, AssetInstallHistoryResponse> responseConverter;

    @Override
    public String saveAssetInstallHistory(AssetInstallHistoryRequest request) throws Exception {
        AssetInstallHistory assetInstallHistory = requestConverter.convert(request, AssetInstallHistory.class);
        assetInstallHistoryDao.insert(assetInstallHistory);
        return assetInstallHistory.getStringId();
    }

    @Override
    public String updateAssetInstallHistory(AssetInstallHistoryRequest request) throws Exception {
        AssetInstallHistory assetInstallHistory = requestConverter.convert(request, AssetInstallHistory.class);
        return assetInstallHistoryDao.update(assetInstallHistory).toString();
    }

    @Override
    public List<AssetInstallHistoryResponse> queryListAssetInstallHistory(AssetInstallHistoryQuery query) throws Exception {
        List<AssetInstallHistory> assetInstallHistoryList = assetInstallHistoryDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetInstallHistoryList, AssetInstallHistoryResponse.class);
    }

    @Override
    public PageResult<AssetInstallHistoryResponse> queryPageAssetInstallHistory(AssetInstallHistoryQuery query) throws Exception {
        return new PageResult<AssetInstallHistoryResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetInstallHistory(query));
    }

    @Override
    public AssetInstallHistoryResponse queryAssetInstallHistoryById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallHistoryResponse assetInstallHistoryResponse = responseConverter
            .convert(assetInstallHistoryDao.getById(queryCondition.getPrimaryKey()), AssetInstallHistoryResponse.class);
        return assetInstallHistoryResponse;
    }

    @Override
    public String deleteAssetInstallHistoryById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetInstallHistoryDao.deleteById(baseRequest.getStringId()).toString();
    }
}
