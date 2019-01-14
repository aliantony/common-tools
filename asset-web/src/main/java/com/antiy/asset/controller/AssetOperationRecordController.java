package com.antiy.asset.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftware", description = "资产操作记录")
@RestController
@RequestMapping("/v1/asset/assetoperationrecord")
public class AssetOperationRecordController {

    @Resource
    private IAssetOperationRecordService assetOperationRecordService;

    /**
     * 查找资产操作历史
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "查找资产操作历史", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftware") @PathVariable("id") Integer id) throws Exception {
        return ActionResponse.success(assetOperationRecordService.findAssetOperationRecordByAssetId(id));
    }


}
