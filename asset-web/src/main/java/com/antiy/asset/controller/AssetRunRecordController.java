package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetRunRecordService;
import com.antiy.asset.vo.query.AssetRunRecordQuery;
import com.antiy.asset.vo.request.AssetRunRecordRequest;
import com.antiy.asset.vo.response.AssetRunRecordResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2020-03-10
 */
@Api(value = "AssetRunRecord", description = "系统运行记录表")
@RestController
@RequestMapping("/v1/asset/assetrunrecord")
public class AssetRunRecordController {

    @Resource
    public IAssetRunRecordService iAssetRunRecordService;

    /**
     * 保存
     *
     * @param assetRunRecordRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetRunRecord") @RequestBody AssetRunRecordRequest assetRunRecordRequest) throws Exception {
        return ActionResponse.success(iAssetRunRecordService.saveAssetRunRecord(assetRunRecordRequest));
    }

    /**
     * 修改
     *
     * @param assetRunRecordRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetRunRecord") AssetRunRecordRequest assetRunRecordRequest) throws Exception {
        return ActionResponse.success(iAssetRunRecordService.updateAssetRunRecord(assetRunRecordRequest));
    }

    /**
     * 批量查询
     *
     * @param assetRunRecordQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetRunRecordResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetRunRecord") AssetRunRecordQuery assetRunRecordQuery) throws Exception {
        return ActionResponse.success(iAssetRunRecordService.queryPageAssetRunRecord(assetRunRecordQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetRunRecordResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetRunRecordService.queryAssetRunRecordById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetRunRecordService.deleteAssetRunRecordById(baseRequest));
    }
}
