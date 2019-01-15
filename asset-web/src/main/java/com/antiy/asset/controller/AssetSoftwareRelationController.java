package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.util.DataTypeUtils;
import com.antiy.common.encoder.Encode;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "AssetSoftwareRelation", description = "资产软件关系信息")
@RestController
@RequestMapping("/api/v1/asset/assetsoftwarerelation")
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
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
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
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
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
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationQuery assetSoftwareRelationQuery) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareRelationService.findPageAssetSoftwareRelation(assetSoftwareRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetSoftwareRelation") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.deleteById(id));
    }

    /**
     * 通过软件ID统计资产数量
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过软件ID统计资产数量", notes = "软件ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/countAssetBySoftId/{id}", method = RequestMethod.GET)
    public ActionResponse countAssetBySoftId(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.countAssetBySoftId(id));
    }

    /**
     * 查询硬件资产关联的软件列表
     *
     * @param assetId
     * @return actionResponse
     */
    @ApiOperation(value = "查询硬件资产关联的软件列表", notes = "资产ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/getSoftwareByAssetId/{assetId}", method = RequestMethod.GET)
    public ActionResponse getSoftwareByAssetId(@ApiParam(value = "assetId") @PathVariable("assetId") @Encode String assetId) throws Exception {
        ParamterExceptionUtils.isNull(assetId, "资产ID不能为空");
        return ActionResponse
            .success(iAssetSoftwareRelationService.getSoftByAssetId(DataTypeUtils.stringToInteger(assetId)));
    }

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return 操作系统名称集合
     */
    @ApiOperation(value = "查询操作系统接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/os", method = RequestMethod.GET)
    public ActionResponse<List<String>> queryOS() throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.findOS());
    }
}
