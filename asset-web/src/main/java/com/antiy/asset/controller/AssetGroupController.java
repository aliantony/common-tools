package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupDetailResponse;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetGroup", description = "资产组表")
@RestController
@RequestMapping("/api/v1/asset/group")
public class AssetGroupController {

    @Resource
    public IAssetGroupService iAssetGroupService;
    @Resource
    public IAssetService      iAssetService;

    /**
     * 保存
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "保存", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:group:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetGroup") AssetGroupRequest assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.saveAssetGroup(assetGroup));
    }

    /**
     * 修改
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "修改", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:group:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetGroup") AssetGroupRequest assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.updateAssetGroup(assetGroup));
    }

    /**
     * 批量查询
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetGroupResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetGroup") AssetGroupQuery assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.findPageAssetGroup(assetGroup));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetGroupResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryById')")
    public ActionResponse queryById(@PathVariable @ApiParam(value = "assetGroup") @Encode String id) throws Exception {
        return ActionResponse.success(iAssetGroupService.findGroupById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:group:deleteById')")
    public ActionResponse deleteById(@PathVariable @RequestBody @ApiParam(value = "id") @Encode String id) throws Exception {
        return ActionResponse.success(iAssetGroupService.deleteById(Integer.valueOf(id)));
    }

    /**
     * 查询下拉项的资产组信息
     * @author zhangyajun
     *
     * @return 资产组名称集合
     */
    @ApiOperation(value = "查询下拉项的资产组信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/groupInfo", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryGroupInfo')")
    public ActionResponse<List<SelectResponse>> queryGroupInfo() throws Exception {
        return ActionResponse.success(iAssetGroupService.queryGroupInfo());
    }

    /**
     * 通过资产组ID查询资产组详情
     * @author zhangyajun
     *
     * @return 资产组名称集合
     */
    @ApiOperation(value = "通过资产组ID查询资产组详情", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetGroupDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetByAssetGroupId", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryAssetByGroupId')")
    public ActionResponse<PageResult<AssetGroupResponse>> queryAssetByGroupId(@ApiParam(value = "query") AssetGroupQuery query) throws Exception {
        return ActionResponse.success(iAssetGroupService.findPageAssetGroup(query));
    }
}
