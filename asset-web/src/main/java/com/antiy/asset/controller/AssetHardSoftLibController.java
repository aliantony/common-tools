package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.BusinessSelectResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class),})
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class),})
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetHardSoftLibResponse.class, responseContainer = "List"),})
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetHardSoftLib") @RequestBody AssetHardSoftLibQuery assetHardSoftLibQuery) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.queryPageAssetHardSoftLib(assetHardSoftLibQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetHardSoftLibResponse.class),})
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class),})
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.deleteAssetHardSoftLibById(baseRequest));
    }

    @ApiOperation(value = "分页查询资产关联的软件信息列表", notes = "必传资产ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = SoftwareResponse.class, responseContainer = "actionResponse")})
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = OsSelectResponse.class),})
    @RequestMapping(value = "/pullDown/os", method = RequestMethod.POST)
    public ActionResponse pullDownOs(@RequestBody(required = false) OsQuery osQuery) {
        return ActionResponse.success(iAssetHardSoftLibService.pullDownOs(osQuery));
    }

    /**
     * 查询下拉的厂商信息
     *
     * @return 厂商名称集合
     * @author zhangyajun
     */
    @ApiOperation(value = "查询厂商接口", notes = "无查询条件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/pullDown/supplier", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:pulldownManufacturer')")
    public ActionResponse<List<String>> pulldownManufacturer(@ApiParam(value = "下拉查询类") @RequestBody AssetPulldownQuery query) throws Exception {
        return ActionResponse.success(iAssetHardSoftLibService.pulldownSupplier(query));
    }

    /**
     * 查询下拉的名称信息
     *
     * @return 查询下拉的名称信息
     * @author zhangyajun
     */
    @ApiOperation(value = "查询下拉的名称信息", notes = "无查询条件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/pullDown/name", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:pulldownName')")
    public ActionResponse<List<String>> pulldownName(@ApiParam(value = "下拉查询类") @RequestBody AssetPulldownQuery query) throws Exception {
        ParamterExceptionUtils.isNull(query, "厂商不能为空");
        ParamterExceptionUtils.isBlank(query.getSupplier(), "厂商不能为空");
        return ActionResponse.success(iAssetHardSoftLibService.pulldownName(query));
    }

    /**
     * 查询下拉的名称信息
     *
     * @return 查询下拉的名称信息
     * @author zhangyajun
     */
    @ApiOperation(value = "查询下拉的版本信息", notes = "无查询条件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/pullDown/version", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:pulldownVersion')")
    public ActionResponse<List<BusinessSelectResponse>> pulldownVersion(@ApiParam(value = "下拉查询类") @RequestBody AssetPulldownQuery query) throws Exception {
        ParamterExceptionUtils.isNull(query, "厂商不能为空");
        ParamterExceptionUtils.isNull(query.getSupplier(), "厂商不能为空");
        ParamterExceptionUtils.isNull(query.getName(), "名称不能为空");
        return ActionResponse.success(iAssetHardSoftLibService.pulldownVersion(query));
    }

    /**
     * 软件列表分页(装机模板-添加软件）
     *
     * @return actionResponse
     */
    @ApiOperation(value = "装机模板-添加软件", notes = "软件列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = OsSelectResponse.class),})
    @RequestMapping(value = "/query/softList", method = RequestMethod.POST)
    public ActionResponse queryPageSoft(@RequestBody AssetSoftwareQuery query) {
        return ActionResponse.success(iAssetHardSoftLibService.queryPageSoft(query));
    }

}
