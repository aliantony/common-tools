package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
@Api(value = "AssetBusiness", description = "")
@RestController
@RequestMapping("/v1/asset/assetbusiness")
public class AssetBusinessController {

    @Resource
    public IAssetBusinessService iAssetBusinessService;

    /**
     * 保存
     *
     * @param assetBusinessRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetBusiness") @RequestBody @Valid AssetBusinessRequest assetBusinessRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessService.saveAssetBusiness(assetBusinessRequest));
    }

    /**
     * 修改
     *
     * @param assetBusinessRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetBusiness") AssetBusinessRequest assetBusinessRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessService.updateAssetBusiness(assetBusinessRequest));
    }

    /**
     * 批量查询
     *
     * @param assetBusinessQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetBusinessResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetBusiness") AssetBusinessQuery assetBusinessQuery)throws Exception{
        return ActionResponse.success(iAssetBusinessService.queryPageAssetBusiness(assetBusinessQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetBusinessResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition)throws Exception{
        return ActionResponse.success(iAssetBusinessService.queryAssetBusinessById(queryCondition));
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
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessService.deleteAssetBusinessById(baseRequest));
    }
}

