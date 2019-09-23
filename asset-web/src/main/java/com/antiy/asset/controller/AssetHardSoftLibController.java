package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "AssetHardSoftLib", description = "CPE表")
@RestController
@RequestMapping("/api/v1/asset/assethardsoftlib")
public class AssetHardSoftLibController {

    @Resource
    public IAssetHardSoftLibService iAssetHardSoftLibService;

    /**
     * 保存
     *
     * @param assetHardSoftLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetHardSoftLib") @RequestBody AssetHardSoftLibRequest assetHardSoftLibRequest) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.saveAssetHardSoftLib(assetHardSoftLibRequest));
    }

    /**
     * 修改
     *
     * @param assetHardSoftLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetHardSoftLib") AssetHardSoftLibRequest assetHardSoftLibRequest) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.updateAssetHardSoftLib(assetHardSoftLibRequest));
    }

    /**
     * 批量查询
     *
     * @param assetHardSoftLibQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetHardSoftLibResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetHardSoftLib") AssetHardSoftLibQuery assetHardSoftLibQuery) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.queryPageAssetHardSoftLib(assetHardSoftLibQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetHardSoftLibResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.queryAssetHardSoftLibById(queryCondition));
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
        return ActionResponse.success(iAssetHardSoftLibService.deleteAssetHardSoftLibById(baseRequest));
    }

    @ApiOperation(value = "分页查询资产关联的软件信息列表", notes = "必传资产ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SoftwareResponse.class, responseContainer = "actionResponse")})
    @RequestMapping(value = "/software/list", method = RequestMethod.POST)
    public ActionResponse getPageSoftwarePage(@ApiParam(value = "assetId") @RequestBody AssetSoftwareQuery queryCondition) {
        return ActionResponse.success(iAssetHardSoftLibService.getPageSoftWareList(queryCondition));
    }

    /**
     * 操作系统(下拉项)
     *
     * @return actionResponse
     */
    @ApiOperation(value = "操作系统下拉选项", notes = "操作系统")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class), })
    @RequestMapping(value = "/pullDown/os", method = RequestMethod.POST)
    public ActionResponse pullDownOs(@RequestBody(required = false) OsQuery osQuery) {
        return ActionResponse.success(iAssetHardSoftLibService.pullDownOs(osQuery));
    }
}
