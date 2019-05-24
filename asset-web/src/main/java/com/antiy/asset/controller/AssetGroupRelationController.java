package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetGroupRelation", description = "资产与资产组关系表")
@RestController
@RequestMapping("/api/v1/asset/grouprelation")
public class                       AssetGroupRelationController {

    @Resource
    public IAssetGroupRelationService iAssetGroupRelationService;

    /**
     * 保存
     *
     * @param assetGroupRelation
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetGroupRelation") AssetGroupRelationRequest assetGroupRelation) throws Exception {
        iAssetGroupRelationService.saveAssetGroupRelation(assetGroupRelation);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetGroupRelation
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetGroupRelation") AssetGroupRelationRequest assetGroupRelation) throws Exception {
        iAssetGroupRelationService.updateAssetGroupRelation(assetGroupRelation);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetGroupRelation
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetGroupRelation") AssetGroupRelationQuery assetGroupRelation) throws Exception {
        return ActionResponse.success(iAssetGroupRelationService.findPageAssetGroupRelation(assetGroupRelation));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetGroupRelation") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetGroupRelationService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetGroupRelationService.deleteById(query.getPrimaryKey()));
    }
}
