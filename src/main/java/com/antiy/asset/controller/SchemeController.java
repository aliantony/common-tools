package com.antiy.asset.controller;

import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "Scheme", description = "方案表")
@RestController
@RequestMapping("/v1/asset/scheme")
public class SchemeController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public ISchemeService iSchemeService;

    /**
     * 保存
     *
     * @param schemeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "scheme") @RequestBody SchemeRequest schemeRequest) throws Exception {
        return ActionResponse.success(iSchemeService.saveScheme(schemeRequest));
    }

    /**
     * 修改
     *
     * @param schemeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "scheme") SchemeRequest schemeRequest) throws Exception {
        iSchemeService.updateScheme(schemeRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param schemeQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "scheme") SchemeQuery schemeQuery) throws Exception {
        return ActionResponse.success(iSchemeService.findPageScheme(schemeQuery));
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "scheme") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iSchemeService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iSchemeService.deleteById(id));
    }
}

