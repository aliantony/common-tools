package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetCpu", description = "处理器表")
@RestController
@RequestMapping("/api/v1/asset/cpu")
public class AssetCpuController {

    @Resource
    public IAssetCpuService iAssetCpuService;

    /**
     * 保存
     *
     * @param assetCpu
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:cpu:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetCpu") AssetCpuRequest assetCpu) throws Exception {
        iAssetCpuService.saveAssetCpu(assetCpu);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetCpu
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:cpu:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetCpu") AssetCpuRequest assetCpu) throws Exception {
        iAssetCpuService.updateAssetCpu(assetCpu);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetCpu
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:cpu:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetCpu") AssetCpuQuery assetCpu) throws Exception {
        return ActionResponse.success(iAssetCpuService.findPageAssetCpu(assetCpu));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:cpu:queryById')")
    public ActionResponse queryById(@ApiParam(value = "assetCpu") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetCpuService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param request 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:cpu:deleteById')")
    public ActionResponse deleteById(@ApiParam(value = "query") @RequestBody BaseRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetCpuService.deleteById(request.getStringId()));
    }
}
