package com.antiy.asset.controller;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author lvliang
 * @since 2019-01-02
 */
@Api(value = "AssetUser", description = "资产用户信息")
@RestController
@RequestMapping("/api/v1/asset/user")
public class AssetUserController {

    @Resource
    public IAssetUserService       iAssetUserService;
    @Resource
    public IAssetDepartmentService iAssetDepartmentService;
    @Resource
    private RedisUtil              redisUtil;

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
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetUser") @RequestBody AssetUserQuery assetUser) throws Exception {
        if (StringUtils.isNotBlank(assetUser.getDepartmentId())) {
            List<AssetDepartmentResponse> responses = iAssetDepartmentService
                .findAssetDepartmentById(DataTypeUtils.stringToInteger(assetUser.getDepartmentId()));
            String[] ids = new String[responses.size()];
            for (int i = 0; i < responses.size(); i++) {
                ids[i] = responses.get(i).getStringId();
            }
            assetUser.setDepartmentIds(ids);
        }
        return ActionResponse.success(iAssetUserService.findPageAssetUser(assetUser));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetUserResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "id") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "Id不能为空");
        AssetUser assetUser = iAssetUserService.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey()));
        AssetUserResponse assetUserResponse = BeanConvert.convertBean(assetUser, AssetUserResponse.class);
        if (StringUtils.isNotBlank(assetUserResponse.getAddress())) {
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                    com.antiy.common.utils.DataTypeUtils.stringToInteger(assetUserResponse.getAddress()));
            SysArea sysArea = redisUtil.getObject(key, SysArea.class);
            if (Objects.isNull(sysArea)) {
                assetUserResponse.setAddress(null);
            } else {
                assetUserResponse.setAddressName(sysArea.getFullName());
            }
        }
        return ActionResponse.success(assetUserResponse);
    }

    /**
     * 注销用户
     *
     * @param baseRequest 主键
     * @return actionResponse
     */
    @ApiOperation(value = "注销用户", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ActionResponse cancelUser(@RequestBody @ApiParam(value = "baseRequest") BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "用户ID不能为空");
        return ActionResponse
            .success(iAssetUserService.deleteUserById(DataTypeUtils.stringToInteger(baseRequest.getStringId())));
    }

    /**
     * 查询下拉项的资产使用者信息
     *
     * @return 用户名集合
     */
    @ApiOperation(value = "查询下拉项的资产使用者信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/userInAsset", method = RequestMethod.POST)
    public ActionResponse queryUserInAsset() throws Exception {
        return ActionResponse.success(iAssetUserService.queryUserInAsset());
    }

}
