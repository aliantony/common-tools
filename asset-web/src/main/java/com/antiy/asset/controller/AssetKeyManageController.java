package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetKeyManageService;
import com.antiy.asset.vo.enums.KeyStatusEnum;
import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author chenchaowu
 */
@Api(value = "AssetKeyManage", description = "Key管理")
@RestController
@RequestMapping("/api/v1/asset/key")
public class AssetKeyManageController {

    @Resource
    private IAssetKeyManageService keyManageService;

    /**
     * 批量查询
     *
     * @param query
     * @return actionResponse
     */

    @ApiOperation(tags = "批量查询接口",value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "query") AssetKeyManageQuery query) throws Exception {
        return ActionResponse.success(keyManageService.findPageAssetKeys(query));
    }

    /**
     * key登记
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "登记接口",value = "key登记", notes = "key登记")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ActionResponse keyRegister(@RequestBody AssetKeyManageRequest request) throws Exception {
        return ActionResponse.success(keyManageService.keyRegister(request));
    }

    /**
     * key领用
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "领用接口",value = "key领用", notes = "key领用")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/recipients", method = RequestMethod.POST)
    public ActionResponse keyRecipients(@RequestBody AssetKeyManageRequest request) throws Exception {
        return ActionResponse.success(keyManageService.keyRecipients(request));
    }

    /**
     * key归还
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "归还接口",value = "key归还", notes = "key归还")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public ActionResponse keyReturn(@RequestBody AssetKeyManageRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getId(), "ID不能为空");
        return ActionResponse.success(keyManageService.keyReturn(request));
    }

    /**
     * key冻结
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "冻结接口",value = "key冻结", notes = "key冻结")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    public ActionResponse keyFreeze(@RequestBody AssetKeyManageRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getId(), "ID不能为空");
        return ActionResponse.success(keyManageService.keyFreeze(request, KeyStatusEnum.KEY_FREEZE.getStatus()));
    }

    /**
     * key解冻
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "解冻接口",value = "key解冻", notes = "key解冻")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST)
    public ActionResponse keyUnfreeze(@RequestBody AssetKeyManageRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getId(), "ID不能为空");
        return ActionResponse.success(keyManageService.keyFreeze(request, KeyStatusEnum.KEY_RECIPIENTS.getStatus()));
    }

    /**
     * key删除
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(tags = "删除接口",value = "key删除", notes = "key删除")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResponse keyDelete(@RequestBody AssetKeyManageRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getId(), "ID不能为空");
        return ActionResponse.success(keyManageService.keyRemove(request));
    }

    /**
     * 导出模板
     *
     * @return actionResponse
     */
    @ApiOperation(value = "导出模板", notes = "主键封装对象")
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:exportTemplate')")
    public void exportTemplate() throws Exception {
        keyManageService.exportTemplate();
    }

    /**
     * 硬件资产-导入网络设备
     *
     * @return
     */
    @ApiOperation(value = "导入KEY", notes = "导入EXcel")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/import/key", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:asset:importNet')")
    public ActionResponse importUser(@ApiParam(value = "file") MultipartFile file) throws Exception {
        if (file == null) {

            throw new BusinessException("导入失败，文件为空，没有选择文件！");
        }
        if (file.getSize() > 1048576 * 5) {

            throw new BusinessException("导入失败，文件不超过5M！");
        }
        return ActionResponse.success(keyManageService.importKey(file));
    }
}