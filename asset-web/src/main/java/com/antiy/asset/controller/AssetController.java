package com.antiy.asset.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.AssetEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Asset", description = "资产主表")
@RestController
@RequestMapping("/v1/asset")
public class AssetController {

    @Resource
    public IAssetService iAssetService;

    // /**
    // *保存PC接口
    // * @param assetPCRequest
    // * @return actionResponse
    // * @throws Exception
    // */
    // @ApiOperation(value = "保存全部数据总接口", notes = "传入Map对象信息")
    // @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class,
    // responseContainer = "actionResponse"),})
    // @RequestMapping(value = "/save/pc", method = RequestMethod.POST)
    // public ActionResponse saveAssetPC(@RequestBody @ApiParam(value = "assetPc")AssetPCRequest assetPCRequest) throws
    // Exception {
    // iAssetService.saveAssetPC(assetPCRequest);
    // return ActionResponse.success();
    // }

    /**
     * 保存
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "asset") AssetRequest asset) throws Exception {
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
     * 通过ID查询
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "asset") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetService.getByAssetId(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "query") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetService.deleteById(id));
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") AssetQuery assetQuery, ServletResponse response) throws Exception {
        List<AssetResponse> list = iAssetService.findListAsset(assetQuery);
        List entities = BeanConvert.convert(list, AssetEntity.class);
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xlsx", "硬件信息", entities);
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void exportTemplate(@ApiParam(value = "query") AssetQuery assetQuery,
                               ServletResponse response) throws Exception {
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xlsx", "硬件信息", null);
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
        ImportResult importResult = ExcelUtils.importExcelFromClient(AssetEntity.class, multipartFile, 1, 0);
        List<?> list = importResult.getDataList();
        List<Object> asset = BeanConvert.convert(list, Asset.class);
        Integer successNum = iAssetService.batchSave(transferList(asset));
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
    @RequestMapping(value = "/update/changeStatus", method = RequestMethod.POST)
    public ActionResponse changeStatus(@ApiParam(value = "资产ID数组") @RequestParam Integer[] ids,
                                       @ApiParam(value = "资产新状态") @RequestParam("targetStatus") Integer targetStatus) throws Exception {
        iAssetService.changeStatus(ids, targetStatus);
        return ActionResponse.success();
    }

    /**
     * 查询厂商信息
     * @author zhangyajun
     *
     * @return 厂商名称集合
     */
    @ApiOperation(value = "查询厂商接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/manufacturer", method = RequestMethod.GET)
    public ActionResponse queryManufacturer() throws Exception {
        return ActionResponse.success(iAssetService.findManufacturer());
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
    public ActionResponse queryAssetByIds(@ApiParam(value = "资产ID数组") @RequestParam("ids") Integer[] ids) throws Exception {
        return ActionResponse.success(iAssetService.queryAssetByIds(ids));
    }

    /**
     * 硬件资产按品类型号统计
     *
     * @return 品类型号名和该品类型号资产数量的映射
     */
    @ApiOperation(value = "硬件资产按类型统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/type", method = RequestMethod.GET)
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
     * @author zhangyajun
     *
     * @return 厂商名和该厂商资产数量的映射
     */
    @ApiOperation(value = "硬件资产按厂商统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.GET)
    public ActionResponse countAssetByManufacturer() throws Exception {
        return ActionResponse.success(iAssetService.countManufacturer());
    }
}
