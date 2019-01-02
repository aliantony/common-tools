package com.antiy.asset.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;
import javax.annotation.Resource;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.vo.request.AssetSoftwareRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareResponse;
import com.antiy.asset.entity.vo.query.AssetSoftwareQuery;


/**
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetSoftware", description = "软件信息表")
@RestController
@RequestMapping("/v1/asset/assetsoftware")
@Slf4j
public class AssetSoftwareController {

    @Resource
    public IAssetSoftwareService iAssetSoftwareService;

    /**
     * 保存
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetSoftware")AssetSoftwareRequest assetSoftware)throws Exception{
        iAssetSoftwareService.saveAssetSoftware(assetSoftware);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftware")AssetSoftwareRequest assetSoftware)throws Exception{
        iAssetSoftwareService.updateAssetSoftware(assetSoftware);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetSoftware") AssetSoftwareQuery assetSoftware)throws Exception{
        return ActionResponse.success(iAssetSoftwareService.findPageAssetSoftware(assetSoftware));
    }

    /**
     * 通过ID查询
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetSoftware") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.deleteById(query.getPrimaryKey()));
    }
}

