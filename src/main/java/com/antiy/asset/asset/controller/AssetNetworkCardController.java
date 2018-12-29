package com.antiy.asset.asset.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;
import javax.annotation.Resource;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.asset.service.IAssetNetworkCardService;
import com.antiy.asset.asset.entity.AssetNetworkCard;
import com.antiy.asset.asset.entity.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.asset.entity.vo.response.AssetNetworkCardResponse;
import com.antiy.asset.asset.entity.vo.query.AssetNetworkCardQuery;



/**
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetNetworkCard", description = "网卡信息表")
@RestController
@RequestMapping("/v1/asset/assetNetworkCard")
@Slf4j
public class AssetNetworkCardController {

    @Resource
    public IAssetNetworkCardService iAssetNetworkCardService;

    /**
     * 保存
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetNetworkCard")AssetNetworkCardRequest assetNetworkCard)throws Exception{
        iAssetNetworkCardService.saveAssetNetworkCard(assetNetworkCard);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetNetworkCard")AssetNetworkCardRequest assetNetworkCard)throws Exception{
        iAssetNetworkCardService.updateAssetNetworkCard(assetNetworkCard);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetNetworkCard") AssetNetworkCardQuery assetNetworkCard)throws Exception{
        return ActionResponse.success(iAssetNetworkCardService.findPageAssetNetworkCard(assetNetworkCard));
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
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetNetworkCard") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetNetworkCardService.getById(query.getPrimaryKey()));
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
        return ActionResponse.success(iAssetNetworkCardService.deleteById(query.getPrimaryKey()));
    }
}

