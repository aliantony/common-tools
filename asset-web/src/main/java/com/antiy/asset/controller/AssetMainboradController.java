package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetMainboradService;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.asset.vo.request.AssetMainboradRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetMainborad", description = "主板表")
@RestController
@RequestMapping("/api/v1/asset/mainborad")
public class AssetMainboradController {

    @Resource
    public IAssetMainboradService iAssetMainboradService;

    /**
     * 保存
     *
     * @param assetMainborad
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:mainborad:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetMainborad") AssetMainboradRequest assetMainborad) throws Exception {
        iAssetMainboradService.saveAssetMainborad(assetMainborad);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetMainborad
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:mainborad:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetMainborad") AssetMainboradRequest assetMainborad) throws Exception {
        iAssetMainboradService.updateAssetMainborad(assetMainborad);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetMainborad
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:mainborad:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetMainborad") AssetMainboradQuery assetMainborad) throws Exception {
        return ActionResponse.success(iAssetMainboradService.findPageAssetMainborad(assetMainborad));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:mainborad:queryById')")
    public ActionResponse queryById(@ApiParam(value = "assetMainborad") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetMainboradService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:mainborad:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetMainboradService.deleteById(id));
    }
}
