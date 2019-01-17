package com.antiy.asset.controller;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.AssetEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetPCRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Asset", description = "资产主表")
@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {

    @Resource
    public IAssetService iAssetService;

    /**
     * 保存PC接口
     * @param assetPCRequest
     * @return actionResponse
     * @throws Exception
     */
    @ApiOperation(value = "保存全部数据总接口", notes = "传入json信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/pc", method = RequestMethod.POST)
    public ActionResponse saveAssetPC(@RequestBody @ApiParam(value = "assetPc") AssetPCRequest assetPCRequest)
                                                                                                              throws Exception {
        iAssetService.saveAssetPC(assetPCRequest);
        return ActionResponse.success();
    }

    /**
     * 保存
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "asset") AssetRequest asset)
                                                                                                                  throws Exception {
        iAssetService.saveAsset(asset);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody(required = false) @ApiParam(value = "asset") AssetRequest asset) throws Exception {
        iAssetService.updateAsset(asset);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(iAssetService.findPageAsset(asset));
    }

    /**
     * 通过ID查询资产详情
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "asset") @PathVariable("id") @Encode String id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetService.getByAssetId(id));
    }

    /**
     * 资产变更
     * @author lvliang
     * @param assetOuterRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产变更", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/change/asset", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody(required = false) AssetOuterRequest assetOuterRequest)
                                                                                                          throws Exception {
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息b不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
        iAssetService.changeAsset(assetOuterRequest);
        return ActionResponse.success();
    }

    /**
     * 通过ID删除
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "query") @PathVariable("id") @Encode String id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetService.deleteById(id));
    }

    /**
     * 导出资产信息
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") AssetQuery assetQuery) throws Exception {
        List<AssetResponse> list = iAssetService.findListAsset(assetQuery);
        List entities = BeanConvert.convert(list, AssetEntity.class);
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xlsx", "硬件信息", entities);
    }

    /**
     * 导出模板
     *
     * @param type 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void exportTemplate(@ApiParam("导出的模板类型") @Min(value = 1, message = "软件类型只能为1，2，3，4") @Max(value = 5, message = "软件类型只能为1，2，3，4，5") Integer type)
                                                                                                                                                           throws Exception {
        ParamterExceptionUtils.isNull(type, "类型不能为空");
        iAssetService.exportTemplate(type);
    }

    /**
     * 导入文件
     *
     * @param multipartFile
     * @return actionResponse
     */
    @ApiOperation(value = "导入文件", notes = "主键封装对象")
    @RequestMapping(value = "/import/file", method = RequestMethod.POST)
    public ActionResponse exportFile(@ApiParam(value = "multipartFile") MultipartFile multipartFile) throws Exception {
        ImportResult<Asset> importResult = ExcelUtils.importExcelFromClient(AssetEntity.class, multipartFile, 1, 0);
        List<Asset> list = importResult.getDataList();
        // List<Object> asset = BeanConvert.convert(list, Asset.class);
        Integer successNum = iAssetService.batchSave(list);
        return ActionResponse.success(successNum);
    }

    private List<Asset> transferList(List<Object> objects) {
        List<Asset> assets = new ArrayList<>();
        objects.forEach(x -> assets.add((Asset) x));
        return assets;
    }

    /**
     * 批量修改资产状态
     *
     * @param ids
     * @param targetStatus
     * @return actionResponse
     */
    @ApiOperation(value = "批量修改资产状态接口", notes = "传入资产状态和资产ID数组")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/changeStatus/batch", method = RequestMethod.POST)
    public ActionResponse changeStatus(@ApiParam(value = "资产ID数组") @RequestParam @Encode String[] ids,
                                       @ApiParam(value = "资产新状态") @RequestParam("targetStatus") Integer targetStatus)
                                                                                                                     throws Exception {
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
    @ApiOperation(value = "通过资产ID修改资产状态", notes = "传入资产ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/changeStatusById", method = RequestMethod.POST)
    public ActionResponse changeStatusById(@ApiParam(value = "资产ID") @RequestParam String id,
                                           @ApiParam(value = "资产新状态") @RequestParam("targetStatus") Integer targetStatus)
                                                                                                                         throws Exception {
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
    @ApiOperation(value = "通过ID数组查询资产列表", notes = "传入资产ID数组")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{ids}", method = RequestMethod.POST)
    public ActionResponse queryAssetByIds(@ApiParam(value = "资产ID数组") @RequestParam("ids") Integer[] ids)
                                                                                                         throws Exception {
        return ActionResponse.success(iAssetService.queryAssetByIds(ids));
    }

    /**
     * 硬件资产按品类型号统计
     *
     * @return 品类型号名和该品类型号资产数量的映射
     */
    @ApiOperation(value = "硬件资产按二级品类型号统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/category", method = RequestMethod.GET)
    public Map<String, Long> countAssetByCategory() throws Exception {
        return iAssetService.countCategory();
    }

    /**
     * 硬件资产按状态统计
     *
     * @return 状态名和该状态下资产数量的映射
     */
    @ApiOperation(value = "硬件资产按状态统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/status", method = RequestMethod.GET)
    public Map<String, Long> countAssetByStatus() throws Exception {
        return iAssetService.countStatus();
    }

    /**
     * 硬件资产-按厂商统计
     *
     * @return 厂商名和该厂商资产数量的映射
     */
    @ApiOperation(value = "硬件资产按厂商统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.GET)
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
    @RequestMapping(value = "/import/pc", method = RequestMethod.POST)
    public ActionResponse<String> importPc(@ApiParam(value = "file") MultipartFile file) throws Exception {

        return ActionResponse.success(iAssetService.importPc(file));
    }

    /**
     * 硬件资产-导入网络设备
     *
     * @return
     */
    @ApiOperation(value = "导入网络设备", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/net", method = RequestMethod.POST)
    public ActionResponse importNet(@ApiParam(value = "file") MultipartFile file) throws Exception {


        return ActionResponse.success(iAssetService.importNet(file));
    }




}
