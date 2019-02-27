package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetPortProtocol", description = "端口协议")
@RestController
@RequestMapping("/api/v1/asset/portprotocol")
public class AssetPortProtocolController {

    @Resource
    public IAssetPortProtocolService iAssetPortProtocolService;

    /**
     * 保存
     *
     * @param assetPortProtocol
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:portprotocol:saveSingle')")
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "assetPortProtocol") AssetPortProtocolRequest assetPortProtocol) throws Exception {
        iAssetPortProtocolService.saveAssetPortProtocol(assetPortProtocol);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetPortProtocol
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:portprotocol:updateSingle')")
    public ActionResponse updateSingle(@RequestBody(required = false) @ApiParam(value = "assetPortProtocol") AssetPortProtocolRequest assetPortProtocol) throws Exception {
        iAssetPortProtocolService.updateAssetPortProtocol(assetPortProtocol);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetPortProtocol
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:portprotocol:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetPortProtocol") AssetPortProtocolQuery assetPortProtocol) throws Exception {
        return ActionResponse.success(iAssetPortProtocolService.findPageAssetPortProtocol(assetPortProtocol));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:portprotocol:queryById')")
    public ActionResponse queryById(@ApiParam(value = "assetPortProtocol") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetPortProtocolService.getById(Integer.parseInt(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "（无效）通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:portprotocol:deleteById')")
    public ActionResponse deleteById(@ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetPortProtocolService.deleteById(Integer.parseInt(query.getPrimaryKey())));
    }
}
