package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetAssemblyLib;
import com.antiy.asset.dao.AssetAssemblyLibDao;
import com.antiy.asset.service.IAssetAssemblyLibService;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.vo.query.AssetAssemblyLibQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 组件表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetAssemblyLibServiceImpl extends BaseServiceImpl<AssetAssemblyLib> implements IAssetAssemblyLibService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetAssemblyLibDao                                       assetAssemblyLibDao;
    @Resource
    private BaseConverter<AssetAssemblyLibRequest, AssetAssemblyLib>  requestConverter;
    @Resource
    private BaseConverter<AssetAssemblyLib, AssetAssemblyLibResponse> responseConverter;
    @Resource
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>                 assemblyResponseBaseConverter;

    @Override
    public String saveAssetAssemblyLib(AssetAssemblyLibRequest request) throws Exception {
        AssetAssemblyLib assetAssemblyLib = requestConverter.convert(request, AssetAssemblyLib.class);
        assetAssemblyLibDao.insert(assetAssemblyLib);
        return assetAssemblyLib.getStringId();
    }

    @Override
    public String updateAssetAssemblyLib(AssetAssemblyLibRequest request) throws Exception {
        AssetAssemblyLib assetAssemblyLib = requestConverter.convert(request, AssetAssemblyLib.class);
        return assetAssemblyLibDao.update(assetAssemblyLib).toString();
    }

    @Override
    public List<AssetAssemblyLibResponse> queryListAssetAssemblyLib(AssetAssemblyLibQuery query) throws Exception {
        List<AssetAssemblyLib> assetAssemblyLibList = assetAssemblyLibDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetAssemblyLibList, AssetAssemblyLibResponse.class);
    }

    @Override
    public PageResult<AssetAssemblyLibResponse> queryPageAssetAssemblyLib(AssetAssemblyLibQuery query) throws Exception {
        return new PageResult<AssetAssemblyLibResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetAssemblyLib(query));
    }

    @Override
    public AssetAssemblyLibResponse queryAssetAssemblyLibById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetAssemblyLibResponse assetAssemblyLibResponse = responseConverter
            .convert(assetAssemblyLibDao.getById(queryCondition.getPrimaryKey()), AssetAssemblyLibResponse.class);
        return assetAssemblyLibResponse;
    }

    @Override
    public String deleteAssetAssemblyLibById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetAssemblyLibDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public List<AssetAssemblyResponse> queryAssemblyByHardSoftId(QueryCondition query) {
        return assemblyResponseBaseConverter
                .convert(assetAssemblyLibDao.queryAssemblyByHardSoftId(query.getPrimaryKey()), AssetAssemblyResponse.class);
    }

}
