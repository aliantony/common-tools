package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.service.IAssetWorkbenchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AssetUnknownRequest;
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

    @Resource
    private IAssetWorkbenchService iAssetWorkbenchService;

    /**
     * 根据用户区域统计未知资产数量
     * @param unknownRequest
     * @return actionResponse
     */
    @ApiOperation(value = "根据区域ID返回资产UUID", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/unknownAssetAmount", method = RequestMethod.POST)
    public ActionResponse queryUnknownAssetCount(AssetUnknownRequest unknownRequest) throws Exception {
        return ActionResponse.success(iAssetService.queryUnknownAssetCount(unknownRequest));
    }

    /**
     * 功能描述 : 查询工作台各资产数量
     * @author ygh
     * @date 19:37
     */
    @ApiOperation(value = "退回和报废申请和执行数量", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/workbench/num", method = RequestMethod.POST)
    public ActionResponse queryAssetWorkNum() throws Exception {
        return ActionResponse.success(iAssetWorkbenchService.queryWorkBench());
    }

}
