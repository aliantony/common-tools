package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetCompositionReportService;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author why
 * @since 2020-02-24
 */
@Api(value = "AssetCompositionReport", description = "")
@RestController
@RequestMapping("/v1/asset/assetcompositionreport")
public class AssetCompositionReportController {
    private static final Logger           logger = LogUtils.get();

    @Resource
    public IAssetCompositionReportService iAssetCompositionReportService;

    /**
     * 保存
     * @param assetCompositionReportRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetCompositionReport") @RequestBody AssetCompositionReportRequest assetCompositionReportRequest) throws Exception {
        iAssetCompositionReportService.saveAssetCompositionReport(assetCompositionReportRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetCompositionReportRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetCompositionReport") AssetCompositionReportRequest assetCompositionReportRequest) throws Exception {
        iAssetCompositionReportService.updateAssetCompositionReport(assetCompositionReportRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetCompositionReportQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetCompositionReport") AssetCompositionReportQuery assetCompositionReportQuery) throws Exception {
        return ActionResponse
            .success(iAssetCompositionReportService.findPageAssetCompositionReport(assetCompositionReportQuery));
    }

    /**
     * 通过ID查询
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetCompositionReport") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetCompositionReportService.getById(id));
    }

    /**
     * 通过ID删除
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetCompositionReportService.deleteById(id));
    }
}
