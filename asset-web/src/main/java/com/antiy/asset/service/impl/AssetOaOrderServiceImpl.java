package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderApplyDao;
import com.antiy.asset.dao.AssetOaOrderApproveDao;
import com.antiy.asset.dao.AssetOaOrderDao;
import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.asset.entity.AssetOaOrderApprove;
import com.antiy.asset.service.IAssetOaOrderService;
import com.antiy.asset.vo.query.AssetOaOrderQuery;
import com.antiy.asset.vo.request.AssetOaOrderApplyRequest;
import com.antiy.asset.vo.request.AssetOaOrderApproveRequest;
import com.antiy.asset.vo.request.AssetOaOrderRequest;
import com.antiy.asset.vo.response.AssetOaOrderApplyResponse;
import com.antiy.asset.vo.response.AssetOaOrderApproveResponse;
import com.antiy.asset.vo.response.AssetOaOrderResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * OA订单表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderServiceImpl extends BaseServiceImpl<AssetOaOrder> implements IAssetOaOrderService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderDao assetOaOrderDao;
    @Resource
    private AssetOaOrderApplyDao assetOaOrderApplyDao;
    @Resource
    private AssetOaOrderApproveDao assetOaOrderApproveDao;

    @Resource
    private BaseConverter<AssetOaOrderRequest, AssetOaOrder> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrder, AssetOaOrderResponse> responseConverter;
    @Resource
    private BaseConverter<AssetOaOrderApplyRequest, AssetOaOrderApply> applyRequestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApply, AssetOaOrderApplyResponse> applyResponseConverter;
    @Resource
    private BaseConverter<AssetOaOrderApproveRequest, AssetOaOrderApprove> approveRequestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApprove, AssetOaOrderApproveResponse> approveResponseConverter;

    @Override
    public Integer saveAssetOaOrder(AssetOaOrderRequest request) throws Exception {
        AssetOaOrder assetOaOrder = requestConverter.convert(request, AssetOaOrder.class);
        //保存订单信息
        assetOaOrderDao.insert(assetOaOrder);
        //保存申请信息
        AssetOaOrderApplyRequest assetOaOrderApplyRequest = request.getAssetOaOrderApplyRequest();
        AssetOaOrderApply assetOaOrderApply = applyRequestConverter.convert(assetOaOrderApplyRequest, AssetOaOrderApply.class);
        assetOaOrderApply.setOrderNumber(assetOaOrder.getNumber());
        assetOaOrderApplyDao.insert(assetOaOrderApply);
        //保存审核信息
        List<AssetOaOrderApproveRequest> assetOaOrderApproveRequests = request.getAssetOaOrderApproveRequests();
        List<AssetOaOrderApprove> assetOaOrderApproves = approveRequestConverter.convert(assetOaOrderApproveRequests, AssetOaOrderApprove.class);
        for (AssetOaOrderApprove assetOaOrderApprove : assetOaOrderApproves) {
            assetOaOrderApprove.setOrderNumber(assetOaOrder.getNumber());
        }
        assetOaOrderApproveDao.insertBatch(assetOaOrderApproves);
        return assetOaOrder.getId();
    }

    @Override
    public Integer updateAssetOaOrder(AssetOaOrderRequest request) throws Exception {
        AssetOaOrder assetOaOrder = requestConverter.convert(request, AssetOaOrder.class);
        return assetOaOrderDao.update(assetOaOrder);
    }

    @Override
    public List<AssetOaOrderResponse> findListAssetOaOrder(AssetOaOrderQuery query) throws Exception {
        List<AssetOaOrder> assetOaOrderList = assetOaOrderDao.findQuery(query);
        //TODO
        List<AssetOaOrderResponse> assetOaOrderResponse = responseConverter.convert(assetOaOrderList, AssetOaOrderResponse.class);
        return assetOaOrderResponse;
    }

    @Override
    public PageResult<AssetOaOrderResponse> findPageAssetOaOrder(AssetOaOrderQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrder(query));
    }

    @Override
    public AssetOaOrderResponse getDetailById(Integer id) throws Exception {
        //查询订单
        AssetOaOrder assetOaOrder = assetOaOrderDao.getById(id);
        if (assetOaOrder == null) {
            return null;
        }
        AssetOaOrderResponse assetOaOrderResponse = responseConverter.convert(assetOaOrder, AssetOaOrderResponse.class);
        //查询申请信息
        AssetOaOrderApply assetOaOrderApply = assetOaOrderApplyDao.getByOrderNumber(assetOaOrder.getNumber());
        AssetOaOrderApplyResponse assetOaOrderApplyResponse = applyResponseConverter.convert(assetOaOrderApply, AssetOaOrderApplyResponse.class);
        Date applyTime = new Date(assetOaOrderApplyResponse.getApplyTime());
        String applyTimeStr = DateUtils.getDataString(applyTime, DateUtils.WHOLE_FORMAT).replaceAll("-","/");
        assetOaOrderApplyResponse.setApplyTimeStr(applyTimeStr);
        //如果是退回和报废，还需要查询资产ip，mac
        if(!StringUtils.isEmpty(assetOaOrderApply.getAssetNumber())){
            HashMap<String, Object> result = assetOaOrderApplyDao.getIpAndMacByAssetNumber(assetOaOrderApply.getAssetNumber());
            if(result != null){
                assetOaOrderApplyResponse.setAssetIp(result.get("ip").toString());
                assetOaOrderApplyResponse.setAssetMac(result.get("mac").toString());
            }
        }
        //如果是出借，还需要拼接出借时间
        if(assetOaOrderApply.getLendStartTime() != null && assetOaOrderApply.getLendEndTime() != null){
            Date startDate =new Date(assetOaOrderApply.getLendStartTime());
            Date endDate =new Date(assetOaOrderApply.getLendEndTime());
            String startTime = DateUtils.getDataString(startDate, DateUtils.NO_TIME_FORMAT).replaceAll("-","/");
            String endTime = DateUtils.getDataString(endDate, DateUtils.NO_TIME_FORMAT).replaceAll("-","/");
            assetOaOrderApplyResponse.setLendTime(startTime + "-" + endTime);
        }
        //查询审批信息
        List<AssetOaOrderApprove> assetOaOrderApproves = assetOaOrderApproveDao.getByOrderNumber(assetOaOrder.getNumber());
        List<AssetOaOrderApproveResponse> assetOaOrderApproveResponses = approveResponseConverter.convert(assetOaOrderApproves, AssetOaOrderApproveResponse.class);
        if(!CollectionUtils.isEmpty(assetOaOrderApproveResponses)){
            for(AssetOaOrderApproveResponse assetOaOrderApproveResponse : assetOaOrderApproveResponses){
                Date approveTime = new Date(assetOaOrderApproveResponse.getApproveTime());
                String approveTimeStr = DateUtils.getDataString(approveTime, DateUtils.WHOLE_FORMAT).replaceAll("-","/");
                assetOaOrderApproveResponse.setApproveTimeStr(approveTimeStr);
            }
        }
        assetOaOrderResponse.setAssetOaOrderApplyResponse(assetOaOrderApplyResponse);
        assetOaOrderResponse.setAssetOaOrderApproveResponses(assetOaOrderApproveResponses);
        return assetOaOrderResponse;
    }
}
