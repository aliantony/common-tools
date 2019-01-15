package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "资产操作记录")
@RestController
@RequestMapping("/api/v1/asset/assetoperationrecord")
public class AssetOperationRecordController {

    @Resource
    private IAssetOperationRecordService assetOperationRecordService;

    /**
     * 查找资产操作历史
     *
     * @param assetOperationRecordQuery
     * @return actionResponse
     */
    @ApiOperation(value = "查找资产操作历史", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {
        return ActionResponse
            .success(assetOperationRecordService.findAssetOperationRecordByAssetId(assetOperationRecordQuery));
    }

}