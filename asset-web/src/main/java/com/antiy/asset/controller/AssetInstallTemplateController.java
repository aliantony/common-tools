package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetInstallTemplateCheckService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateOsAndStatusResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
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
@Api(value = "AssetInstallTemplate", description = "装机模板")
@RestController
@RequestMapping("/api/v1/asset/assetinstalltemplate")
public class AssetInstallTemplateController {

    @Resource
    private IAssetInstallTemplateService iAssetInstallTemplateService;
    @Resource
    private IAssetInstallTemplateCheckService iAssetInstallTemplateCheckService;

    /**
     * 装机模板综合查询： 模板列表
     *
     * @return
     */
    @ApiOperation(value = "查询装机模板操作系统和状态接口")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateOsAndStatusResponse.class),})
    @RequestMapping(value = "/query/operationSystemAndStatus", method = RequestMethod.POST)
    public ActionResponse queryOsAndStatus() {
        return ActionResponse.success(iAssetInstallTemplateService.queryOsAndStatus());
    }

    /**
     * 保存
     *
     * @param assetInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class),})
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetInstallTemplate") @RequestBody AssetInstallTemplateRequest assetInstallTemplateRequest) throws Exception {
        return ActionResponse
                .success(iAssetInstallTemplateService.saveAssetInstallTemplate(assetInstallTemplateRequest));
    }

    /**
     * 修改
     *
     * @param assetInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class),})
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetInstallTemplate") AssetInstallTemplateRequest assetInstallTemplateRequest) throws Exception {
        return ActionResponse
                .success(iAssetInstallTemplateService.updateAssetInstallTemplate(assetInstallTemplateRequest));
    }

    /**
     * 批量查询
     *
     * @param assetInstallTemplateQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateResponse.class, responseContainer = "List"),})
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetInstallTemplate") @RequestBody AssetInstallTemplateQuery assetInstallTemplateQuery) throws Exception {
        return ActionResponse
                .success(iAssetInstallTemplateService.queryPageAssetInstallTemplate(assetInstallTemplateQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateResponse.class),})
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetInstallTemplateService.queryAssetInstallTemplateById(queryCondition));
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
        return ActionResponse.success(iAssetInstallTemplateService.deleteAssetInstallTemplateById(baseRequest));
    }

    @ApiOperation(value = "资产关联的装机模板信息", notes = "传入资产ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetTemplateRelationResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/query/relationInfo", method = RequestMethod.POST)
    public AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "资产Id不能为空");
        AssetTemplateRelationResponse assetTemplateRelationResponse = iAssetInstallTemplateService.queryTemplateByAssetId(queryCondition);
        return assetTemplateRelationResponse;
    }

    @ApiOperation(value = "模板审核信息", notes = "传入模板ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateCheckResponse.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/query/auditInfo", method = RequestMethod.POST)
    public List<AssetInstallTemplateCheckResponse> queryTemplateCheckByTemplateId(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "装机模板Id不能为空");
        List<AssetInstallTemplateCheckResponse> templateCheckResponses = iAssetInstallTemplateCheckService.queryTemplateCheckByTemplateId(queryCondition);
        return templateCheckResponses;
    }
}
