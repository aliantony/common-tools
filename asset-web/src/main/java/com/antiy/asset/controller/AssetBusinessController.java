package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AreaIdRequest;
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

}
