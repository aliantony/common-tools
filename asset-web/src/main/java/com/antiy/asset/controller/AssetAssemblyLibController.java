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
