package com.antiy.asset.controller;

import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.query.AssetBaselinTemplateQuery;
import com.antiy.asset.vo.query.AssetMultipleQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetCountByAreaIdsRequest;
import com.antiy.asset.vo.request.AssetIpRequest;
import com.antiy.asset.vo.request.AssetLockRequest;
import com.antiy.asset.vo.request.AssetMatchRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.ExportTemplateRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.request.NumberMac;
import com.antiy.asset.vo.request.ProcessTemplateRequest;
import com.antiy.asset.vo.request.UnconnectedManufacturerRequest;
import com.antiy.asset.vo.response.AssetCountColumnarResponse;
import com.antiy.asset.vo.response.AssetCountResponse;
import com.antiy.asset.vo.response.AssetMatchResponse;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "asset") AssetOuterRequest asset) throws Exception {
        return iAssetService.saveAsset(asset);
    }

    /**
     * 根据资产ID 得把模板实体
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "根据资产ID得把模板实体", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetsTemplate", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:saveSingle')")
    public ActionResponse assetsTemplate(@RequestBody(required = false) @ApiParam(value = "asset") ProcessTemplateRequest asset) throws Exception {
        return ActionResponse.success(iAssetService.assetsTemplate(asset));
    }

    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "asset") AssetQuery asset) throws Exception {
        if (StringUtils.isNotBlank(asset.getSortName()) && StringUtils.isNotBlank(asset.getSortOrder())) {
            if (asset.getSortName().equals("gmtCreate")) {
                asset.setSortName("gmt_create");
            }
        }
        return ActionResponse.success(iAssetService.findPageAsset(asset));
    }

    /**
     * 通联设置资产查询接口
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "通联设置资产查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/unconnectedList", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryList')")
    public ActionResponse findUnconnectedAsset(@RequestBody @ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(iAssetService.findUnconnectedAsset(asset));
    }

    @ApiOperation(value = "通过区域Id查询当前区域是否存在资产", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAssetCountByAreaIds", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryAssetCountByAreaIds')")
    public ActionResponse queryAssetCountByAreaIds(@RequestBody @ApiParam(value = "areaIds") AssetCountByAreaIdsRequest areaIds) {
        return ActionResponse.success(iAssetService.queryAssetCountByAreaIds(areaIds.getAreaIds()));
    }

    /**
     * 通过ID查询资产详情
     *
     * @param condition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "asset") QueryCondition condition) throws Exception {
        ParamterExceptionUtils.isNull(condition, "资产不能为空");
        ParamterExceptionUtils.isNull(condition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.getByAssetId(condition));
    }

    /**
     * 通过ID查询资产详情
     *
     * @param condition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/get/assemblyInfo", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryById')")
    public ActionResponse getAssemblyInfo(@RequestBody @ApiParam(value = "asset") QueryCondition condition) {
        ParamterExceptionUtils.isNull(condition, "资产不能为空");
        ParamterExceptionUtils.isNull(condition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.getAssemblyInfo(condition));
    }

    /**
     * 资产变更
     *
     * @param assetOuterRequest 资产信息
     * @return actionResponse
     * @author lvliang
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

    @ApiOperation(value = "查询资产状态", notes = "")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "query/assetStatus", method = RequestMethod.POST)
    public ActionResponse getAssetStatus(@RequestBody AssetLockRequest assetLockRequest) throws Exception {
        return iAssetService.dealAssetOperation(assetLockRequest);
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
    public void export(@ApiParam(value = "query") AssetQuery assetQuery, HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        iAssetService.exportData(assetQuery, response, request);

    }

    /**
     * 导出模板
     *
     * @param request 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板", notes = "主键封装对象")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:exportTemplate')")
    public void exportTemplate(@ApiParam("导出的模板类型") ExportTemplateRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getType(), "类型不能为空");
        iAssetService.exportTemplate(request.getType());
    }

    // /**
    // * 下载流程相关模板
    // *
    // * @return actionResponse
    // */
    // @ApiOperation(value = "下载流程相关模板", notes = "主键封装对象")
    // @RequestMapping(value = "/export/implementation/file", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:implementationFile')")
    // public void implementationFile(ProcessTemplateRequest baseRequest) throws Exception {
    // ParamterExceptionUtils.isEmpty(baseRequest.getIds(), "资产id不能为空");
    // iAssetService.implementationFile(baseRequest);
    //
    // }

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
     * 判断mac是否重复
     *
     * @return actionResponse
     */
    @ApiOperation(value = "判断mac是否重复,true重复", notes = "传入资产mac")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/CheckRepeatMAC", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:checkRepeatMAC')")
    public ActionResponse checkRepeatMAC(@RequestBody NumberMac numberMac) throws Exception {
        return ActionResponse.success(iAssetService.checkRepeatMAC(numberMac.getMac(), numberMac.getId()));
    }

    /**
     * 判断编号是否重复
     *
     * @return actionResponse
     */
    @ApiOperation(value = "判断编号是否重复，true重复", notes = "传入资产编号")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/CheckRepeatNumber", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:checkRepeatNumber')")
    public ActionResponse checkRepeatNumber(@RequestBody NumberMac numberMac) throws Exception {
        return ActionResponse.success(iAssetService.checkRepeatNumber(numberMac.getNumber(), numberMac.getId()));
    }

    /**
     * 查询通联设置厂商下拉接口
     *
     * @return 厂商名称集合
     * @author zhangyajun
     */
    @ApiOperation(value = "查询通联设置厂商下拉接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/pulldown/unconnectedManufacturer", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:pulldownManufacturer')")
    public ActionResponse<Set<String>> pulldownUnconnectedManufacturer(@RequestBody UnconnectedManufacturerRequest request) throws Exception {
        return ActionResponse
            .success(iAssetService.pulldownUnconnectedManufacturer(request.getIsNet(), request.getPrimaryKey()));
    }

    // /**
    // * 通过ID数组查询资产列表
    // * @author zhangyajun
    // *
    // * @param ids
    // * @return actionResponse
    // */
    // @ApiOperation(value = "(无效)通过ID数组查询资产列表", notes = "传入资产ID数组")
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class,
    // responseContainer = "actionResponse"), })
    // @RequestMapping(value = "/query/{ids}", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryAssetByIds')")
    // public ActionResponse queryAssetByIds(@ApiParam(value = "资产ID数组") @RequestParam("idfs") @Encode String[] ids)
    // throws Exception {
    // Integer[] id = DataTypeUtils.stringArrayToIntegerArray(ids);
    // return ActionResponse.success(iAssetService.queryAssetByIds(id));
    // }

    /**
     * 硬件资产按品类型号统计
     *
     * @return 品类型号名和该品类型号资产数量的映射
     */
    @ApiOperation(value = "资产品类型号统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountResponse.class, responseContainer = "assetCountResponse"), })
    @RequestMapping(value = "/count/category", method = RequestMethod.POST)
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
    @RequestMapping(value = "/count/status", method = RequestMethod.POST)
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
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.POST)
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
    public ActionResponse importPc(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 10) {

            throw new BusinessException("导入失败，文件不超过10M！");
        }
        return ActionResponse.success(iAssetService.importPc(file, null));
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
    public ActionResponse importNet(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 10) {

            throw new BusinessException("导入失败，文件不超过10M！");
        }
        return ActionResponse.success(iAssetService.importNet(file, null));
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
    public ActionResponse importSafety(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 10) {

            throw new BusinessException("导入失败，文件不超过10M！");
        }

        return ActionResponse.success(iAssetService.importSecurity(file, null));
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
    public ActionResponse importStorage(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 10) {

            throw new BusinessException("导入失败，文件不超过10M！");
        }
        return ActionResponse.success(iAssetService.importStory(file, null));
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
    public ActionResponse importOhters(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 10) {

            throw new BusinessException("导入失败，文件不超过10M！");
        }
        return ActionResponse.success(iAssetService.importOhters(file, null));
    }

    @ApiOperation(value = "(无效)启动流程", notes = "启动流程")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/start/process", method = RequestMethod.POST)
    public ActionResponse manualStartProcess(@ApiParam @RequestBody ManualStartActivityRequest manualStartActivityRequest) {
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
        activityClient.manualStartProcess(manualStartActivityRequest);
        return ActionResponse.success();
    }

    @ApiOperation(value = "(无效)处理流程", notes = "处理流程")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/deal/process", method = RequestMethod.POST)
    public ActionResponse completeTask(@ApiParam @RequestBody ActivityHandleRequest activityHandleRequest) {
        activityClient.completeTask(activityHandleRequest);
        return ActionResponse.success();
    }

    @ApiOperation(value = "获取资产id列表", notes = "获取资产id列表")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetIds", method = RequestMethod.POST)
    public ActionResponse findAssetIds() {
        return ActionResponse.success(iAssetService.findAssetIds());
    }

    @ApiOperation(value = "工作台硬件登记数量", notes = "获取资产id列表")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/waitRegistCount", method = RequestMethod.POST)
    public ActionResponse queryWaitRegistCount() {
        return ActionResponse.success(iAssetService.queryWaitRegistCount());
    }

    @ApiOperation(value = "正常资产数量统计", notes = "正常资产数量统计")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/normal/count", method = RequestMethod.POST)
    public ActionResponse queryNormalCount() {
        return ActionResponse.success(iAssetService.queryNormalCount());
    }

    /* @ApiOperation(value = "告警资产数量统计", notes = "正常资产数量统计")
     * @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class,
     * responseContainer = "actionResponse"), })
     * @RequestMapping(value = "/alarm/count", method = RequestMethod.POST) public ActionResponse findAlarmAssetCount()
     * { return ActionResponse.success(iAssetService.findAlarmAssetCount()); } */

    /**
     * 资产列表查询-基准模板下拉项信息
     *
     * @return 资产组名称集合
     * @author zhangyajun
     */
    @ApiOperation(value = "资产列表查询-基准模板下拉项信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/baselineTemplate", method = RequestMethod.POST)
    public ActionResponse<List<SelectResponse>> queryBaselineTemplate() {
        return ActionResponse.success(iAssetService.queryBaselineTemplate());
    }

    /**
     * 异常统计
     */
    @ApiOperation(value = "异常统计", notes = "异常统计")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/vul/count", method = RequestMethod.POST)
    public ActionResponse<Integer> countVul(@RequestBody @ApiParam(value = "asset") AssetQuery assetQuery) throws Exception {
        Integer count = iAssetService.countUnusual(assetQuery);
        return ActionResponse.success(count);
    }

    @ApiOperation(value = "更新资产基准模板", notes = "更新资产基准模板")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/baselineTemplate", method = RequestMethod.POST)
    public ActionResponse<Integer> updateAssetBaselineTemplate(@RequestBody @ApiParam(value = "asset") AssetBaselinTemplateQuery query) throws Exception {
        Integer count = iAssetService.updateAssetBaselineTemplate(query);
        return ActionResponse.success(count);
    }

    @ApiOperation(value = "可用的批量操作", notes = "根据资产当前状态获取可用的下一步批量操作", response = ActionResponse.class)
    @RequestMapping(value = "/query/assetStatusBatch", method = RequestMethod.POST)
    public ActionResponse queryAssetStatus(@RequestParam @NotNull(message = "资产状态不能为空") Integer assetStatus) {
        return ActionResponse.success(iAssetService.findAvailableAssetStatus(assetStatus));
    }

    @ApiOperation(value = "根据ip和mac查询资产名称编号", notes = "提供给日志告警", response = AssetMatchResponse.class, responseContainer = "List")
    @RequestMapping(value = "/query/assetInfo", method = RequestMethod.POST)
    public ActionResponse queryAssetInfo(@RequestBody AssetMatchRequest request) {
        return ActionResponse.success(iAssetService.queryAssetInfo(request));
    }

    /**
     * 资产列表综合查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "资产列表综合查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetlist", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:queryList')")
    public ActionResponse queryAssetList(@RequestBody @ApiParam(value = "asset") AssetMultipleQuery asset) throws Exception {
        if (StringUtils.isNotBlank(asset.getSortName()) && StringUtils.isNotBlank(asset.getSortOrder())) {
            if (asset.getSortName().equals("gmtCreate")) {
                asset.setSortName("gmt_create");
            }
        }
        return ActionResponse.success(iAssetService.queryAssetPage(asset));
    }

    @ApiOperation(value = "资产探测使用-查询该区域资产已使用的ip", notes = "查询该区域资产已使用的ip")
    @RequestMapping(value = "/queryIpByAreaId", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryIpByAreaId(@RequestBody AssetIpRequest request) {
        return ActionResponse.success(iAssetService.queryIpByAreaId(request));
    }

    @ApiOperation(value = "资产机器名推荐", notes = "推荐机器名")
    @RequestMapping(value = "/machineName/recommend", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse recommendName( ) {
        return ActionResponse.success(iAssetService.recommendName());
    }
    @ApiOperation(value = "资产列表查询-厂商下拉查询", notes = "")
    @RequestMapping(value = "/query/manufacturer", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryManufacturer() {
        return ActionResponse.success(iAssetService.queryManufacturer());
    }

    @ApiOperation(value = "资产列表查询-名称下拉查询", notes = "")
    @RequestMapping(value = "/query/name", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryName() {
        return ActionResponse.success(iAssetService.queryName());
    }

    @ApiOperation(value = "资产列表查询-版本下拉查询", notes = "")
    @RequestMapping(value = "/query/version", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryVersion() {
        return ActionResponse.success(iAssetService.queryVersion());
    }

    @ApiOperation(value = "资产列表查询-资产组下拉查询", notes = "")
    @RequestMapping(value = "/query/group", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryAssetGroup() {
        return ActionResponse.success(iAssetService.queryAssetGroup());
    }

    @ApiOperation(value = "资产列表查询-使用者下拉查询", notes = "")
    @RequestMapping(value = "/query/user", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryUser() {
        return ActionResponse.success(iAssetService.queryUser());
    }
    @ApiOperation(value = "资产列表查询-所属组织下拉查询", notes = "")
    @RequestMapping(value = "/query/department", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryDepartment() {
        return ActionResponse.success(iAssetService.queryDepartment());
    }
    @ApiOperation(value = "资产列表查询-基准模板下拉查询", notes = "")
    @RequestMapping(value = "/query/template", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryTemplate() {
        return ActionResponse.success(iAssetService.queryTemplate());
    }
    @ApiOperation(value = "资产列表查询-从属业务下拉查询", notes = "")
    @RequestMapping(value = "/query/business", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    public ActionResponse queryBusiness() {
        return ActionResponse.success(iAssetService.queryBusiness());
    }
}
