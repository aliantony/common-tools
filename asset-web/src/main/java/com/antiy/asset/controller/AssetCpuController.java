package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetCpu", description = "处理器表")
@RestController
@RequestMapping("/api/v1/asset/assetcpu")
public class AssetCpuController {

    @Resource
    public IAssetCpuService iAssetCpuService;

    /**
     * 保存
     *
     * @param assetCpu
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
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
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
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
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetCpu") AssetCpuQuery assetCpu) throws Exception {
        return ActionResponse.success(iAssetCpuService.findPageAssetCpu(assetCpu));
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
    public ActionResponse queryById(@ApiParam(value = "assetCpu") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetCpuService.getById(id));
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
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetCpuService.deleteById(id));
    }
}
