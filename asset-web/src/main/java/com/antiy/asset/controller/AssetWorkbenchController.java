package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AreaIdRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(description = "工作台接口")
@RestController
@RequestMapping("/api/v1/asset")
public class AssetWorkbenchController {

    @Resource
    public IAssetService iAssetService;

    /**
     * 根据用户区域统计未知资产数量
     * @param areaIdRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据区域ID返回资产UUID", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/unknownAssetAmount", method = RequestMethod.POST)
    public ActionResponse queryUnknownAssetCount(AreaIdRequest areaIdRequest) throws Exception {
        return ActionResponse.success(iAssetService.queryUnknownAssetCount(areaIdRequest));
    }
}
