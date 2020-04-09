package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderDao;
import com.antiy.asset.dao.AssetOaOrderHandleDao;
import com.antiy.asset.dao.AssetOaOrderLendDao;
import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.entity.AssetOaOrderLend;
import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.asset.vo.response.AssetOaOrderHandleResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单处理关联资产表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Service
public class AssetOaOrderHandleServiceImpl extends BaseServiceImpl<AssetOaOrderHandle> implements IAssetOaOrderHandleService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderHandleDao assetOaOrderHandleDao;
    @Resource
    private AssetOaOrderDao assetOaOrderDao;
    @Resource
    private AssetOaOrderLendDao assetOaOrderLendDao;

    @Resource
    private BaseConverter<AssetOaOrderHandleRequest, AssetOaOrderHandle> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrderHandle, AssetOaOrderHandleResponse> responseConverter;

    @Override
    public Integer saveAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception {
       String orderNumber = request.getorderNumber();
        AssetOaOrder assetOaOrder = assetOaOrderDao.getByNumber(orderNumber);
        if(AssetOaOrderTypeEnum.LEND.getCode().equals(assetOaOrder.getOrderType())){
            //如果是出借，需要保存出借记录
            AssetOaOrderLend assetOaOrderLend = new AssetOaOrderLend();
            assetOaOrderLend.setLendStatus(1);
            assetOaOrderLend.setOrderNumber(orderNumber);
            assetOaOrderLendDao.insert(assetOaOrderLend);

        }
        if(CollectionUtils.isEmpty(request.getAssetIds())){
            throw new BusinessException("请选择资产");
        }else{
            List<AssetOaOrderHandle> assetOaOrderHandles = new ArrayList<AssetOaOrderHandle>();
            for(Integer assetId : request.getAssetIds()){
                AssetOaOrderHandle assetOaOrderHandle = new AssetOaOrderHandle();
                assetOaOrderHandle.setAssetId(assetId);
                assetOaOrderHandle.setOrderNumber(orderNumber);
                assetOaOrderHandles.add(assetOaOrderHandle);
            }
            assetOaOrderHandleDao.insertBatch(assetOaOrderHandles);
        }
        return request.getAssetIds().size();
    }

    @Override
    public Integer updateAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception {
        AssetOaOrderHandle assetOaOrderHandle = requestConverter.convert(request, AssetOaOrderHandle.class);
        return assetOaOrderHandleDao.update(assetOaOrderHandle);
    }

    @Override
    public List<AssetOaOrderHandleResponse> findListAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception {
        List<AssetOaOrderHandle> assetOaOrderHandleList = assetOaOrderHandleDao.findQuery(query);
        //TODO
        List<AssetOaOrderHandleResponse> assetOaOrderHandleResponse = responseConverter.convert(assetOaOrderHandleList, AssetOaOrderHandleResponse.class);
        return assetOaOrderHandleResponse;
    }

    @Override
    public PageResult<AssetOaOrderHandleResponse> findPageAssetOaOrderHandle(AssetOaOrderHandleQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrderHandle(query));
    }
}
