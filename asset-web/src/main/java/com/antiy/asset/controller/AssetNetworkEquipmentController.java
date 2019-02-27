package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
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
@Api(value = "AssetNetworkEquipment", description = "网络设备详情表")
@RestController
@RequestMapping("/api/v1/asset/networkequipment")
public class AssetNetworkEquipmentController {

    @Resource
    public IAssetNetworkEquipmentService iAssetNetworkEquipmentService;

    /**
     * 保存
     *
     * @param assetNetworkEquipment
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:networkequipment:saveSingle')")
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetNetworkEquipment") AssetNetworkEquipmentRequest assetNetworkEquipment) throws Exception {
        iAssetNetworkEquipmentService.saveAssetNetworkEquipment(assetNetworkEquipment);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetNetworkEquipment
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:networkequipment:updateSingle')")
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetNetworkEquipment") AssetNetworkEquipmentRequest assetNetworkEquipment) throws Exception {
        iAssetNetworkEquipmentService.updateAssetNetworkEquipment(assetNetworkEquipment);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetNetworkEquipment
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:networkequipment:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetNetworkEquipment") AssetNetworkEquipmentQuery assetNetworkEquipment) throws Exception {
        return ActionResponse
            .success(iAssetNetworkEquipmentService.findPageAssetNetworkEquipment(assetNetworkEquipment));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:networkequipment:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetNetworkEquipment") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetNetworkEquipmentService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:networkequipment:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isNull(baseRequest.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetNetworkEquipmentService.deleteById(baseRequest.getStringId()));
    }
}
