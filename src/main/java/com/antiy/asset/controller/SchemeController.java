package com.antiy.asset.controller;

import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "Scheme", description = "方案表")
@RestController
@RequestMapping("/v1/asset/scheme")
@Slf4j
public class SchemeController {

    @Resource
    public ISchemeService iSchemeService;

    /**
     * 保存
     *
     * @param scheme
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "scheme") SchemeRequest scheme) throws Exception {
        iSchemeService.saveScheme(scheme);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param scheme
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody(required = false) @ApiParam(value = "scheme") SchemeRequest scheme) throws Exception {
        iSchemeService.updateScheme(scheme);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param scheme
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "scheme") SchemeQuery scheme) throws Exception {
        return ActionResponse.success(iSchemeService.findPageScheme(scheme));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "scheme") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iSchemeService.getById(Integer.valueOf(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iSchemeService.deleteById(Integer.valueOf(query.getPrimaryKey())));
    }
}

