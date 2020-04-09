package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetLendRelationService;
import com.antiy.asset.vo.query.ApproveListQuery;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.request.AssetLendInfoRequest;
import com.antiy.asset.vo.request.AssetLendRelationRequest;
import com.antiy.asset.vo.request.UniqueKeyRquest;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
@Api(value = "AssetLendRelation", description = "")
@RestController
@RequestMapping("/api/v1/asset/assetlendrelation")
public class AssetLendRelationController {

    @Resource
    public IAssetLendRelationService iAssetLendRelationService;

    /**
     * 保存
     *
     * @param assetLendRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetLendRelation") @RequestBody AssetLendRelationRequest assetLendRelationRequest) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.saveAssetLendRelation(assetLendRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetLendRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetLendRelation") AssetLendRelationRequest assetLendRelationRequest) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.updateAssetLendRelation(assetLendRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetLendRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetLendRelationResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetLendRelation")  @RequestBody AssetLendRelationQuery assetLendRelationQuery)throws Exception{
        return ActionResponse.success(iAssetLendRelationService.queryPageAssetLendRelation(assetLendRelationQuery));
    }


    /**
     * 查询出借详情
     */

    @ApiOperation(value = "查询详情接口", notes = "传入唯一键")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetLendRelationResponse.class),
    })
    @RequestMapping(value = "/query/info", method = RequestMethod.POST)
    public ActionResponse queryInfo(@ApiParam(value = "assetLendRelation") @RequestBody  UniqueKeyRquest uniqueKeyRquest)throws Exception{
        AssetLendRelationResponse assetLendRelationResponse= iAssetLendRelationService.queryInfo(uniqueKeyRquest.getUniqueId());
        return ActionResponse.success(assetLendRelationResponse);
    }

    /**
     * 归还确认
     */
    @ApiOperation(value = "归还确认接口", notes = "传入唯一键")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetLendRelationResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/return/confirm", method = RequestMethod.POST)
    public ActionResponse queryHistory(@ApiParam(value = "assetLendRelation") @RequestBody AssetLendRelationRequest assetLendRelationRequest)throws Exception{
        Integer returnConfirm= iAssetLendRelationService.returnConfirm(assetLendRelationRequest);
        return ActionResponse.success(returnConfirm);
    }

    /**
     * 出借历史
     */
    @ApiOperation(value = "出借历史接口", notes = "传入资产id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetLendRelationResponse.class),
    })
    @RequestMapping(value = "/query/lend/history", method = RequestMethod.POST)
    public ActionResponse queryLendHistory(@ApiParam(value = "assetLendRelation") @RequestBody ObjectQuery objectQuery)throws Exception{
        PageResult<AssetLendRelationResponse> assetLendRelationResponses= iAssetLendRelationService.queryHistory(objectQuery);
        return ActionResponse.success(assetLendRelationResponses);
    }


    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetLendRelationResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.queryAssetLendRelationById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.deleteAssetLendRelationById(baseRequest));
    }

    /**
     * 保存出借信息
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(value = "保存出借信息", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/lendInfo", method = RequestMethod.POST)
    public ActionResponse saveLendInfo(@ApiParam(value = "AssetLendInfo") AssetLendInfoRequest request) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.saveLendInfo(request));
    }

    /**
     * 审批单综合查询
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(value = "审批单综合查询", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/query/approveList", method = RequestMethod.POST)
    public ActionResponse queryApproveList(@ApiParam(value = "AssetLendInfo") ApproveListQuery request) throws Exception {
        return ActionResponse.success(iAssetLendRelationService.queryApproveList(request));
    }
}

