package com.antiy.asset.controller;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetSchemeQuery;
import com.antiy.asset.vo.request.AssetOperationRecordRequest;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.asset.vo.response.AssetPreStatusInfoResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.StatusLogResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LoginUserUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "资产操作记录")
@RestController
@RequestMapping("/api/v1/asset/changeLog")
public class AssetOperationRecordController {

    @Resource
    private IAssetOperationRecordService assetOperationRecordService;

    /**
     * 查找资产操作历史
     *
     * @param queryCondition 分装主键vo
     * @return actionResponse 响应数据
     */
    @ApiOperation(value = "退役/报废/准入/执行方案（资产）列表", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = StatusLogResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/check/list", method = RequestMethod.POST)
    public ActionResponse queryRetExecList(@ApiParam(value = "assetOperationRecordQuery") @RequestBody AssetSchemeQuery assetSchemeQuery) throws Exception {
       /* assetSchemeQuery.setOrginStatusOne(7);
        assetSchemeQuery.setOrginStatusTwo(10);
        assetSchemeQuery.setTargetStatus(9);*/
        List<AssetResponse> assetOperationRecordList=assetOperationRecordService.queryAssetSchemListByAssetIds(assetSchemeQuery);
        return ActionResponse.success(assetOperationRecordList);
    }


    @ApiOperation(value = "资产退役/报废/入网审批资产信息列表", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = StatusLogResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/asset/list", method = RequestMethod.POST)
    public ActionResponse queryCheckList(@ApiParam(value = "assetSchemeQuery") @RequestBody AssetSchemeQuery assetSchemeQuery) throws Exception {
        PageResult<AssetResponse> assetResponseList=assetOperationRecordService.queryCheckList(assetSchemeQuery);
        return ActionResponse.success(assetResponseList);
    }
    /*@ApiOperation(value = "资产 退役/报废/入网审批方案和附件", notes = "传入taskId")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = StatusLogResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/check/scheme", method = RequestMethod.POST)
    public ActionResponse queryCheckScheme(@ApiParam(value = "operationRecordRequest") @RequestBody AssetOperationRecordRequest operationRecordRequest) throws Exception {
        AssetOperationRecordResponse recordResponse=assetOperationRecordService.queryCheckSchemeByTaskId(operationRecordRequest.getTaskId());
        return ActionResponse.success(recordResponse);
    }*/
   /* @ApiOperation(value = "报废执行报废方案列表", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = StatusLogResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/scrapExec/list", method = RequestMethod.POST)
    public ActionResponse queryScrapExecList(@ApiParam(value = "assetOperationRecordQuery") @RequestBody AssetSchemeQuery assetSchemeQuery) throws Exception {
        assetSchemeQuery.setOrginStatusOne(12);
        assetSchemeQuery.setOrginStatusTwo(14);
        assetSchemeQuery.setTargetStatus(13);
        List<AssetOperationRecordResponse> assetOperationRecordList=assetOperationRecordService.queryAssetSchemListByAssetIds(assetSchemeQuery);
        return ActionResponse.success(assetOperationRecordList);
    }*/

    @ApiOperation(value = "查找资产操作历史", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = StatusLogResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOperationRecordQuery") @RequestBody QueryCondition queryCondition) throws Exception {
        System.out.println(LoginUserUtil.getLoginUser().toString());
        return assetOperationRecordService.queryAssetAllStatusInfo(queryCondition.getPrimaryKey());
    }

    /**
     * 上一步备注信息批量查询
     */
    @ApiOperation(value = "上一步资产信息批量查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetPreStatusInfoResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/preNote/batch", method = RequestMethod.POST)
    public ActionResponse batchQueryPreStatusInfo(@ApiParam(value = "batchQueryRequest") @RequestBody BatchQueryRequest query){
        return assetOperationRecordService.batchQueryAssetPreStatusInfo(query.getIds());
    }

}
