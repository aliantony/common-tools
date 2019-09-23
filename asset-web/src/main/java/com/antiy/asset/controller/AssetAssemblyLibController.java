package com.antiy.asset.controller;

import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetAssemblyLibService;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.vo.query.AssetAssemblyLibQuery;

import java.util.List;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetAssemblyLib", description = "组件表")
@RestController
@RequestMapping("/api/v1/asset/assetassemblylib")
public class AssetAssemblyLibController {

    @Resource
    public IAssetAssemblyLibService iAssetAssemblyLibService;

    /**
     * 保存
     *
     * @param assetAssemblyLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetAssemblyLib") @RequestBody AssetAssemblyLibRequest assetAssemblyLibRequest) throws Exception {
        return ActionResponse.success(iAssetAssemblyLibService.saveAssetAssemblyLib(assetAssemblyLibRequest));
    }

    /**
     * 修改
     *
     * @param assetAssemblyLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetAssemblyLib") AssetAssemblyLibRequest assetAssemblyLibRequest) throws Exception {
        return ActionResponse.success(iAssetAssemblyLibService.updateAssetAssemblyLib(assetAssemblyLibRequest));
    }

    /**
     * 批量查询
     *
     * @param assetAssemblyLibQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblyLibResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetAssemblyLib") AssetAssemblyLibQuery assetAssemblyLibQuery) throws Exception {
        return ActionResponse.success(iAssetAssemblyLibService.queryPageAssetAssemblyLib(assetAssemblyLibQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblyLibResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetAssemblyLibService.queryAssetAssemblyLibById(queryCondition));
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
        return ActionResponse.success(iAssetAssemblyLibService.deleteAssetAssemblyLibById(baseRequest));
    }

    /**
     * 通过四库id查询关联组件
     * @author zhangyajun
     *
     * @return
     */
    @ApiOperation(value = "通过四库id查询关联组件", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assembly/id", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:queryAssemblyById')")
    public ActionResponse<List<AssetAssemblyResponse>> queryAssemblyByHardSoftId(@ApiParam(value = "下拉查询类") @RequestBody QueryCondition query) throws Exception {
        ParamterExceptionUtils.isNull(query, "id不能为空");
        ParamterExceptionUtils.isNull(query.getPrimaryKey(), "id不能为空");
        return ActionResponse.success(iAssetAssemblyLibService.queryAssemblyByHardSoftId(query));
    }
}
