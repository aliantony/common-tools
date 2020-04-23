package com.antiy.asset.controller;

import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.impl.AssetEntryServiceImpl;
import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryRecordResponse;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.asset.vo.response.AssetEntryStatusResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@Api(value = "AssetEntryController", description = "准入管理")
@RestController
@RequestMapping("/api/v1/asset/entryControl")
public class AssetEntryController {
    @Resource
    private AssetEntryServiceImpl iAssetEntryService;
    @Resource
    private IAssetService assetService;
    @Resource
    public AccessExportConvert accessExportConvert;
    @Resource
    public ExcelDownloadUtil excelDownloadUtil;


    @ApiOperation(value = "综合列表", response = AssetEntryResponse.class, responseContainer = "List")
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryPage(@RequestBody AssetEntryQuery query) throws Exception {
        return ActionResponse.success(iAssetEntryService.queryPage(query));
    }

    @ApiOperation(value = "准入指令下发", notes = "更新准入状态", response = String.class, responseContainer = "ActionResponse")
    @RequestMapping(value = "/update/entryStatus", method = RequestMethod.POST)
    public ActionResponse updateEntryStatus(@RequestBody AssetEntryRequest request) throws Exception {
        return ActionResponse.success(iAssetEntryService.updateEntryStatus(request));
    }

    @ApiOperation(value = "准入历史记录", notes = "查询历史记录", response = AssetEntryRecordResponse.class, responseContainer = "List")
    @RequestMapping(value = "/query/record", method = RequestMethod.POST)
    public ActionResponse queryRecord(@RequestBody AssetEntryQuery query) throws Exception {
        return ActionResponse.success(iAssetEntryService.queryRecord(query));
    }

    @ApiOperation(hidden = true,value = "准入状态查询", notes = "查询资产准入状态", response = AssetEntryStatusResponse.class, responseContainer = "List")
    @RequestMapping(value = "/query/entryStatus", method = RequestMethod.POST)
    public ActionResponse queryEntryStatus(@RequestBody AssetEntryRequest request) {
        return ActionResponse.success(iAssetEntryService.queryEntryStatus(request.getAssetActivityRequests().stream()
        .map(ActivityHandleRequest::getStringId).collect(Collectors.toList())));
    }

    @ApiOperation(value = "对接漏洞配置补丁", notes = "是否弹出阻断入网提示窗", response = Boolean.class)
    @RequestMapping(value = "/query/entryOperation", method = RequestMethod.POST)
    public ActionResponse queryEntryOperation(@RequestBody AssetEntryQuery query) {
        return ActionResponse.success(iAssetEntryService.queryEntryOperation(query));
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
        AssetEntryQuery assetQuery = new AssetEntryQuery();
        if (!Objects.isNull(status)) {
            assetQuery.setEntryStatus(String.valueOf(status));
        }
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.validate();
//        if (start != null) {
//            assetQuery.setStart(start - 1);
//            assetQuery.setEnd(end - start + 1);
//        }
        List<AssetEntryResponse> assetList = iAssetEntryService.queryList(assetQuery);
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
                new BusinessData("导出资产", 0, "",
                        assetQuery, BusinessModuleEnum.ACCESS_MANAGEMENT, BusinessPhaseEnum.NONE));
        LogUtils.info(LogUtils.get(AssetAdmittanceController.class),
                AssetEventEnum.ASSET_ADMITTANCE_EXPORT.getName() + " {}", assetQuery.toString());
        return ActionResponse.success();
    }

}
