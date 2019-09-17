package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetIpMac;
import com.antiy.asset.dao.AssetIpMacDao;
import com.antiy.asset.service.IAssetIpMacService;
import com.antiy.asset.vo.request.AssetIpMacRequest;
import com.antiy.asset.vo.response.AssetIpMacResponse;
import com.antiy.asset.vo.query.AssetIpMacQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 资产-IP-MAC表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
@Service
public class AssetIpMacServiceImpl extends BaseServiceImpl<AssetIpMac> implements IAssetIpMacService {

    private Logger                                        logger = LogUtils.get(this.getClass());

    @Resource
    private AssetIpMacDao                                 assetIpMacDao;
    @Resource
    private BaseConverter<AssetIpMacRequest, AssetIpMac>  requestConverter;
    @Resource
    private BaseConverter<AssetIpMac, AssetIpMacResponse> responseConverter;

    @Override
    public String saveAssetIpMac(AssetIpMacRequest request) throws Exception {
        AssetIpMac assetIpMac = requestConverter.convert(request, AssetIpMac.class);
        assetIpMacDao.insert(assetIpMac);
        return assetIpMac.getStringId();
    }

    @Override
    public String updateAssetIpMac(AssetIpMacRequest request) throws Exception {
        AssetIpMac assetIpMac = requestConverter.convert(request, AssetIpMac.class);
        return assetIpMacDao.update(assetIpMac).toString();
    }

    @Override
    public List<AssetIpMacResponse> queryListAssetIpMac(AssetIpMacQuery query) throws Exception {
        List<AssetIpMac> assetIpMacList = assetIpMacDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetIpMacList, AssetIpMacResponse.class);
    }

    @Override
    public PageResult<AssetIpMacResponse> queryPageAssetIpMac(AssetIpMacQuery query) throws Exception {
        return new PageResult<AssetIpMacResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.queryListAssetIpMac(query));
    }

    @Override
    public AssetIpMacResponse queryAssetIpMacById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetIpMacResponse assetIpMacResponse = responseConverter
            .convert(assetIpMacDao.getById(queryCondition.getPrimaryKey()), AssetIpMacResponse.class);
        return assetIpMacResponse;
    }

    @Override
    public String deleteAssetIpMacById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetIpMacDao.deleteById(baseRequest.getStringId()).toString();
    }
}
