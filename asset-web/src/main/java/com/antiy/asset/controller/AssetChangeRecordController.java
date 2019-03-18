package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 *
 * @author why
 * @since 2019-02-19
 */
@Api(value = "AssetChangeRecord", description = "变更记录表 ")
@RestController
@RequestMapping("/api/v1/asset/changerecord")
public class AssetChangeRecordController {
    private static final Logger      logger = LogUtils.get();

    @Resource
    public IAssetChangeRecordService iAssetChangeRecordService;

    /**
     * 保存
     * @param assetChangeRecordRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:changerecord:saveSingle')")
    public ActionResponse saveSingle(@ApiParam(value = "assetChangeRecord") @RequestBody AssetChangeRecordRequest assetChangeRecordRequest) throws Exception {
        return iAssetChangeRecordService.saveAssetChangeRecord(assetChangeRecordRequest);
    }

    /**
     * 修改
     * @param assetChangeRecordRequest
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetChangeRecord") AssetChangeRecordRequest assetChangeRecordRequest) throws Exception {
        iAssetChangeRecordService.updateAssetChangeRecord(assetChangeRecordRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetChangeRecordQuery
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetChangeRecord") AssetChangeRecordQuery assetChangeRecordQuery) throws Exception {
        return ActionResponse.success(iAssetChangeRecordService.findPageAssetChangeRecord(assetChangeRecordQuery));
    }

    /**
     * 通过ID删除
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetChangeRecordService.deleteById(id));
    }

    /**
     * 通过ID查询变更信息统一接口
     * @param businessId
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询变更信息统一接口", notes = "业务ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class, responseContainer = "actionResponse") })
    @RequestMapping(value = "/queryUniformChangeInfo", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:changerecord:queryUniformChangeInfo')")
    public ActionResponse queryUniformChangeInfo(@ApiParam(value = "业务ID") Integer businessId, @ApiParam(value = "品类型号ID") Integer categoryModelId) throws Exception {
        return ActionResponse.success(iAssetChangeRecordService.queryUniformChangeInfo(businessId, categoryModelId));
    }
}
