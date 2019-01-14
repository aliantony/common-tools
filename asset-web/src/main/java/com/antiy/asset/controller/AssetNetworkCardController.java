package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetNetworkCardService;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetNetworkCard", description = "网卡信息表")
@RestController
@RequestMapping("/api/v1/asset/assetnetworkcard")
public class AssetNetworkCardController {

    @Resource
    public IAssetNetworkCardService iAssetNetworkCardService;

    /**
     * 保存
     *
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetNetworkCard") AssetNetworkCardRequest assetNetworkCard) throws Exception {
        iAssetNetworkCardService.saveAssetNetworkCard(assetNetworkCard);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetNetworkCard") AssetNetworkCardRequest assetNetworkCard) throws Exception {
        iAssetNetworkCardService.updateAssetNetworkCard(assetNetworkCard);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetNetworkCard
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetNetworkCard") AssetNetworkCardQuery assetNetworkCard) throws Exception {
        PageResult<AssetNetworkCardResponse> pageResult = iAssetNetworkCardService
            .findPageAssetNetworkCard(assetNetworkCard);
        return ActionResponse.success(iAssetNetworkCardService.findPageAssetNetworkCard(assetNetworkCard));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetNetworkCard") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetNetworkCardService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetNetworkCardService.deleteById(id));
    }
}
