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
public class AssetGroupRelationController {

    @Resource
    public IAssetGroupRelationService iAssetGroupRelationService;

    /**
     * 判断资产组是否存在关联资产
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "判断资产组是否存在关联资产", notes = "判断资产组是否存在关联资产")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/hasRealtionAsset", method = RequestMethod.POST)
    @PreAuthorize(value="hasAuthority('asset:grouprelation:queryById')")
    public ActionResponse hasRealtionAsset(@RequestBody @ApiParam(value = "assetGroupRelation") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "资产组ID不能为空");
        return ActionResponse.success(iAssetGroupRelationService.hasRealtionAsset(query.getPrimaryKey()) > 0);
    }
}
