package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.request.UniqueKeyRquest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
@Api(value = "AssetBusiness", description = "")
@RestController
@RequestMapping("/api/v1/asset/assetbusiness")
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
    public ActionResponse saveSingle(@ApiParam(value = "assetBusiness") @RequestBody  AssetBusinessRequest assetBusinessRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessService.saveAssetBusiness(assetBusinessRequest));
    }

    /**
     * 查询业务可以添加的资产
     */
    @ApiOperation(value = "查询业务可以添加的资产", notes = "传入实体对象信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/query/asset/list", method = RequestMethod.POST)
    public ActionResponse queryAsset(@RequestBody AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception {
        PageResult<AssetResponse> pageResult= iAssetBusinessService.queryAsset(assetAddOfBusinessQuery);
        return  ActionResponse.success(pageResult);
    }
    @ApiOperation(value = "查询业务信息", notes = "传入业务主键（uniqueId）")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetBusinessResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/query/info", method = RequestMethod.POST)
    public ActionResponse querySingle(@RequestBody UniqueKeyRquest uniqueKeyRquest) throws Exception {
        ParamterExceptionUtils.isNull(uniqueKeyRquest.getUniqueId(),"唯一键不能为空");
        AssetBusinessResponse assetBusinessResponse= iAssetBusinessService.getByUniqueId(uniqueKeyRquest.getUniqueId());
        return  ActionResponse.success(assetBusinessResponse);
    }

    /**
     * 业务已经关联的资产
     * @param assetAddOfBusinessQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "业务已经关联的资产信息", notes = "传入实体对象信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetBusinessRelationResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/query/assetRelation/list", method = RequestMethod.POST)
    public ActionResponse queryBusinessAsset(@RequestBody AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception {
        ParamterExceptionUtils.isNull(assetAddOfBusinessQuery.getUniqueId(),"唯一键不能为空");
        List<AssetBusinessRelationResponse> assetList= iAssetBusinessService.queryAssetByBusinessId(assetAddOfBusinessQuery);
        return  ActionResponse.success(assetList);
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
    public ActionResponse updateSingle(@ApiParam(value = "assetBusiness") @Valid @RequestBody AssetBusinessRequest assetBusinessRequest)throws Exception{
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
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetBusiness") @RequestBody  AssetBusinessQuery assetBusinessQuery)throws Exception{
        return ActionResponse.success(iAssetBusinessService.queryPageAssetBusiness(assetBusinessQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询 无用", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetBusinessResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition)throws Exception{
        return ActionResponse.success(iAssetBusinessService.queryAssetBusinessById(queryCondition));
    }


    /**
     * 删除业务
     */
    @ApiOperation(value = "通过uniqueId删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResponse deleteByUniqueId(@ApiParam(value = "assetBusinessRequest") @RequestBody UniqueKeyRquest uniqueKeyRquest)throws Exception{
        Integer result=iAssetBusinessService.updateStatusByUniqueId(uniqueKeyRquest.getUniqueIds());
        return ActionResponse.success(result);
    }
}

