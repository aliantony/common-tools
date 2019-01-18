package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.templet.AssetEntity;
import com.antiy.asset.templet.AssetUserEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.encoder.Encode;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lvliang
 * @since 2019-01-02
 */
@Api(value = "AssetUser", description = "资产用户信息")
@RestController
@RequestMapping("/api/v1/asset/assetuser")
public class AssetUserController {

    @Resource
    public IAssetUserService iAssetUserService;

    /**
     * 保存
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.saveAssetUser(assetUser));
    }

    /**
     * 导入用户信息
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入用户信息", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/importUser", method = RequestMethod.POST)
    public void importUser(@RequestBody @PathVariable("file") MultipartFile file) throws Exception {
        ImportResult<AssetUserEntity> importResult = ExcelUtils.importExcelFromClient(AssetUserEntity.class, file, 1,
            0);
        List<AssetUserEntity> assetUserEntityList = importResult.getDataList();
        List<AssetUser> assetUserList = BeanConvert.convert(assetUserEntityList, AssetUser.class);
        iAssetUserService.importUser(assetUserList);
    }

    /**
     * 导出用户模板
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导出用户模板", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/exportTemplet", method = RequestMethod.GET)
    public void exportTemplet() throws Exception {
        ExcelUtils.exportTemplet(AssetUserEntity.class, "用户信息表", "用户信息");
    }

    /**
     * 修改
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.updateAssetUser(assetUser));
    }

    /**
     * 批量查询
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetUserResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetUser") AssetUserQuery assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.findPageAssetUser(assetUser));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetUserResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable @ApiParam(value = "id") @Encode String id) throws Exception {
        return ActionResponse.success(iAssetUserService.getById(DataTypeUtils.stringToInteger(id)));
    }

    /**
     * 注销用户
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "注销用户", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.POST)
    public ActionResponse cancelUser(@PathVariable @RequestBody @ApiParam(value = "id") @Encode String id) throws Exception {
        return ActionResponse.success(iAssetUserService.deleteById(DataTypeUtils.stringToInteger(id)));
    }

    /**
     * 查询下拉项的资产使用者信息
     *
     * @return 用户名集合
     */
    @ApiOperation(value = "查询下拉项的资产使用者信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/userInAsset", method = RequestMethod.GET)
    public ActionResponse queryUserInAsset() throws Exception {
        return ActionResponse.success(iAssetUserService.queryUserInAsset());
    }
}
