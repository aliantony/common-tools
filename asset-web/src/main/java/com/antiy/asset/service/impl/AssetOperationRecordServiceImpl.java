package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.entity.AssetStatusNote;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetSchemeQuery;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.asset.vo.response.AssetPreStatusInfoResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.StatusLogResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    @Resource
    private AssetDao assetDao;

    @Override
    public ActionResponse queryAssetAllStatusInfo(String id) {
        List<AssetStatusDetail> assetStatusDetails = assetOperationRecordDao.queryAssetAllStatusInfo(id);
        List<AssetPreStatusInfoResponse> responses = new ArrayList<>();
        for (AssetStatusDetail inner:assetStatusDetails) {
            StatusLogResponse outer = new StatusLogResponse();
            BeanUtils.copyProperties(inner,outer);
            StringBuilder sb = new StringBuilder();
            sb.append(inner.getOperateUserName());
            if (!sb.toString().isEmpty()) {
                sb.append(",");
            }
            sb.append(inner.getContent()).append(inner.getOriginStatus().describe(inner.getProcessResult()));
            outer.setDescribe(sb.toString());
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

    @Override
    public List<AssetResponse> queryAssetSchemListByAssetIds(AssetSchemeQuery assetSchemeQuery) {

        return assetOperationRecordDao.queryAssetSchemListByAssetIds(assetSchemeQuery);
    }

    @Override
    public PageResult<AssetResponse> queryCheckList(AssetSchemeQuery assetSchemeQuery) {
        List<AssetResponse> assetResponseList=assetDao.queryCheckList(assetSchemeQuery);
        Integer count=assetDao.countCheckList(assetSchemeQuery);
        count=count==null?0:count;
        return new PageResult<>(assetSchemeQuery.getPageSize(),count,assetSchemeQuery.getCurrentPage(),assetResponseList);
    }

    @Override
    public AssetOperationRecordResponse queryCheckSchemeByTaskId(Integer taskId) {
        AssetOperationRecordResponse recordResponse=assetOperationRecordDao.queryCheckSchemeByTaskId(taskId);
        return recordResponse;
    }
}
