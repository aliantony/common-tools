package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSoftServiceLibService;
import com.antiy.asset.vo.request.AssetSoftServiceLibRequest;
import com.antiy.asset.vo.response.AssetSoftServiceLibResponse;
import com.antiy.asset.vo.query.AssetSoftServiceLibQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSoftServiceLib", description = "软件提供的服务")
@RestController
@RequestMapping("/api/v1/asset/assetsoftservicelib")
public class AssetSoftServiceLibController {

    @Resource
    public IAssetSoftServiceLibService iAssetSoftServiceLibService;

    /**
     * 保存
     *
     * @param assetSoftServiceLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftServiceLib") @RequestBody AssetSoftServiceLibRequest assetSoftServiceLibRequest) throws Exception {
        return ActionResponse.success(iAssetSoftServiceLibService.saveAssetSoftServiceLib(assetSoftServiceLibRequest));
    }

    /**
     * 修改
     *
     * @param assetSoftServiceLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftServiceLib") AssetSoftServiceLibRequest assetSoftServiceLibRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceLibService.updateAssetSoftServiceLib(assetSoftServiceLibRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSoftServiceLibQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftServiceLibResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftServiceLib") AssetSoftServiceLibQuery assetSoftServiceLibQuery) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceLibService.queryPageAssetSoftServiceLib(assetSoftServiceLibQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftServiceLibResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetSoftServiceLibService.queryAssetSoftServiceLibById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetSoftServiceLibService.deleteAssetSoftServiceLibById(baseRequest));
    }
}
