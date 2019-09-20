package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.response.AssetPreStatusInfoResponse;
import com.antiy.asset.vo.response.StatusLogResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LoginUserUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

import java.util.List;

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
     * @param queryCondition
     * @return actionResponse
     */
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
