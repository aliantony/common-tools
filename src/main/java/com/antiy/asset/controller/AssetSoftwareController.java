package com.antiy.asset.controller;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ImportExcel;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.templet.AssetEntity;
import com.antiy.asset.vo.templet.AssetSoftwareEntity;
import com.antiy.asset.vo.templet.ImportResult;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "软件信息表")
@RestController
@RequestMapping("/v1/asset/assetsoftware")
@Slf4j
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetSoftware") AssetSoftwareRequest assetSoftware) throws Exception {
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftware") AssetSoftwareRequest assetSoftware) throws Exception {
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") AssetSoftwareQuery assetSoftware) throws Exception {
        return ActionResponse.success(iAssetSoftwareService.findPageAssetSoftware(assetSoftware));
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
    public ActionResponse queryById(@ApiParam(value = "assetSoftware") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.getById(Integer.parseInt(query.getPrimaryKey())));
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
        return ActionResponse.success(iAssetSoftwareService.deleteById(Integer.parseInt(query.getPrimaryKey())));
    }

    /**
     * 导出模板文件
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板文件", notes = "主键封装对象")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void export(@ApiParam(value = "query") QueryCondition query,HttpServletRequest request) throws Exception {
        ExcelUtils.exportToClient(AssetSoftwareEntity.class, "软件信息模板.xlsx", "软件信息", null);
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
        List<AssetSoftwareResponse> assetSoftwareResponses = iAssetSoftwareService.findListAssetSoftware(assetSoftwareQuery);
        List list = BeanConvert.convert(assetSoftwareResponses, AssetSoftwareEntity.class);
        ExcelUtils.exportToClient(AssetSoftwareEntity.class, "软件信息表.xlsx", "软件信息", list);
    }

    /**
     * 导入文件
     *
     * @param multipartFile 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导入文件", notes = "主键封装对象")
    @RequestMapping(value = "/import/file", method = RequestMethod.POST)
    public ActionResponse exportFile(@ApiParam(value = "multipartFile") MultipartFile multipartFile) throws Exception {
        ImportResult importResult = ExcelUtils.importExcelFromClient(AssetSoftwareEntity.class, multipartFile, 1, 0);
        List<?> list = importResult.getDataList();
        List<Object> assetSoftwares = BeanConvert.convert(list, AssetSoftware.class);
        Integer successNum = iAssetSoftwareService.batchSave(transferList(assetSoftwares));
        Boolean success = successNum == assetSoftwares.size();
        if (success) {
            return ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "失败了" + (assetSoftwares.size() - successNum) + "条数据");
        }
    }

    private List<AssetSoftware> transferList(List<Object> objects) {
        List<AssetSoftware> assetSoftwares = new ArrayList<>();
        objects.forEach(x -> assetSoftwares.add((AssetSoftware) x));
        return assetSoftwares;
    }
}

