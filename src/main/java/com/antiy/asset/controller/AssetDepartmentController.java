package com.antiy.asset.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;
import javax.annotation.Resource;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.vo.request.AssetDepartmentRequest;
import com.antiy.asset.entity.vo.response.AssetDepartmentResponse;
import com.antiy.asset.entity.vo.query.AssetDepartmentQuery;


/**
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetDepartment", description = "资产部门信息")
@RestController
@RequestMapping("/v1/asset/assetdepartment")
@Slf4j
public class AssetDepartmentController {

    @Resource
    public IAssetDepartmentService iAssetDepartmentService;

    /**
     * 保存
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetDepartment")AssetDepartmentRequest assetDepartment)throws Exception{
        iAssetDepartmentService.saveAssetDepartment(assetDepartment);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetDepartment")AssetDepartmentRequest assetDepartment)throws Exception{
        iAssetDepartmentService.updateAssetDepartment(assetDepartment);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetDepartment") AssetDepartmentQuery assetDepartment)throws Exception{
        return ActionResponse.success(iAssetDepartmentService.findPageAssetDepartment(assetDepartment));
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
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetDepartment") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetDepartmentService.getById(query.getPrimaryKey()));
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
        return ActionResponse.success(iAssetDepartmentService.deleteById(query.getPrimaryKey()));
    }
}

