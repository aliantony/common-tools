package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.SoftInstallStatus;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "软件信息表")
@RestController
@RequestMapping("/api/v1/asset/software")
public class AssetSoftwareController {

    @Resource
    public IAssetSoftwareService iAssetSoftwareService;

    /**
     * 保存
     *
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "软件资产登记接口", notes = "传入登记信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:saveSingle')")
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "assetSoftware") AssetSoftwareRequest assetSoftware) throws Exception {

        return iAssetSoftwareService.saveAssetSoftware(assetSoftware);
    }

    /**
     * 修改
     *
     * @param assetSoftware
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse") })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:updateSingle')")
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
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") @RequestBody AssetSoftwareQuery assetSoftware) throws Exception {
        return ActionResponse.success(iAssetSoftwareService.findPageAssetSoftware(assetSoftware));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:queryById')")
    public ActionResponse queryById(@ApiParam(value = "assetSoftware") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @PreAuthorize(value = "hasAuthority('asset:software:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isNull(baseRequest.getStringId(), "ID不能为空");
        return ActionResponse
            .success(iAssetSoftwareService.deleteById(DataTypeUtils.stringToInteger(baseRequest.getStringId())));
    }

    /**
     * 导出模板文件
     *
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板文件", notes = "主键封装对象")
    @PreAuthorize(value = "hasAuthority('asset:software:export')")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void export() throws Exception {
        iAssetSoftwareService.exportTemplate();
    }

    @ApiOperation(value = "查询厂商接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/pulldown/manufacturer", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:pulldownManufacturer')")
    public ActionResponse<List<String>> pulldownManufacturer() throws Exception {
        return ActionResponse.success(iAssetSoftwareService.pulldownManufacturer());
    }

    /**
     * 导出模板文件
     *
     * @param assetSoftwareQuery 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导出数据文件", notes = "主键封装对象")
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:software:exportFile')")
    public void exportFile(@ApiParam(value = "assetSoftwareQuery") AssetSoftwareQuery assetSoftwareQuery,
                           HttpServletResponse response, HttpServletRequest request) throws Exception {
        iAssetSoftwareService.exportData(assetSoftwareQuery, response, request);
    }

    /**
     * 导入软件excel文件
     *
     * @param multipartFile 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "导入软件excel文件", notes = "导入软件excel文件")
    @RequestMapping(value = "/import/file", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:importExcel')")
    public ActionResponse importExcel(@ApiParam(value = "multipartFile") MultipartFile multipartFile,
                                      AssetImportRequest importRequest) throws Exception {

        return ActionResponse.success(iAssetSoftwareService.importExcel(multipartFile, importRequest));

    }

    /**
     * 软件资产按二级品类型号统计
     *
     * @return 品类型号名和该品类信号型产数量的映射
     */
    @ApiOperation(value = "软件资产按二级品类型号统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/category", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:countAssetByCategory')")
    public ActionResponse countAssetByCategory() throws Exception {
        return ActionResponse.success(iAssetSoftwareService.countCategory());
    }

    /**
     * 硬件资产按状态统计
     *
     * @return 状态名和该状态下资产数量的映射
     */
    @ApiOperation(value = "软件资产按状态统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountColumnarResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/status", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:countAssetByStatus')")
    public ActionResponse countAssetByStatus() throws Exception {
        return ActionResponse.success(iAssetSoftwareService.countStatus());
    }

    /**
     * 硬件资产按厂商统计
     *
     * @return 厂商名和该厂商资产数量的映射
     */
    @ApiOperation(value = "软件资产按厂商统计接口", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/manufacturer", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:countAssetByManufacturer')")
    public ActionResponse countAssetByManufacturer() throws Exception {
        return ActionResponse.success(iAssetSoftwareService.countManufacturer());
    }

    /**
     * 软件资产厂商查询
     * @param manufacturerName
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "软件资产厂商查询（无效)", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/manufacturer", method = RequestMethod.POST)
    public ActionResponse<List<String>> queryAssetByManufacturer(@ApiParam(value = "manufacturerName") @RequestBody String manufacturerName) throws Exception {
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
    @RequestMapping(value = "/query/detail", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:querySoftwareDetail')")
    public ActionResponse<AssetSoftwareDetailResponse> querySoftwareDetail(@ApiParam(value = "softwareQuery") @RequestBody SoftwareQuery softwareQuery) throws Exception {
        ParamterExceptionUtils.isBlank(softwareQuery.getPrimaryKey(), "软件资产Id不能为空");
        return ActionResponse.success(iAssetSoftwareService.querySoftWareDetail(softwareQuery));
    }

    /**
     * 软件安装列表查询
     * @param softwareQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "软件安装列表查询", notes = "软件安装列表查询")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/install", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:queryInstallList')")
    public ActionResponse queryInstallList(@ApiParam(value = "softwareQuery") @RequestBody AssetSoftwareQuery softwareQuery) throws Exception {
        return ActionResponse.success(iAssetSoftwareService.findPageInstall(softwareQuery));
    }

    /**
     * 软件对应的硬件安装列表查询
     * @param softwareQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "软件对应的硬件安装列表查询", notes = "软件安装列表查询")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/assetinstall", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:queryAssetInstallList')")
    public ActionResponse queryAssetInstallList(@ApiParam(value = "softwareQuery") @RequestBody AssetSoftwareQuery softwareQuery) throws Exception {
        // 只查询安装成功的
        softwareQuery.setInstallStatus(SoftInstallStatus.SUCCESS.getCode());
        return ActionResponse.success(iAssetSoftwareService.findPageAssetInstall(softwareQuery));
    }

    /**
     * 自动安装、人工安装进度
     * @param softwareQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "自动安装、人工安装进度", notes = "自动安装、人工安装进度")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareDetailResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/installSchedule", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:queryInstallSchedule')")
    public ActionResponse queryInstallSchedule(@ApiParam(value = "softwareQuery") @RequestBody AssetSoftwareQuery softwareQuery) throws Exception {
        ParamterExceptionUtils.isBlank(softwareQuery.getId(), "软件ID不能为空");
        SoftwareInstallResponse softwareInstallResponse = new SoftwareInstallResponse();
        softwareInstallResponse.setAssetSoftware(
            BeanConvert.convertBean(iAssetSoftwareService.getById(softwareQuery.getId()), AssetSoftwareResponse.class));
        softwareInstallResponse
            .setAssetSoftwareInstallResponseList(iAssetSoftwareService.findAssetInstallList(softwareQuery));
        return ActionResponse.success(softwareInstallResponse);
    }

    @ApiOperation(value = "资产配置(软件安装配置)", notes = "资产配置")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/asset/setting", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:software:assetSetting')")
    public ActionResponse assetSetting(@ApiParam @RequestBody ConfigRegisterRequest registerRequest) throws Exception {
        return iAssetSoftwareService.softwareInstallConfig(registerRequest);
    }
}
