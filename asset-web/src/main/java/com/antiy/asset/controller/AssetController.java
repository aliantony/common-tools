package com.antiy.asset.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.AssetCountColumnarResponse;
import com.antiy.asset.vo.response.AssetCountResponse;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Asset", description = "资产主表")
@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {

    @Resource
    public IAssetService   iAssetService;
    @Resource
    private ActivityClient activityClient;

    /**
     * 保存
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "登记资产", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:saveSingle')")
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "asset") AssetOuterRequest asset) throws Exception {
        return iAssetService.saveAsset(asset);
    }

    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryList')")
    public ActionResponse queryList(@ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(iAssetService.findPageAsset(asset));
    }

    @ApiOperation(value = "通过区域Id查询当前区域是否存在资产", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAssetCountByAreaIds", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryAssetCountByAreaIds')")
    public ActionResponse queryAssetCountByAreaIds(@ApiParam(value = "areaIds") ArrayList<Integer> areaIds) throws Exception {
        return ActionResponse.success(iAssetService.queryAssetCountByAreaIds(areaIds));
    }

    /**
     * 通过ID查询资产详情
     *
     * @param asset 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryById')")
    public ActionResponse queryById(@ApiParam(value = "asset") AssetDetialCondition asset) throws Exception {
        ParamterExceptionUtils.isNull(asset, "资产不能为空");
        ParamterExceptionUtils.isNull(asset.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.getByAssetId(asset));
    }

    /**
     * 资产变更
     * @author lvliang
     * @param assetOuterRequest 资产信息
     * @return actionResponse
     */
    @ApiOperation(value = "资产变更", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/change/asset", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:changeAsset')")
    public ActionResponse updateSingle(@RequestBody(required = false) AssetOuterRequest assetOuterRequest) throws Exception {
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
        iAssetService.changeAsset(assetOuterRequest);
        return ActionResponse.success();
    }

    /**
     * 通过ID删除
     *
     * @param request 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:deleteById')")
    public ActionResponse deleteById(@ApiParam(value = "queryCondition") @RequestBody BaseRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetService.deleteById(request.getStringId()));
    }

    /**
     * 导出资产信息
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象")
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:export')")
    public void export(@ApiParam(value = "query") AssetQuery assetQuery,
                       HttpServletResponse response) throws Exception {
        iAssetService.exportData(assetQuery, response);
    }

    /**
     * 导出模板
     *
     * @param type 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板", notes = "主键封装对象")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:exportTemplate')")
    public void exportTemplate(@ApiParam("导出的模板类型") Integer[] type) throws Exception {
        ParamterExceptionUtils.isNull(type, "类型不能为空");
        iAssetService.exportTemplate(type);
    }

    /**
     * 批量修改资产状态
     *
     * @param ids
     * @param targetStatus
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量修改资产状态接口", notes = "传入资产状态和资产ID数组")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/changeStatus/batch", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:changeStatus')")
    public ActionResponse changeStatus(@ApiParam(value = "资产ID数组") @RequestParam @Encode String[] ids,
                                       @ApiParam(value = "资产新状态") @RequestParam("targetStatus") Integer targetStatus) throws Exception {
        iAssetService.changeStatus(ids, targetStatus);
        return ActionResponse.success();
    }

    /**
     * 通过资产ID修改资产状态
     *
     * @param id
     * @param targetStatus
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过资产ID修改资产状态", notes = "传入资产ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/changeStatusById", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:changeStatusById')")
    public ActionResponse changeStatusById(@ApiParam(value = "资产ID") @RequestParam String id,
                                           @ApiParam(value = "资产新状态") @RequestParam("targetStatus") Integer targetStatus) throws Exception {
        iAssetService.changeStatusById(id, targetStatus);
        return ActionResponse.success();
    }

    /**
     * 查询下拉的厂商信息
     * @author zhangyajun
     *
     * @return 厂商名称集合
     */
    @ApiOperation(value = "查询厂商接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/pulldown/manufacturer", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:pulldownManufacturer')")
    public ActionResponse<List<String>> pulldownManufacturer() throws Exception {
        return ActionResponse.success(iAssetService.pulldownManufacturer());
    }

    /**
     * 通过ID数组查询资产列表
     * @author zhangyajun
     *
     * @param ids
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)通过ID数组查询资产列表", notes = "传入资产ID数组")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{ids}", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:queryAssetByIds')")
    public ActionResponse queryAssetByIds(@ApiParam(value = "资产ID数组") @RequestParam("ids") @Encode String[] ids) throws Exception {
        Integer[] id = DataTypeUtils.stringArrayToIntegerArray(ids);
        return ActionResponse.success(iAssetService.queryAssetByIds(id));
    }

    /**
     * 硬件资产按品类型号统计
     *
     * @return 品类型号名和该品类型号资产数量的映射
     */
    @ApiOperation(value = "硬件资产按二级品类型号统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountResponse.class, responseContainer = "assetCountResponse"), })
    @RequestMapping(value = "/count/category", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:countAssetByCategory')")
    public ActionResponse countAssetByCategory() throws Exception {
        return ActionResponse.success(iAssetService.countCategory());
    }

    /**
     * 硬件资产按状态统计
     *
     * @return 状态名和该状态下资产数量的映射
     */
    @ApiOperation(value = "硬件资产按状态统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountColumnarResponse.class, responseContainer = "assetCountResponse"), })
    @RequestMapping(value = "/count/status", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:countAssetByStatus')")
    public ActionResponse countAssetByStatus() throws Exception {
        return ActionResponse.success(iAssetService.countStatus());
    }

    /**
     * 硬件资产-按厂商统计
     *
     * @return 厂商名和该厂商资产数量的映射
     */
    @ApiOperation(value = "硬件资产按厂商统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountResponse.class, responseContainer = "assetCountResponse"), })
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:countAssetByManufacturer')")
    public ActionResponse countAssetByManufacturer() throws Exception {
        return ActionResponse.success(iAssetService.countManufacturer());
    }

    /**
     * 硬件资产-导入计算设备
     *
     * @return
     */
    @ApiOperation(value = "导入计算设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/computer", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:importPc')")
    public ActionResponse importPc(@ApiParam(value = "file") MultipartFile file,
                                   AssetImportRequest importRequest) throws Exception {
        return ActionResponse.success(iAssetService.importPc(file, importRequest));
    }

    /**
     * 硬件资产-导入网络设备
     *
     * @return
     */
    @ApiOperation(value = "导入网络设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/net", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:importNet')")
    public ActionResponse importNet(@ApiParam(value = "file") MultipartFile file,
                                    AssetImportRequest importRequest) throws Exception {

        return ActionResponse.success(iAssetService.importNet(file, importRequest));
    }

    /**
     * 硬件资产-导入安全设备
     *
     * @return
     */
    @ApiOperation(value = "导入安全设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/safety", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:importSafety')")
    public ActionResponse importSafety(@ApiParam(value = "file") MultipartFile file,
                                       AssetImportRequest importRequest) throws Exception {
        return ActionResponse.success(iAssetService.importSecurity(file, importRequest));
    }

    /**
     * 硬件资产-导入存储设备
     *
     * @return
     */
    @ApiOperation(value = "导入存储设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/storage", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:importStorage')")
    public ActionResponse importStorage(@ApiParam(value = "file") MultipartFile file,
                                        AssetImportRequest importRequest) throws Exception {

        return ActionResponse.success(iAssetService.importStory(file, importRequest));
    }

    /**
     * 硬件资产-导入其他设备
     *
     * @return
     */
    @ApiOperation(value = "导入其他设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/ohters", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:importOhters')")
    public ActionResponse importOhters(@ApiParam(value = "file") MultipartFile file,
                                       AssetImportRequest importRequest) throws Exception {

        return ActionResponse.success(iAssetService.importOhters(file, importRequest));
    }

    @ApiOperation(value = "(无效)启动流程", notes = "启动流程")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/start/process", method = RequestMethod.POST)
    public ActionResponse manualStartProcess(@ApiParam @RequestBody ManualStartActivityRequest manualStartActivityRequest) throws Exception {
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
        activityClient.manualStartProcess(manualStartActivityRequest);
        return ActionResponse.success();
    }

    @ApiOperation(value = "(无效)处理流程", notes = "处理流程")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/deal/process", method = RequestMethod.POST)
    public ActionResponse completeTask(@ApiParam @RequestBody ActivityHandleRequest activityHandleRequest) throws Exception {
        activityClient.completeTask(activityHandleRequest);
        return ActionResponse.success();
    }
}
