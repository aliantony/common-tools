package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "AssetSoftwareLicense", description = "软件许可表")
@RestController
@RequestMapping("/api/v1/asset/softwarelicense")
public class AssetSoftwareLicenseController {
    private static final Logger         logger = LogUtils.get();

    @Resource
    public IAssetSoftwareLicenseService iAssetSoftwareLicenseService;

    /**
     * 保存
     * @param assetSoftwareLicenseRequest
     * @return actionResponse
     */
    @PreAuthorize("hasAuthority('asset:softwarelicense:saveSingle')")
    @ApiOperation(value = "（无效）保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftwareLicense") @RequestBody AssetSoftwareLicenseRequest assetSoftwareLicenseRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareLicenseService.saveAssetSoftwareLicense(assetSoftwareLicenseRequest));
    }

    /**
     * 修改
     * @param assetSoftwareLicenseRequest
     * @return actionResponse
     */
    @PreAuthorize("hasAuthority('asset:softwarelicense:updateSingle')")
    @ApiOperation(value = "（无效）修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftwareLicense") @RequestBody AssetSoftwareLicenseRequest assetSoftwareLicenseRequest) throws Exception {
        iAssetSoftwareLicenseService.updateAssetSoftwareLicense(assetSoftwareLicenseRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetSoftwareLicenseQuery
     * @return actionResponse
     */
    @PreAuthorize("hasAuthority('asset:softwarelicense:queryList')")
    @ApiOperation(value = "（无效）批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareLicense") @RequestBody AssetSoftwareLicenseQuery assetSoftwareLicenseQuery) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareLicenseService.findPageAssetSoftwareLicense(assetSoftwareLicenseQuery));
    }

    /**
     * 通过ID查询
     * @param queryCondition
     * @return actionResponse
     */
    @PreAuthorize("hasAuthority('asset:softwarelicense:queryById')")
    @ApiOperation(value = "（无效）通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "assetSoftwareLicense") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID删除接口", notes = "主键封装对象")
    @PreAuthorize("hasAuthority('asset:softwarelicense:deleteById')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestBody BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isNull(baseRequest.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.deleteById(baseRequest.getStringId()));
    }
}
