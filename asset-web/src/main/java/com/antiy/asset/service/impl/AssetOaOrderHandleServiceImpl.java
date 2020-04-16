package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOaOrderDao;
import com.antiy.asset.dao.AssetOaOrderHandleDao;
import com.antiy.asset.dao.AssetOaOrderResultDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.entity.AssetOaOrderResult;
import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.enums.AssetOaOrderStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.asset.vo.response.AssetOaOrderHandleResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderHandleServiceImpl extends BaseServiceImpl<AssetOaOrderHandle> implements IAssetOaOrderHandleService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderHandleDao assetOaOrderHandleDao;
    @Resource
    private AssetOaOrderDao assetOaOrderDao;
    @Resource
    private AssetOaOrderResultDao assetOaOrderResultDao;
    @Resource
    private AssetDao assetDao;

    @Resource
    private BaseConverter<AssetOaOrderHandleRequest, AssetOaOrderHandle> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrderHandle, AssetOaOrderHandleResponse> responseConverter;

    @Resource
    private AesEncoder aesEncoder;

    @Override
    public Integer saveAssetOaOrderHandle(AssetOaOrderHandleRequest request) throws Exception {
        if (CollectionUtils.isEmpty(request.getAssetIds())) {
            throw new BusinessException("请关联资产");
        }
        String orderNumber = request.getOrderNumber();
        AssetOaOrder assetOaOrder = assetOaOrderDao.getByNumber(orderNumber);
        if (AssetOaOrderTypeEnum.LEND.getCode().equals(assetOaOrder.getOrderType())) {
            //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
            AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
            assetOaOrderResult.setOrderNumber(orderNumber);
            assetOaOrderResult.setLendStatus(request.getLendStatus());
            assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
            assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
            if (!request.getLendStatus().equals(1)) {
                logger.info("----------不许出借,OrderNumber：{}", request.getOrderNumber());
                assetOaOrderResult.setRefuseReason(request.getRefuseReason());
                assetOaOrderResultDao.insert(assetOaOrderResult);
                return 0;
            } else {
                logger.info("----------允许出借,OrderNumber：{}", request.getOrderNumber());
                assetOaOrderResult.setLendUserId(request.getLendUserId());
                assetOaOrderResult.setLendTime(request.getLendTime());
                assetOaOrderResult.setReturnTime(request.getReturnTime());
                assetOaOrderResult.setLendRemark(request.getLendRemark());
                assetOaOrderResultDao.insert(assetOaOrderResult);
            }
        }else if (AssetOaOrderTypeEnum.BACK.getCode().equals(assetOaOrder.getOrderType()) || AssetOaOrderTypeEnum.SCRAP.getCode().equals(assetOaOrder.getOrderType())) {
            logger.info("----------允许出借,OrderNumber：{}", request.getOrderNumber());
            //如果是出借，需要保存出借记录,1 拒绝出借，0允许出借
            AssetOaOrderResult assetOaOrderResult = new AssetOaOrderResult();
            assetOaOrderResult.setOrderNumber(orderNumber);
            assetOaOrderResult.setGmtCreate(System.currentTimeMillis());
            assetOaOrderResult.setPlan(request.getPlan());
            assetOaOrderResult.setFile(request.getFile());
            assetOaOrderResult.setHandleType(request.getHandleType());
            assetOaOrderResult.setExcuteUserId(assetOaOrder.getOrderType());
            assetOaOrderResultDao.insert(assetOaOrderResult);
        }
        List<AssetOaOrderHandle> assetOaOrderHandles = new ArrayList<AssetOaOrderHandle>();
        for (String assetId : request.getAssetIds()) {
            assetId = assetId.endsWith("==") ? aesEncoder.decode(assetId, LoginUserUtil.getLoginUser().getUsername()) : assetId;
            Integer assetIdInt = Integer.parseInt(assetId);
            AssetOaOrderHandle assetOaOrderHandle = new AssetOaOrderHandle();
            assetOaOrderHandle.setAssetId(assetIdInt);
            assetOaOrderHandle.setOrderNumber(orderNumber);
            assetOaOrderHandles.add(assetOaOrderHandle);
        }
        assetOaOrderHandleDao.insertBatch(assetOaOrderHandles);
        //对资产做相应操作
        if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.INNET.getCode())) {
            //如果是入网，不更改资产状态
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.BACK.getCode())) {
            //如果是退回,资产状态改为待退回
            Asset asset = new Asset();
            asset.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
            assetDao.updateStatusByNumber(asset);
        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.SCRAP.getCode())) {
            //如果是报废

        } else if (assetOaOrder.getOrderType().equals(AssetOaOrderTypeEnum.LEND.getCode())) {
            //如果是出借

        }
        //更改订单状态为已处理
        assetOaOrder.setOrderStatus(AssetOaOrderStatusEnum.OVER_HANDLE.getCode());
        assetOaOrderDao.update(assetOaOrder);
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
