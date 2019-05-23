package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AlarmAssetRequest;
import com.antiy.asset.vo.response.AlarmAssetResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author: zhangbing
 * @date: 2019/5/15 10:57
 * @description:
 */
@Api(value = "AlarmAssetController", description = "告警资产")
@RestController
@RequestMapping("/api/v1/asset/alarm")
public class AlarmAssetController {

    @Resource
    private IAssetService assetService;

    /**
     * 批量查询
     *
     * @param request
     * @return actionResponse
     */

    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AlarmAssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:alarm:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "asset") AlarmAssetRequest request) throws Exception {
        return ActionResponse.success(assetService.queryAlarmAssetList(request));
    }
}
