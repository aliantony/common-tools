package com.antiy.asset.controller;

import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
public class AssetEntryController {
    @Resource
    private iAssetEntryService iAssetEntryService;
    @Resource
    private IAssetService assetService;
    @Resource
    public AccessExportConvert accessExportConvert;
    @Resource
    public ExcelDownloadUtil excelDownloadUtil;





    public ActionResponse queryPage(AssetEntryQuery query) throws Exception {
        return ActionResponse.success(iAssetEntryService.queryPage(query));
    }

    public ActionResponse updateEntryStatus(AssetEntryRequest request) throws Exception {
        return ActionResponse.success(iAssetEntryService.updateEntryStatus(request));
    }
    public ActionResponse queryRecord(AssetEntryQuery  query) throws Exception {
        return ActionResponse.success(iAssetEntryService. queryRecord (query));
    }

    /**
     * 准入管理导出
     *
     * @return
     */
    @ApiOperation(value = "准入管理导出", notes = "准入管理")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),})
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
        assetQuery.setAssetStatusList(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11));
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