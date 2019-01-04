package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.request.AssetDepartmentUpdateRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetDepartment", description = "资产部门信息")
@RestController
@RequestMapping("/v1/asset/assetdepartment")
public class AssetDepartmentController {

    @Resource
    public IAssetDepartmentService iAssetDepartmentService;

    /**
     * 保存
     *
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetDepartment") AssetDepartmentRequest assetDepartment) throws Exception {
        Boolean success = iAssetDepartmentService.saveAssetDepartment(assetDepartment) > 0;
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "保存失败");
        }
    }

    /**
     * 修改
     *
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetDepartment") AssetDepartmentUpdateRequest assetDepartment) throws Exception {
        Boolean success = iAssetDepartmentService.updateAssetDepartment(assetDepartment) > 0;
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "修改失败");
        }
    }

    /**
     * 批量查询
     *
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetDepartment") AssetDepartmentQuery assetDepartment) throws Exception {
        return ActionResponse.success(iAssetDepartmentService.findPageAssetDepartment(assetDepartment));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable @ApiParam(value = "assetDepartment") Integer id) throws Exception {
        return ActionResponse.success(iAssetDepartmentService.getById(id));

    }

    /**
     * 通过ID删除
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@PathVariable @ApiParam(value = "id") Integer id) throws Exception {
        Boolean success = iAssetDepartmentService.deleteById(id) > 0;
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "删除失败");
        }
    }
}

