package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.request.AssetIpAddressRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.CategoryType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

import java.util.List;

/**
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@Api(value = "AssetLinkRelation", description = "通联关系表")
@RestController
@RequestMapping("/api/v1/asset/linkrelation")
public class AssetLinkRelationController {

    @Resource
    public IAssetLinkRelationService iAssetLinkRelationService;

    /**
     * 保存
     *
     * @param assetLinkRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:saveSingle')")
    public ActionResponse saveSingle(@ApiParam(value = "assetLinkRelation") @RequestBody AssetLinkRelationRequest assetLinkRelationRequest) throws Exception {

        return ActionResponse.success(iAssetLinkRelationService.saveAssetLinkRelation(assetLinkRelationRequest));
    }
    /**
     * 批量保存
     *
     * @param assetLinkRelationRequestList
     * @return actionResponse
     */
    @ApiOperation(value = "批量保存", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class), })
    @RequestMapping(value = "/save/list", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:saveList')")
    public ActionResponse saveList(@ApiParam(value = "assetLinkRelation") @RequestBody List<AssetLinkRelationRequest> assetLinkRelationRequestList) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.saveAssetLinkRelationList(assetLinkRelationRequestList));
    }

    /**
     * 修改
     *
     * @param assetLinkRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:updateSingle')")
    public ActionResponse updateSingle(@ApiParam(value = "assetLinkRelation") AssetLinkRelationRequest assetLinkRelationRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.updateAssetLinkRelation(assetLinkRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetLinkRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetLinkRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetLinkRelation") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryPageAssetLinkRelation(assetLinkRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetLinkRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键不能为空");
        return ActionResponse.success(iAssetLinkRelationService.queryAssetLinkRelationById(queryCondition));
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
    // @PreAuthorize("hasAuthority('asset:linkrelation:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键不能为空");
        return ActionResponse.success(iAssetLinkRelationService.deleteAssetLinkRelationById(baseRequest));
    }

    @ApiOperation(value = "通过资产Id查询可用的IP地址", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/query/ip", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:queryAssetIpAddress')")
    public ActionResponse queryAssetIpAddress(@RequestBody AssetIpAddressRequest request) throws Exception {
        ParamterExceptionUtils.isBlank(request.getAssetId(), "资产Id不能为空");
        return ActionResponse.success(iAssetLinkRelationService.queryIpAddressByAssetId(request.getAssetId(),
            request.getEnable() == null ? true : request.getEnable()));
    }

    /**
     * 查询资产列表
     *
     * @param assetQuery
     * @return actionResponse
     */
    @ApiOperation(value = "查询资产列表", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class), })
    @RequestMapping(value = "/query/assetList", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:queryAssetList')")
    public ActionResponse queryAssetList(@RequestBody @ApiParam(value = "主键封装对象") AssetQuery assetQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryAssetPage(assetQuery));
    }

    /**
     * 查询已关联资产关系列表
     *
     * @param assetLinkRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "查询已关联资产关系列表", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class), })
    @RequestMapping(value = "/query/assetLinkedList", method = RequestMethod.POST)
    // @PreAuthorize("hasAuthority('asset:linkrelation:queryAssetLinkedList')")
    public ActionResponse queryAssetLinkedList(@RequestBody @ApiParam(value = "主键封装对象") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryLinekedRelationPage(assetLinkRelationQuery));
    }

    /**
     * 通过ID查询设备端口
     *
     * @param queryCondition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询设备端口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/portById", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:linkrelation:queryPortById')")
    public ActionResponse queryPortById(@RequestBody @ApiParam(value = "assetLinkRelationQuery") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键不能为空");
        return ActionResponse.success(iAssetLinkRelationService.queryPortById(queryCondition));
    }

    /**
     * 资产通联数量列表查询
     *
     * @param assetLinkRelationQuery 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "资产通联状态列表查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetLinkedCount", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:linkrelation:queryAssetLinkedCount')")
    public ActionResponse queryAssetLinkedCount(@RequestBody @ApiParam(value = "assetLinkRelationQuery") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryAssetLinkedCountPage(assetLinkRelationQuery));
    }

    /**
     * 与当前资产通联的资产列表查询
     * @param assetLinkRelationQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "与当前资产通联的资产列表查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetLinkRelationResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/linkedAssetListByAssetId", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:linkrelation:queryLinkedAssetListByAssetId')")
    public ActionResponse queryLinkedAssetListByAssetId(@RequestBody @ApiParam(value = "assetLinkRelationQuery") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryLinkedAssetPageByAssetId(assetLinkRelationQuery));
    }

    /**
     * 查询剩余的ip
     * @param useableIpRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询剩余的ip", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/useableip", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:linkrelation:queryUseableIp')")
    public ActionResponse queryUseableIp(@ApiParam(value = "useableIpRequest")@RequestBody UseableIpRequest useableIpRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryUseableIp(useableIpRequest));
    }
}
