package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 软件信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService {


    @Resource
    private AssetSoftwareDao assetSoftwareDao;
    @Resource
    private BaseConverter<AssetSoftwareRequest, AssetSoftware> requestConverter;
    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareResponse> responseConverter;

    @Override
    public Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
        AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
        return assetSoftwareDao.insert(assetSoftware);
    }

    @Override
    @Transactional
    public Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception {
        int i = 0;
        for (; i < assetSoftwareList.size(); i++) {
            assetSoftwareDao.insert(assetSoftwareList.get(i));
        }
        return i + 1;
    }

    @Override
    public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
        AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
        return assetSoftwareDao.update(assetSoftware);
    }

    @Override
    public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
        List<AssetSoftware> assetSoftware = assetSoftwareDao.findListAssetSoftware(query);
        List<AssetSoftwareResponse> assetSoftwareResponse = responseConverter.convert(assetSoftware, AssetSoftwareResponse.class);
        return assetSoftwareResponse;
    }

    public Integer findCountAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    public PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetSoftware(query), query.getCurrentPage(), this.findListAssetSoftware(query));
    }
}
