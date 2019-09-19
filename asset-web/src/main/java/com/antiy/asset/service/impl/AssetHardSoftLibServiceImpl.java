package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> CPE表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetHardSoftLibServiceImpl extends BaseServiceImpl<AssetHardSoftLib> implements IAssetHardSoftLibService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetHardSoftLibDao                                       assetHardSoftLibDao;
    @Resource
    private BaseConverter<AssetHardSoftLibRequest, AssetHardSoftLib>  requestConverter;
    @Resource
    private BaseConverter<AssetHardSoftLib, AssetHardSoftLibResponse> responseConverter;

    @Override
    public String saveAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception {
        AssetHardSoftLib assetHardSoftLib = requestConverter.convert(request, AssetHardSoftLib.class);
        assetHardSoftLibDao.insert(assetHardSoftLib);
        return assetHardSoftLib.getStringId();
    }

    @Override
    public String updateAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception {
        AssetHardSoftLib assetHardSoftLib = requestConverter.convert(request, AssetHardSoftLib.class);
        return assetHardSoftLibDao.update(assetHardSoftLib).toString();
    }

    @Override
    public List<AssetHardSoftLibResponse> queryListAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception {
        List<AssetHardSoftLib> assetHardSoftLibList = assetHardSoftLibDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetHardSoftLibList, AssetHardSoftLibResponse.class);
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception {
        return new PageResult<AssetHardSoftLibResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetHardSoftLib(query));
    }

    @Override
    public AssetHardSoftLibResponse queryAssetHardSoftLibById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetHardSoftLibResponse assetHardSoftLibResponse = responseConverter
            .convert(assetHardSoftLibDao.getById(queryCondition.getPrimaryKey()), AssetHardSoftLibResponse.class);
        return assetHardSoftLibResponse;
    }

    @Override
    public String deleteAssetHardSoftLibById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetHardSoftLibDao.deleteById(baseRequest.getStringId()).toString();
    }
}
