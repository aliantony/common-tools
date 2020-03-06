package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetManufactureService;
import com.antiy.asset.vo.query.AssetManufactureQuery;
import com.antiy.asset.vo.request.AssetManufactureRequest;
import com.antiy.asset.vo.response.AssetManufactureResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
@Api(value = "AssetManufacture", description = "厂商管理")
@RestController
@RequestMapping("/api/v1/asset/manufacture")
public class AssetManufactureController {

    @Resource
    public IAssetManufactureService iAssetManufactureService;

    /**
     * 保存
     *
     * @param assetManufactureRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetManufacture") @RequestBody AssetManufactureRequest assetManufactureRequest) throws Exception {
        return ActionResponse.success(iAssetManufactureService.saveAssetManufacture(assetManufactureRequest));
    }

    /**
     * 修改
     *
     * @param assetManufactureRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetManufacture") AssetManufactureRequest assetManufactureRequest) throws Exception {
        return ActionResponse.success(iAssetManufactureService.updateAssetManufacture(assetManufactureRequest));
    }

    /**
     * 批量查询
     *
     * @param assetManufactureQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetManufactureResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetManufacture") AssetManufactureQuery assetManufactureQuery) throws Exception {
        return ActionResponse.success(iAssetManufactureService.queryPageAssetManufacture(assetManufactureQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetManufactureResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetManufactureService.queryAssetManufactureById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetManufactureService.deleteAssetManufactureById(baseRequest));
    }

    /**
     * 树形厂商数据
     *
     * @return actionResponse
     */
    @ApiOperation(value = "树形厂商数据", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/tree/safetyManufacture", method = RequestMethod.POST)
    public ActionResponse safetyManufacture() throws Exception {
        return ActionResponse.success(iAssetManufactureService.safetyManufacture());
    }

    /**
     * 通过厂商ID查询下属安全设备
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过厂商ID查询下属安全设备", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetManufactureResponse.class), })
    @RequestMapping(value = "/query/deviceById", method = RequestMethod.GET)
    public ActionResponse queryDeviceById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetManufactureService.queryAssetManufactureById(queryCondition));
    }
}
