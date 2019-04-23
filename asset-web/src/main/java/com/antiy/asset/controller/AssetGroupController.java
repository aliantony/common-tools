package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

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
     * @param queryCondition 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetGroupResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryById')")
    public ActionResponse queryById(@ApiParam(value = "queryCondition") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "primaryKey不能为空");
        return ActionResponse.success(iAssetGroupService.findGroupById(queryCondition.getPrimaryKey()));
    }

    /**
     * 查询下拉项的资产组创建人
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询下拉项的资产组创建人", notes = "")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetGroupResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/createUser", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryCreateUser')")
    public ActionResponse queryCreateUser() throws Exception {
        return ActionResponse.success(iAssetGroupService.queryCreateUser());
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:group:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "queryCondition") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "stringId不能为空");
        return ActionResponse.success(iAssetGroupService.deleteById(Integer.valueOf(baseRequest.getStringId())));
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
     * 查询下拉项的资产组信息（通联设置界面）
     * @author zhangyajun
     *
     * @return 资产组名称集合
     */
    @ApiOperation(value = "查询下拉项的资产组信息（通联设置界面）", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/unconnectedGroupInfo", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:group:queryGroupInfo')")
    public ActionResponse<List<SelectResponse>> queryUnconnectedGroupInfo(@ApiParam("是否是网络设备") Integer isNet) throws Exception {
        return ActionResponse.success(iAssetGroupService.queryUnconnectedGroupInfo(isNet));
    }

    // /**
    // * 移除关联资产
    // * @author zhangyajun
    // *
    // * @return 移除关联资产
    // */
    // @ApiOperation(value = "移除关联资产", notes = "移除关联资产")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer =
    // "actionResponse"), })
    // @RequestMapping(value = "/removeAssociateAsset", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:group:removeAssociateAsset')")
    // public ActionResponse removeAssociateAsset(@ApiParam("removeAssociateAssetRequest") RemoveAssociateAssetRequest
    // removeAssociateAssetRequest) throws Exception {
    // return ActionResponse.success(iAssetGroupService.removeAssociateAsset(removeAssociateAssetRequest));
    // }
}
