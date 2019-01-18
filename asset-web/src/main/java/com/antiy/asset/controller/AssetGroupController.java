package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.common.encoder.Encode;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupDetailResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetGroup", description = "资产组表")
@RestController
@RequestMapping("/api/v1/asset/assetgroup")
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
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
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
    public ActionResponse deleteById(@PathVariable @RequestBody @ApiParam(value = "id") @Encode String id) throws Exception {
        return ActionResponse.success(iAssetGroupService.deleteById(id));
    }

    /**
     * 查询下拉项的资产组信息
     * @author zhangyajun
     *
     * @return 资产组名称集合
     */
    @ApiOperation(value = "查询下拉项的资产组信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/groupInfo", method = RequestMethod.GET)
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
    @RequestMapping(value = "/query/assetByGroupId/{id}", method = RequestMethod.GET)
    public ActionResponse<AssetGroupDetailResponse> queryAssetByGroupId(@ApiParam(value = "资产组ID") @PathVariable Integer id) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAssetGroup(id);
        AssetGroup assetGroup = iAssetGroupService.getById(id);
        PageResult<AssetResponse> pageResult = iAssetService.findPageAsset(assetQuery);
        int assetSize = pageResult.getItems().size();
        AssetGroupDetailResponse assetGroupDetailResponse = null;
        if (assetSize > 0) {
            assetGroupDetailResponse = new AssetGroupDetailResponse();
            assetGroupDetailResponse.setAssetGroupName(assetGroup.getName());
            assetGroupDetailResponse.setAssetGroupMemo(assetGroup.getMemo());
            assetGroupDetailResponse.setAssetResponseList(pageResult.getItems());
        }
        return ActionResponse.success(assetGroupDetailResponse);
    }
}
