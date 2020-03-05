package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetIpRelationDao;
import com.antiy.asset.dao.AssetManufactureDao;
import com.antiy.asset.entity.AssetManufacture;
import com.antiy.asset.service.IAssetManufactureService;
import com.antiy.asset.vo.enums.NetStatusEnum;
import com.antiy.asset.vo.query.AssetManufactureQuery;
import com.antiy.asset.vo.request.AssetManufactureRequest;
import com.antiy.asset.vo.response.AssetManufactureResponse;
import com.antiy.asset.vo.response.AssetManufactureTreeResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;

/**
 * <p> 安全厂商表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
@Service
public class AssetManufactureServiceImpl extends BaseServiceImpl<AssetManufacture> implements IAssetManufactureService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetManufactureDao                                       assetManufactureDao;
    @Resource
    private BaseConverter<AssetManufactureRequest, AssetManufacture>  requestConverter;
    @Resource
    private BaseConverter<AssetManufacture, AssetManufactureResponse> responseConverter;
    @Resource
    private AssetIpRelationDao                                        ipRelationDao;

    @Override
    public String saveAssetManufacture(AssetManufactureRequest request) throws Exception {
        AssetManufacture assetManufacture = requestConverter.convert(request, AssetManufacture.class);
        assetManufactureDao.insert(assetManufacture);
        return assetManufacture.getStringId();
    }

    @Override
    public String updateAssetManufacture(AssetManufactureRequest request) throws Exception {
        AssetManufacture assetManufacture = requestConverter.convert(request, AssetManufacture.class);
        return assetManufactureDao.update(assetManufacture).toString();
    }

    @Override
    public List<AssetManufactureResponse> queryListAssetManufacture(AssetManufactureQuery query) throws Exception {
        List<AssetManufacture> assetManufactureList = assetManufactureDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetManufactureList, AssetManufactureResponse.class);
    }

    @Override
    public PageResult<AssetManufactureResponse> queryPageAssetManufacture(AssetManufactureQuery query) throws Exception {
        return new PageResult<AssetManufactureResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetManufacture(query));
    }

    @Override
    public AssetManufactureResponse queryAssetManufactureById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetManufactureResponse assetManufactureResponse = responseConverter
            .convert(assetManufactureDao.getById(queryCondition.getPrimaryKey()), AssetManufactureResponse.class);
        return assetManufactureResponse;
    }

    @Override
    public String deleteAssetManufactureById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetManufactureDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public List<AssetManufactureTreeResponse> safetyManufacture() throws Exception {
        List<AssetManufactureTreeResponse> treeResponseList = Lists.newArrayList();
        List<AssetManufacture> manufactureList = assetManufactureDao.getManufacture();
        for (AssetManufacture manufacture : manufactureList) {
            AssetManufactureTreeResponse treeResponse = new AssetManufactureTreeResponse();
            treeResponse.setId(manufacture.getUniqueId());
            treeResponse.setProductName(manufacture.getProductName());
            treeResponse.setVersion(manufacture.getVersion());
            treeResponse.setPid(manufacture.getPid());
            treeResponse.setName(manufacture.getName());
            treeResponse.setNetStatus(manufacture.getNetStatus());
            treeResponse.setAssetId(manufacture.getAssetId());
            treeResponse.setNumber(manufacture.getNumber());
            if (manufacture.getNetStatus() != null) {
                treeResponse.setNetStatusName(NetStatusEnum.getEnumByCode(manufacture.getNetStatus()).getName());
            }
            // 获取资产ip地址
            treeResponse.setIps(ipRelationDao.findIpsByAssetId(manufacture.getAssetId()));
            treeResponseList.add(treeResponse);
        }
        return treeResponseList;
    }
}
