package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetCpeFilterService;
import com.antiy.asset.vo.request.AssetCpeFilterRequest;
import com.antiy.asset.vo.response.AssetCpeFilterResponse;
import com.antiy.asset.vo.query.AssetCpeFilterQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetCpeFilter", description = "过滤显示表（筛选指定的数据给用户）")
@RestController
@RequestMapping("/api/v1/asset/cpefilter")
public class AssetCpeFilterController {

    // @Resource
    // public IAssetCpeFilterService iAssetCpeFilterService;
    //
    // /**
    // * 保存
    // *
    // * @param assetCpeFilterRequest
    // * @return actionResponse
    // */
    // @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    // @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    // public ActionResponse saveSingle(@ApiParam(value = "assetCpeFilter") @RequestBody AssetCpeFilterRequest
    // assetCpeFilterRequest) throws Exception {
    // return ActionResponse.success(iAssetCpeFilterService.saveAssetCpeFilter(assetCpeFilterRequest));
    // }
    //
    // /**
    // * 修改
    // *
    // * @param assetCpeFilterRequest
    // * @return actionResponse
    // */
    // @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    // @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    // public ActionResponse updateSingle(@ApiParam(value = "assetCpeFilter") AssetCpeFilterRequest
    // assetCpeFilterRequest) throws Exception {
    // return ActionResponse.success(iAssetCpeFilterService.updateAssetCpeFilter(assetCpeFilterRequest));
    // }
    //
    // /**
    // * 批量查询
    // *
    // * @param assetCpeFilterQuery
    // * @return actionResponse
    // */
    // @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeFilterResponse.class,
    // responseContainer = "List"), })
    // @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    // public ActionResponse queryList(@ApiParam(value = "assetCpeFilter") AssetCpeFilterQuery assetCpeFilterQuery)
    // throws Exception {
    // return ActionResponse.success(iAssetCpeFilterService.queryPageAssetCpeFilter(assetCpeFilterQuery));
    // }
    //
    // /**
    // * 通过ID查询
    // *
    // * @param queryCondition
    // * @return actionResponse
    // */
    // @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeFilterResponse.class), })
    // @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    // public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
    // return ActionResponse.success(iAssetCpeFilterService.queryAssetCpeFilterById(queryCondition));
    // }
    //
    // /**
    // * 通过ID删除
    // *
    // * @param baseRequest
    // * @return actionResponse
    // */
    // @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    // @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    // public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
    // return ActionResponse.success(iAssetCpeFilterService.deleteAssetCpeFilterById(baseRequest));
    // }
}
