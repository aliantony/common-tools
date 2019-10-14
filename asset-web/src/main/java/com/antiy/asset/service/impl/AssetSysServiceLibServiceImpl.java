package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSysServiceLib;
import com.antiy.asset.dao.AssetSysServiceLibDao;
import com.antiy.asset.service.IAssetSysServiceLibService;
import com.antiy.asset.vo.request.AssetSysServiceLibRequest;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.asset.vo.query.AssetSysServiceLibQuery;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p> 服务表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSysServiceLibServiceImpl extends BaseServiceImpl<AssetSysServiceLib>
                                           implements IAssetSysServiceLibService {

    private Logger                                                        logger = LogUtils.get(this.getClass());

    @Resource
    private AssetSysServiceLibDao                                         assetSysServiceLibDao;
    @Resource
    private BaseConverter<AssetSysServiceLibRequest, AssetSysServiceLib>  requestConverter;
    @Resource
    private BaseConverter<AssetSysServiceLib, AssetSysServiceLibResponse> responseConverter;

    @Override
    public List<AssetSysServiceLibResponse> queryListAssetSysServiceLib(AssetSysServiceLibQuery query) throws Exception {
        List<AssetSysServiceLib> assetSysServiceLibList = assetSysServiceLibDao.findQuery(query);
        return responseConverter.convert(assetSysServiceLibList, AssetSysServiceLibResponse.class);
    }

    @Override
    public PageResult<AssetSysServiceLibResponse> queryPageAssetSysServiceLib(AssetSysServiceLibQuery query) throws Exception {
        Integer num = assetSysServiceLibDao.findCount(query);
        if (Objects.equals(num, 0)) {
            return new PageResult<>(query.getPageSize(), num, query.getCurrentPage(), new ArrayList<>());
        }
        return new PageResult<>(query.getPageSize(), num, query.getCurrentPage(),
            this.queryListAssetSysServiceLib(query));
    }

    @Override
    public AssetSysServiceLibResponse queryAssetSysServiceLibById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSysServiceLibResponse assetSysServiceLibResponse = responseConverter
            .convert(assetSysServiceLibDao.getById(queryCondition.getPrimaryKey()), AssetSysServiceLibResponse.class);
        return assetSysServiceLibResponse;
    }

}
