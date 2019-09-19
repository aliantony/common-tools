package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetServiceDepend;
import com.antiy.asset.dao.AssetServiceDependDao;
import com.antiy.asset.service.IAssetServiceDependService;
import com.antiy.asset.vo.request.AssetServiceDependRequest;
import com.antiy.asset.vo.response.AssetServiceDependResponse;
import com.antiy.asset.vo.query.AssetServiceDependQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 服务依赖的服务表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetServiceDependServiceImpl extends BaseServiceImpl<AssetServiceDepend>
                                           implements IAssetServiceDependService {

    private Logger                                                        logger = LogUtils.get(this.getClass());

    @Resource
    private AssetServiceDependDao                                         assetServiceDependDao;
    @Resource
    private BaseConverter<AssetServiceDependRequest, AssetServiceDepend>  requestConverter;
    @Resource
    private BaseConverter<AssetServiceDepend, AssetServiceDependResponse> responseConverter;

    @Override
    public String saveAssetServiceDepend(AssetServiceDependRequest request) throws Exception {
        AssetServiceDepend assetServiceDepend = requestConverter.convert(request, AssetServiceDepend.class);
        assetServiceDependDao.insert(assetServiceDepend);
        return assetServiceDepend.getStringId();
    }

    @Override
    public String updateAssetServiceDepend(AssetServiceDependRequest request) throws Exception {
        AssetServiceDepend assetServiceDepend = requestConverter.convert(request, AssetServiceDepend.class);
        return assetServiceDependDao.update(assetServiceDepend).toString();
    }

    @Override
    public List<AssetServiceDependResponse> queryListAssetServiceDepend(AssetServiceDependQuery query) throws Exception {
        List<AssetServiceDepend> assetServiceDependList = assetServiceDependDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetServiceDependList, AssetServiceDependResponse.class);
    }

    @Override
    public PageResult<AssetServiceDependResponse> queryPageAssetServiceDepend(AssetServiceDependQuery query) throws Exception {
        return new PageResult<AssetServiceDependResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetServiceDepend(query));
    }

    @Override
    public AssetServiceDependResponse queryAssetServiceDependById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetServiceDependResponse assetServiceDependResponse = responseConverter
            .convert(assetServiceDependDao.getById(queryCondition.getPrimaryKey()), AssetServiceDependResponse.class);
        return assetServiceDependResponse;
    }

    @Override
    public String deleteAssetServiceDependById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetServiceDependDao.deleteById(baseRequest.getStringId()).toString();
    }
}
