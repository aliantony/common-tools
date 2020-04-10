package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.query.AssetAssemblyScrapRequest;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.ActionResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetAssembly", description = "资产组件关系表")
@RestController
@RequestMapping("/api/v1/asset/assetassembly")
public class AssetAssemblyController {

    @Resource
    public IAssetAssemblyService iAssetAssemblyService;

    /**
     * 批量查询
     *
     * @param assetAssemblyQuery
     * @return actionResponse
     */
    @ApiOperation(value = "可添加组件列表查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblyResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/enableList", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetAssembly") @RequestBody AssetAssemblyQuery assetAssemblyQuery) throws Exception {
        return ActionResponse.success(iAssetAssemblyService.queryPageAssetAssembly(assetAssemblyQuery));
    }

    /**
     * 报废暂存
     */
    @ApiOperation(value = "报废暂存", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/scrap/update", method = RequestMethod.POST)
    public ActionResponse scrapUpdate(@ApiParam(value = "assetAssembly") @RequestBody AssetAssemblyScrapRequest assetAssemblyScrapRequest) throws Exception {
        Integer result= iAssetAssemblyService.scrapUpdate(assetAssemblyScrapRequest);
        return ActionResponse.success(result);
    }

}
