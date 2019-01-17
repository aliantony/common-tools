package com.antiy.asset.controller;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetSoftwareDetailResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "软件信息表")
@RestController
@RequestMapping("/api/v1/asset/assetsoftware")
public class AssetSoftwareController {

    @Resource
    public IAssetSoftwareService iAssetSoftwareService;

    /**
     * 保存
     *
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "assetSoftware") AssetSoftwareRequest assetSoftware) throws Exception {
        iAssetSoftwareService.saveAssetSoftware(assetSoftware);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftware") AssetSoftwareRequest assetSoftware) throws Exception {
        ParamterExceptionUtils.isBlank(assetSoftware.getId(), "软件Id不能为空");
        iAssetSoftwareService.updateAssetSoftware(assetSoftware);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") AssetSoftwareQuery assetSoftware) throws Exception {
        return ActionResponse.success(iAssetSoftwareService.findPageAssetSoftware(assetSoftware));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetSoftware") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.deleteById(id));
    }

    /**
     * 导出模板文件
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板文件", notes = "主键封装对象")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") AssetSoftwareQuery query, HttpServletResponse response) throws Exception {
        iAssetSoftwareService.downloadSoftware(query,response);
    }

    /**
     * 导出模板文件
     *
     * @param assetSoftwareQuery 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板文件", notes = "主键封装对象")
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    public void exportFile(@ApiParam(value = "assetSoftwareQuery") AssetSoftwareQuery assetSoftwareQuery) throws Exception {
        List<AssetSoftwareResponse> assetSoftwareResponses = iAssetSoftwareService
            .findListAssetSoftware(assetSoftwareQuery);
        List list = BeanConvert.convert(assetSoftwareResponses, AssetSoftwareEntity.class);
        ExcelUtils.exportToClient(AssetSoftwareEntity.class, "软件信息表.xlsx", "软件信息", list);
    }

    /**
     * 导入软件excel文件
     *
     * @param multipartFile 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导入excel文件", notes = "主键封装对象")
    @RequestMapping(value = "/import/file", method = RequestMethod.POST)
    public ActionResponse exportFile(@ApiParam(value = "multipartFile") MultipartFile multipartFile) throws Exception {
        ImportResult<AssetSoftwareEntity> importResult = ExcelUtils.importExcelFromClient(AssetSoftwareEntity.class, multipartFile, 1, 0);
        List<AssetSoftwareEntity> list = importResult.getDataList();
         List<AssetSoftware> assetSoftwares = BeanConvert.convert(list, AssetSoftware.class);
        Integer successNum = iAssetSoftwareService.batchSave(assetSoftwares);
        return ActionResponse.success(successNum);

    }

    private List<AssetSoftware> transferList(List<Object> objects) {
        List<AssetSoftware> assetSoftwares = new ArrayList<>();
        objects.forEach(x -> assetSoftwares.add((AssetSoftware) x));
        return assetSoftwares;
    }

//    /**
//     * 导入excel文件
//     *
//     * @param multipartFile 主键封装对象
//     * @return actionResponse
//     */
//    @ApiOperation(value = "导入excel文件", notes = "主键封装对象")
//    @RequestMapping(value = "/import/excel", method = RequestMethod.POST)
//    public ActionResponse importExcel(@ApiParam(value = "multipartFile") MultipartFile multipartFile) throws Exception {
//
//        return ActionResponse.success();
//    }

    /**
     * 软件资产按二级品类型号统计
     *
     * @return 品类型号名和该品类信号型产数量的映射
     */
    @ApiOperation(value = "软件资产按二级品类型号统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/category", method = RequestMethod.GET)
    public Map<String, Long> countAssetByCategory() throws Exception {
        return iAssetSoftwareService.countCategory();
    }

    /**
     * 硬件资产按状态统计
     *
     * @return 状态名和该状态下资产数量的映射
     */
    @ApiOperation(value = "软件资产按状态统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/status", method = RequestMethod.GET)
    public Map<String, Long> countAssetByStatus() throws Exception {
        return iAssetSoftwareService.countStatus();
    }

    /**
     * 硬件资产按厂商统计
     *
     * @return 厂商名和该厂商资产数量的映射
     */
    @ApiOperation(value = "软件资产按厂商统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.GET)
    public Map<String, Long> countAssetByManufacturer() throws Exception {
        return iAssetSoftwareService.countManufacturer();
    }

    /**
     * 软件资产厂商查询
     * @param manufacturerName
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "软件资产厂商查询", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/manufacturer", method = RequestMethod.GET)
    public ActionResponse<List<String>> queryAssetByManufacturer(@ApiParam(value = "manufacturerName") String manufacturerName) throws Exception {
        return ActionResponse.success(iAssetSoftwareService.getManufacturerName(manufacturerName));
    }

    /**
     * 软件详情查询
     * @param softwareQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "软件详情查询", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/detail", method = RequestMethod.GET)
    public ActionResponse<AssetSoftwareDetailResponse> querySoftwareDetail(@ApiParam(value = "softwareQuery") SoftwareQuery softwareQuery) throws Exception {
        ParamterExceptionUtils.isBlank(softwareQuery.getPrimaryKey(), "软件资产Id不能为空");
        return ActionResponse.success(iAssetSoftwareService.querySoftWareDetail(softwareQuery));
    }
}
