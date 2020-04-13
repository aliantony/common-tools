package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetNettypeManageService;
import com.antiy.asset.vo.query.AssetNettypeManageQuery;
import com.antiy.asset.vo.request.AssetNettypeManageRequest;
import com.antiy.asset.vo.response.AssetNettypeManageResponse;
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
 * @author wangqian
 * @since 2020-04-07
 */
@Api(value = "AssetNettypeManage", description = "网络类型管理api")
@RestController
@RequestMapping("/v1/asset/assetnettypemanage")
public class AssetNettypeManageController {

    @Resource
    public IAssetNettypeManageService iAssetNettypeManageService;

    /**
     * 保存
     *
     * @param assetNettypeManageRequest
     * @return actionResponse
     */
    @ApiOperation(value = "新增网络类型", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetNettypeManage") @RequestBody AssetNettypeManageRequest assetNettypeManageRequest)throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.saveAssetNettypeManage(assetNettypeManageRequest));
    }

    /**
     * 修改
     *
     * @param assetNettypeManageRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改网络类型", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetNettypeManage") @RequestBody AssetNettypeManageRequest assetNettypeManageRequest)throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.updateAssetNettypeManage(assetNettypeManageRequest));
    }

    /**
     * 批量查询
     *
     * @param assetNettypeManageQuery
     * @return actionResponse
     */
    @ApiOperation(value = "分页查询", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetNettypeManageResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetNettypeManage") @RequestBody AssetNettypeManageQuery assetNettypeManageQuery)throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.queryPageAssetNettypeManage(assetNettypeManageQuery));
    }


    /**
     * 批量查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询全部", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetNettypeManageResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/getAll", method = RequestMethod.POST)
    public ActionResponse queryListAll()throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.getAllList());
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetNettypeManageResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") @RequestBody QueryCondition queryCondition)throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.queryAssetNettypeManageById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除网络类型", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") @RequestBody BaseRequest baseRequest)throws Exception{
        return ActionResponse.success(iAssetNettypeManageService.deleteAssetNettypeManageById(baseRequest));
    }
}

