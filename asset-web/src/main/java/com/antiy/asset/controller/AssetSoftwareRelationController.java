package com.antiy.asset.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareDetailResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "AssetSoftwareRelation", description = "资产软件关系信息")
@RestController
@RequestMapping("/api/v1/asset/softwarerelation")
public class AssetSoftwareRelationController {
    private static final Logger          logger = LogUtils.get();

    @Resource
    public IAssetSoftwareRelationService iAssetSoftwareRelationService;

    /**
     * 保存
     *
     * @param assetSoftwareRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)保存接口", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:softwarerelation:saveSingle')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftwareRelation") @RequestBody AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareRelationService.saveAssetSoftwareRelation(assetSoftwareRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetSoftwareRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)修改接口", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:softwarerelation:updateSingle')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception {
        iAssetSoftwareRelationService.updateAssetSoftwareRelation(assetSoftwareRelationRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetSoftwareRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量查询接口", notes = "传入查询条件")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryList')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareRelationResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationQuery assetSoftwareRelationQuery) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareRelationService.findPageAssetSoftwareRelation(assetSoftwareRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID查询", notes = "主键封装对象")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryById')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareRelationResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetSoftwareRelation") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID删除接口", notes = "主键封装对象")
    @PreAuthorize("hasAuthority('asset:softwarerelation:deleteById')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestBody BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isNull(baseRequest.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.deleteById(baseRequest.getStringId()));
    }

    /**
     * 通过软件ID统计资产数量
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过软件ID统计资产数量", notes = "软件ID")
    @PreAuthorize("hasAuthority('asset:softwarerelation:countAssetBySoftId')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/countAssetBySoftId", method = RequestMethod.GET)
    public ActionResponse countAssetBySoftId(@ApiParam(value = "id") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService
            .countAssetBySoftId(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())));
    }

    /**
     * 查询硬件资产关联的软件列表
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "查询硬件资产关联的软件列表", notes = "资产ID")
    @PreAuthorize("hasAuthority('asset:softwarerelation:getSoftwareByAssetId')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/getSoftwareByAssetId", method = RequestMethod.GET)
    public ActionResponse getSoftwareByAssetId(@ApiParam(value = "assetId") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "资产ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService
            .getSoftByAssetId(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())));
    }

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return 操作系统名称集合
     */
    @ApiOperation(value = "查询操作系统接口", notes = "无查询条件")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryOS')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/os", method = RequestMethod.GET)
    public ActionResponse<List<String>> queryOS() throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.findOS());
    }

    /**
     * 自动安装软件
     * @param assetSoftwareRelationList
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "（无效）自动安装软件", notes = "安装软件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/install/auto", method = RequestMethod.POST)
    public ActionResponse installAauto(@ApiParam(value = "softwareQuery") @RequestBody List<AssetSoftwareRelationRequest> assetSoftwareRelationList) throws Exception {
        iAssetSoftwareRelationService.installAauto(assetSoftwareRelationList);
        return ActionResponse.success();
    }

    /**
     * 人工安装软件
     * @param assetSoftwareRelationList
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "（无效）手动安装软件", notes = "安装软件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/software/artificial", method = RequestMethod.POST)
    public ActionResponse installArtificial(@ApiParam(value = "softwareQuery") @RequestBody List<AssetSoftwareRelationRequest> assetSoftwareRelationList) throws Exception {
        iAssetSoftwareRelationService.installArtificial(assetSoftwareRelationList);
        return ActionResponse.success();
    }

    /**
     * 安装软件
     * @param assetSoftwareRelationList
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "安装软件", notes = "安装软件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/software/install", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:softwarerelation:installSoftware')")
    public ActionResponse installSoftware(@ApiParam(value = "softwareQuery") @RequestBody AssetSoftwareRelationList assetSoftwareRelationList) throws Exception {
        iAssetSoftwareRelationService.installSoftware(assetSoftwareRelationList);
        return ActionResponse.success();
    }

    /**
     * 根据资产id分页查询关联的软件信息
     * @param queryCondition
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "分页查询硬件资产关联的软件列表", notes = "必传资产ID")
    @PreAuthorize("hasAuthority('asset:softwarerelation:querySimpleSoftwareByAssetId')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/simpleSoftwareList", method = RequestMethod.GET)
    public ActionResponse getSimpleSoftwarePageByAssetId(@ApiParam(value = "assetId") AssetSoftwareRelationQuery queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getAssetId(), "资产ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.getSimpleSoftwarePageByAssetId(queryCondition));
    }

}
