package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSoftServiceLib;
import com.antiy.asset.dao.AssetSoftServiceLibDao;
import com.antiy.asset.service.IAssetSoftServiceLibService;
import com.antiy.asset.vo.request.AssetSoftServiceLibRequest;
import com.antiy.asset.vo.response.AssetSoftServiceLibResponse;
import com.antiy.asset.vo.query.AssetSoftServiceLibQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 软件提供的服务 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSoftServiceLibServiceImpl extends BaseServiceImpl<AssetSoftServiceLib>
                                            implements IAssetSoftServiceLibService {

    private Logger                                                          logger = LogUtils.get(this.getClass());

    @Resource
    private AssetSoftServiceLibDao                                          assetSoftServiceLibDao;
    @Resource
    private BaseConverter<AssetSoftServiceLibRequest, AssetSoftServiceLib>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftServiceLib, AssetSoftServiceLibResponse> responseConverter;

    @Override
    public String saveAssetSoftServiceLib(AssetSoftServiceLibRequest request) throws Exception {
        AssetSoftServiceLib assetSoftServiceLib = requestConverter.convert(request, AssetSoftServiceLib.class);
        assetSoftServiceLibDao.insert(assetSoftServiceLib);
        return assetSoftServiceLib.getStringId();
    }

    @Override
    public String updateAssetSoftServiceLib(AssetSoftServiceLibRequest request) throws Exception {
        AssetSoftServiceLib assetSoftServiceLib = requestConverter.convert(request, AssetSoftServiceLib.class);
        return assetSoftServiceLibDao.update(assetSoftServiceLib).toString();
    }

    @Override
    public List<AssetSoftServiceLibResponse> queryListAssetSoftServiceLib(AssetSoftServiceLibQuery query) throws Exception {
        List<AssetSoftServiceLib> assetSoftServiceLibList = assetSoftServiceLibDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSoftServiceLibList, AssetSoftServiceLibResponse.class);
    }

    @Override
    public PageResult<AssetSoftServiceLibResponse> queryPageAssetSoftServiceLib(AssetSoftServiceLibQuery query) throws Exception {
        return new PageResult<AssetSoftServiceLibResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetSoftServiceLib(query));
    }

    @Override
    public AssetSoftServiceLibResponse queryAssetSoftServiceLibById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSoftServiceLibResponse assetSoftServiceLibResponse = responseConverter
            .convert(assetSoftServiceLibDao.getById(queryCondition.getPrimaryKey()), AssetSoftServiceLibResponse.class);
        return assetSoftServiceLibResponse;
    }

    @Override
    public String deleteAssetSoftServiceLibById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetSoftServiceLibDao.deleteById(baseRequest.getStringId()).toString();
    }
}
