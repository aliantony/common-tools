package com.antiy.asset.controller;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ExportExcel;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.templet.AssetEntity;
import com.antiy.asset.vo.templet.AssetSoftwareEntity;
import com.antiy.asset.vo.templet.ImportResult;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Asset", description = "资产主表")
@RestController
@RequestMapping("/v1/asset")
@Slf4j
public class AssetController {

    @Resource
    public IAssetService iAssetService;

    /**
     * 保存
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "asset") AssetRequest asset) throws Exception {
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "asset") AssetRequest asset) throws Exception {
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(iAssetService.findPageAsset(asset));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "asset") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetService.deleteById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") AssetQuery assetQuery, ServletResponse response) throws Exception {
        List<AssetResponse> list = iAssetService.findListAsset(assetQuery);
        List entities = BeanConvert.convert(list, AssetEntity.class);
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xls", "硬件信息", entities);
    }

    /**
     * 通过ID删除
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出硬件信息", notes = "主键封装对象", produces = "application/octet-stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void exportTemplate(@ApiParam(value = "query") AssetQuery assetQuery, ServletResponse response) throws Exception {
        ExcelUtils.exportToClient(AssetEntity.class, "硬件信息表.xls", "硬件信息", null);
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
        Boolean success = successNum == asset.size();
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "失败了" + (asset.size() - successNum) + "条数据");
        }
    }

    private List<Asset> transferList(List<Object> objects) {
        List<Asset> assets = new ArrayList<>();
        objects.forEach(x -> assets.add((Asset) x));
        return assets;
    }
}

