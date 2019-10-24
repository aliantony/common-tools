package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.entity.AssetStatusNote;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 资产操作记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetOperationRecordServiceImpl extends BaseServiceImpl<AssetOperationRecord>
                                             implements IAssetOperationRecordService {
    private Logger                                                                    logger = LogUtils
        .get(this.getClass());
    @Resource
    private AssetOperationRecordDao                                                   assetOperationRecordDao;

    @Override
    public ActionResponse queryAssetAllStatusInfo(String id) {
        List<AssetStatusDetail> assetStatusDetails = assetOperationRecordDao.queryAssetAllStatusInfo(id);
        List<AssetPreStatusInfoResponse> responses = new ArrayList<>();
        for (AssetStatusDetail inner:assetStatusDetails) {
            StatusLogResponse outer = new StatusLogResponse();
            BeanUtils.copyProperties(inner,outer);
            outer.setDescribe(inner.getOperateUserName()+","+inner.getContent()+inner.getOriginStatus().describe(inner.getProcessResult()));
            if (StringUtils.isEmpty(inner.getFileInfo())) {
                outer.setFileInfo(JsonUtil.ListToJson(Collections.EMPTY_LIST));
            } else {
                outer.setFileInfo(HtmlUtils.htmlUnescape(inner.getFileInfo()));
            }
            responses.add(outer);
        }
        return ActionResponse.success(responses);
    }

    @Override
    public ActionResponse batchQueryAssetPreStatusInfo(List<String> ids) {
        List<AssetStatusNote> assetStatusNotes = assetOperationRecordDao.queryAssetPreStatusInfo(ids);
        List<AssetPreStatusInfoResponse> responses = new ArrayList<>();
        HashMap<String, AssetStatusNote> dbData = new HashMap<>(assetStatusNotes.size());
        assetStatusNotes.forEach(e -> dbData.put(e.getAssetId(), e));
        for (String assetId:
             ids) {
            AssetStatusNote e = dbData.getOrDefault(assetId, null);
            if (e == null) {
                continue;
            }
            AssetPreStatusInfoResponse eachData = new AssetPreStatusInfoResponse(e.getAssetId(), e.getNote(), e.getOriginStatus().getValue());
            responses.add(eachData);
            if (StringUtils.isEmpty(e.getFileInfo())) {
                eachData.setFileInfo(JsonUtil.ListToJson(Collections.EMPTY_LIST));
            } else {
                eachData.setFileInfo(HtmlUtils.htmlUnescape(e.getFileInfo()));
            }
        }

        return ActionResponse.success(responses);
    }

}
