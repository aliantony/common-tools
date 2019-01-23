package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetDepartment", description = "资产部门信息")
@RestController
@RequestMapping("/api/v1/asset/department")
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
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:department:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetDepartment") AssetDepartmentRequest assetDepartment)
                                                                                                                              throws Exception {
        ParamterExceptionUtils.isNull(assetDepartment, "请求不能为空");
        ParamterExceptionUtils.isNull(assetDepartment.getName(), "部门名不能为空");
        ParamterExceptionUtils.isNull(assetDepartment.getParentId(), "父级部门不能为空");
        return iAssetDepartmentService.saveAssetDepartment(assetDepartment);
    }

    /**
     * 修改
     *
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:department:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetDepartment") AssetDepartmentRequest assetDepartment)
                                                                                                                                throws Exception {
        ParamterExceptionUtils.isNull(assetDepartment, "请求不能为空");
        ParamterExceptionUtils.isNull(assetDepartment.getId(), "部门名不能为空");
        return iAssetDepartmentService.updateAssetDepartment(assetDepartment);
    }

    /**
     * 批量查询
     *
     * @param assetDepartment
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetDepartmentResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:department:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetDepartment") AssetDepartmentQuery assetDepartment)
                                                                                                              throws Exception {
        return ActionResponse.success(iAssetDepartmentService.findPageAssetDepartment(assetDepartment));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetDepartmentResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:department:queryById')")
    public ActionResponse queryById(@PathVariable @ApiParam(value = "assetDepartment") @Encode String id)
                                                                                                          throws Exception {
        ParamterExceptionUtils.isNull(id, "id不能为空");
        return ActionResponse.success(iAssetDepartmentService.getById(Integer.parseInt(id)));

    }

    /**
     * 通过ID删除 删除后会把该部门下的所有用户的部门id置为null
     *
     * @param id 主键,isConfirm 是否已经确认删除
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:department:deleteById')")
    public ActionResponse deleteById(@PathVariable @ApiParam(value = "id")@Encode String id) throws Exception {
        ParamterExceptionUtils.isNull(id, "id不能为空");
        return iAssetDepartmentService.deleteAllById(Integer.parseInt(id));
    }

    @ApiOperation(value = "通过ID查询所有部门信息及子部门信息", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetDepartmentResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:department:getByID')")
    public ActionResponse getByID(@PathVariable @ApiParam(value = "id") @Encode String id) throws Exception {
        ParamterExceptionUtils.isNull(id, "id不能为空");
        return ActionResponse.success(iAssetDepartmentService.findAssetDepartmentById(Integer.parseInt(id)));
    }

    /**
     * 查询部门的树形结构
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询部门树形结构", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetDepartmentNodeResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/node", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:department:queryDepartmentNode')")
    public ActionResponse queryDepartmentNode() throws Exception {
        return ActionResponse.success(iAssetDepartmentService.findDepartmentNode());
    }
}
