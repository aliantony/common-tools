package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetHardDiskService;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.asset.vo.request.AssetHardDiskRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetHardDisk", description = "硬盘表")
@RestController
@RequestMapping("/api/v1/asset/harddisk")
public class AssetHardDiskController {

    @Resource
    public IAssetHardDiskService iAssetHardDiskService;

    /**
     * 保存
     *
     * @param assetHardDisk
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:harddisk:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetHardDisk") AssetHardDiskRequest assetHardDisk) throws Exception {
        iAssetHardDiskService.saveAssetHardDisk(assetHardDisk);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetHardDisk
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('asset:harddisk:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetHardDisk") AssetHardDiskRequest assetHardDisk) throws Exception {
        iAssetHardDiskService.updateAssetHardDisk(assetHardDisk);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetHardDisk
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:harddisk:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetHardDisk") AssetHardDiskQuery assetHardDisk) throws Exception {
        return ActionResponse.success(iAssetHardDiskService.findPageAssetHardDisk(assetHardDisk));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:harddisk:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetHardDisk") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetHardDiskService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('asset:harddisk:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetHardDiskService.deleteById(query.getPrimaryKey()));
    }
}
