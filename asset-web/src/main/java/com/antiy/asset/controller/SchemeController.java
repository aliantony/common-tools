package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.query.AssetIDAndSchemeTypeQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * 方案表控制器
 *
 * @author zhangyajun
 * @create 2019-01-27 10:57
 **/
@Api(value = "SchemeController", description = "方案信息表")
@RestController
@RequestMapping("/api/v1/asset/scheme")
public class SchemeController {

    @Resource
    private ISchemeService iSchemeService;

    /**
     * 批量查询
     *
     * @param schemeQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SchemeResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:scheme:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") SchemeQuery schemeQuery) throws Exception {
        return ActionResponse.success(iSchemeService.findPageScheme(schemeQuery));
    }

    /**
     * 通过ID和方案类型查询
     *
     * @param assetIDAndSchemeTypeQuery 条件封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Scheme.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/AssetIdAndType", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:scheme:queryByAssetId')")
    public ActionResponse queryByAssetIdAndType(@ApiParam(value = "queryCondition") AssetIDAndSchemeTypeQuery assetIDAndSchemeTypeQuery) throws Exception {
        ParamterExceptionUtils.isNull(assetIDAndSchemeTypeQuery.getAssetId(), "资产ID不能为空");
        return ActionResponse.success(iSchemeService.findSchemeByAssetIdAndType(assetIDAndSchemeTypeQuery));
    }
}
