package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.response.AssetSoftwareDetailResponse;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyajun
 * @since 2019-01-04
 */
@Transactional(rollbackFor = Exception.class)
@Api(value = "AssetSoftwareRelation", description = "资产软件关系信息")
@RestController
@RequestMapping("/api/v1/asset/softwarerelation")
public class AssetSoftwareRelationController {
    private static final Logger          logger = LogUtils.get();

    @Resource
    public IAssetSoftwareRelationService iAssetSoftwareRelationService;


    /**
     * 通过软件ID统计资产数量
     *
     * @param queryCondition
     * @return actionResponse
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ApiOperation(value = "通过软件ID统计资产数量", notes = "软件ID")
    @PreAuthorize("hasAuthority('asset:softwarerelation:countAssetBySoftId')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/countAssetBySoftId", method = RequestMethod.POST)
    public ActionResponse countAssetBySoftId(@ApiParam(value = "id") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService
            .countAssetBySoftId(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())));
    }

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return 操作系统名称集合
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ApiOperation(value = "查询操作系统接口", notes = "无查询条件")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryOS')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/os", method = RequestMethod.POST)
    public ActionResponse<List<SelectResponse>> queryOS() throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.findOS());
    }

    /**
     * 资产已关联的软件列表
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ApiOperation(value = "资产已关联软件列表", notes = "")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryInstallList')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/installedList", method = RequestMethod.POST)
    public ActionResponse<List<AssetSoftwareInstallResponse>> queryInstalledList(@ApiParam("查询条件") @RequestBody InstallQuery query) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.queryInstalledList(query));
    }
    /**
     * 资产可安装的软件列表
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ApiOperation(value = "资产可安装的软件列表", notes = "")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryInstallList')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/installableList", method = RequestMethod.POST)
    public ActionResponse<PageResult<AssetSoftwareInstallResponse>> queryInstallableList(@ApiParam("查询条件") @RequestBody InstallQuery query) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.queryInstallableList(query));
    }

    /**
     * 批量资产关联软件
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @ApiOperation(value = "批量资产关联软件", notes = "")
    @PreAuthorize("hasAuthority('asset:softwarerelation:queryInstallList')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/batchRelation", method = RequestMethod.POST)
    public ActionResponse batchRelation(@ApiParam("查询条件") @RequestBody AssetSoftwareReportRequest softwareReportRequest) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.batchRelation(softwareReportRequest));
    }
}
