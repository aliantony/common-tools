package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetLicenseService;
import com.antiy.asset.vo.response.AlarmAssetResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author: zhangyajun
 * @date: 2019/6/22 14:10
 * @description:
 */
@Api(value = "LicenseController", description = "告警资产")
@RestController
@RequestMapping("/api/v1/asset/license")
public class AssetLicenseController {

    @Resource
    private IAssetLicenseService licenseService;

    /**
     * 授权数量校验
     *
     * @param
     * @return actionResponse
     */

    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AlarmAssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/validate/authNum", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:alarm:queryList')")
    public ActionResponse validateAuthNum() throws Exception {
        return ActionResponse.success(licenseService.validateAuthNum());
    }
}
