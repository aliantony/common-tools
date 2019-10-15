package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSynchRedundant;
import com.antiy.asset.vo.query.AssetSynchCpeQuery;
import com.antiy.asset.vo.request.AreaIdRequest;
import com.antiy.asset.vo.request.AssetIdRequest;
import com.antiy.asset.vo.request.AssetMatchRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "", description = "根据区域ID返回资产UUID")
@RestController
@RequestMapping("/api/v1/asset")
public class AssetBusinessController {

    @Resource
    public IAssetService iAssetService;

    @Resource
    private IAssetSynchRedundant synchRedundant;

    /**
     * 根据区域ID返回资产UUID
     *
     * @param areaIdRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据区域ID返回资产UUID", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/uuidByAreaId", method = RequestMethod.POST)
    public ActionResponse queryUuidByAreaId(@RequestBody(required = false) @ApiParam(value = "areaIdRequest") AreaIdRequest areaIdRequest) throws Exception {
        return ActionResponse.success(iAssetService.queryUuidByAreaIds(areaIdRequest));
    }

    /**
     * 根据区域ID返回资产ID
     *
     * @param areaIdRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据区域ID返回资产ID", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/idByAreaId", method = RequestMethod.POST)
    public ActionResponse queryIdByAreaId(@RequestBody(required = false) @ApiParam(value = "areaIdRequest") AreaIdRequest areaIdRequest) throws Exception {
        return ActionResponse.success(iAssetService.queryIdByAreaIds(areaIdRequest));
    }

    /**
     * 根据IP/MAC判断资产是否存在
     *
     * @param assetMatchRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据IP+MAC判断资产是否存在", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/match/assetByIpMac", method = RequestMethod.POST)
    public ActionResponse queryIdByAreaId(@RequestBody(required = false) @ApiParam(value = "assetMatchRequest") AssetMatchRequest assetMatchRequest) throws Exception {
        return ActionResponse.success(iAssetService.matchAssetByIpMac(assetMatchRequest));
    }

    /**
     * 更新冗余字段接口-routing项目用
     *
     * @param synchCpeQuery
     * @return actionResponse
     */
    @ApiOperation(value = "更新冗余字段接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/redundantFiled", method = RequestMethod.POST)
    public ActionResponse queryIdByAreaId(@RequestBody(required = false) @ApiParam(value = "synchCpeQuery") AssetSynchCpeQuery synchCpeQuery) throws Exception {
        return ActionResponse.success(synchRedundant.synchRedundantAsset(synchCpeQuery));
    }

    /**
     * 根据资产ID返回资产UUID
     *
     * @param assetIdRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据区域ID返回资产UUID", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/uuidByAssetId", method = RequestMethod.POST)
    public ActionResponse queryUuidByAssetId(@RequestBody(required = false) @ApiParam(value = "assetIdRequest") AssetIdRequest assetIdRequest) throws Exception {
        return ActionResponse.success(iAssetService.queryUuidByAssetId(assetIdRequest));
    }

}
