package com.antiy.asset.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.enums.AdmittanceStatusEnum;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AdmittanceRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author 吕梁
 * @since 2019-01-19
 */
@Api(value = "AssetAdmittance", description = "准入管理")
@RestController
@RequestMapping("/api/v1/asset/admittance")
public class AssetAdmittanceController {

    @Resource
    public IAssetService       assetService;
    @Resource
    public ExcelDownloadUtil   excelDownloadUtil;
    @Resource
    public AccessExportConvert accessExportConvert;

    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */

    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:admittance:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "asset") AssetQuery asset) throws Exception {
        asset.setAssetStatusList(Arrays.asList(new Integer[] { 3, 4, 5, 6, 7, 8, 9 }));
        asset.setAdmittance(true);
        return ActionResponse.success(assetService.findPageAsset(asset));
    }

    /**
     * 准入管理
     *
     * @return
     */
    @ApiOperation(value = "准入管理", notes = "准入管理")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/access/anagement", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:admittance:accessManagement')")
    public ActionResponse anagement(@RequestBody AdmittanceRequest admittance) throws Exception {
        ParamterExceptionUtils.isNull(admittance, "资产不能为空");
        ParamterExceptionUtils.isNull(admittance.getStringId(), "资产主键不能为空");
        ParamterExceptionUtils.isNull(admittance.getAdmittanceStatus(), "资产准入状态不能为空");
        Asset asset = new Asset();
        asset.setId(DataTypeUtils.stringToInteger(admittance.getStringId()));
        asset.setAdmittanceStatus(admittance.getAdmittanceStatus());
        Asset log = assetService.getById(admittance.getStringId());
        // 记录操作日志和运行日志
        if (admittance.getAdmittanceStatus() == 2) {
            LogUtils.recordOperLog(
                new BusinessData(AssetEventEnum.ASSET_ADMITTANCE_ALLOW.getName(), asset.getId(), log.getName(), log,
                    BusinessModuleEnum.ACCESS_MANAGEMENT, BusinessPhaseEnum.getByStatus(log.getAssetStatus())));
            LogUtils.info(LogUtils.get(AssetAdmittanceController.class),
                AssetEventEnum.ASSET_ADMITTANCE_ALLOW.getName() + " {}", asset.toString());
        } else if (admittance.getAdmittanceStatus() == 3) {
            LogUtils.recordOperLog(
                new BusinessData(AssetEventEnum.ASSET_ADMITTANCE_REFUSE.getName(), asset.getId(), log.getName(), log,
                    BusinessModuleEnum.ACCESS_MANAGEMENT, BusinessPhaseEnum.getByStatus(log.getAssetStatus())));
            LogUtils.info(LogUtils.get(AssetAdmittanceController.class),
                AssetEventEnum.ASSET_ADMITTANCE_REFUSE.getName() + " {}", asset.toString());
        }

        return ActionResponse.success(assetService.update(asset));
    }

    /**
     * 准入管理导出
     *
     * @return
     */
    @ApiOperation(value = "准入管理导出", notes = "准入管理")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/access/export", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:admittance:export')")
    public ActionResponse export(@ApiParam(value = "asset") @RequestParam(required = false) Integer status,
                                 Integer start, Integer end, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        if (start != null || end != null) {
            ParamterExceptionUtils.isTrue(start != null && end != null, "导出条数有误");
            ParamterExceptionUtils.isTrue(start <= end, "导出条数有误");
        }
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAdmittanceStatus(status);
        assetQuery.setPageSize(Constants.ALL_PAGE);
        if (start != null) {
            assetQuery.setStart(start - 1);
            assetQuery.setEnd(end - start + 1);
        }
        assetQuery.setAssetStatusList(Arrays.asList(3, 4, 5, 6, 7, 8, 9));
        List<AssetResponse> assetList = assetService.findListAsset(assetQuery, null);
        if (!CollectionUtils.isNotEmpty(assetList)) {
            return ActionResponse.success("没有数据可以导出");
        }
        List<AccessExport> accessExportList = accessExportConvert.convert(assetList, AccessExport.class);
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setDownloadList(accessExportList);
        excelDownloadUtil.excelDownload(request, response,
            "准入管理" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(
            new BusinessData("导出《准入管理" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0, "",
                assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(LogUtils.get(AssetAdmittanceController.class),
            AssetEventEnum.ASSET_ADMITTANCE_EXPORT.getName() + " {}", assetQuery.toString());
        return ActionResponse.success();
    }
}