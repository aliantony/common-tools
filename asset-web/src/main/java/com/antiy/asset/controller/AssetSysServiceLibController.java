package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSysServiceLibService;
import com.antiy.asset.vo.request.AssetSysServiceLibRequest;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.asset.vo.query.AssetSysServiceLibQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSysServiceLib", description = "服务表")
@RestController
@RequestMapping("/api/v1/asset/assetsysservicelib")
public class AssetSysServiceLibController {

    @Resource
    public IAssetSysServiceLibService iAssetSysServiceLibService;

    /**
     * 批量查询
     *
     * @param assetSysServiceLibQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSysServiceLibResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetSysServiceLib") @RequestBody AssetSysServiceLibQuery assetSysServiceLibQuery) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.queryPageAssetSysServiceLib(assetSysServiceLibQuery));
    }

}
