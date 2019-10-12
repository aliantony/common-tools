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
    public ActionResponse saveSingle(@ApiParam(value = "assetLinkRelation") @RequestBody AssetLinkRelationRequest assetLinkRelationRequest) throws Exception {

        return ActionResponse.success(iAssetLinkRelationService.saveAssetLinkRelation(assetLinkRelationRequest));
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
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键不能为空");
        return ActionResponse.success(iAssetLinkRelationService.deleteAssetLinkRelationById(baseRequest));
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
    public ActionResponse queryAssetLinkedList(@RequestBody @ApiParam(value = "主键封装对象") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryLinekedRelationPage(assetLinkRelationQuery));
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
    public ActionResponse queryLinkedAssetListByAssetId(@RequestBody @ApiParam(value = "assetLinkRelationQuery") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryLinkedAssetPageByAssetId(assetLinkRelationQuery));
    }

    /**
     * 查询剩余的ip和端口
     * @param useableIpRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询剩余的ip", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/useableip", method = RequestMethod.POST)
    public ActionResponse queryUseableIpPort(@ApiParam(value = "useableIpRequest")@RequestBody UseableIpRequest useableIpRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryUseableIp(useableIpRequest));
    }
}
