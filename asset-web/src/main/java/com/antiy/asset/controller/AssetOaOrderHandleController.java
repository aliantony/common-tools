package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
import com.antiy.biz.file.FileRespVO;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Api(value = "AssetOaOrderHandle", description = "订单处理关联资产表")
@RestController
@RequestMapping("/api/v1/asset/assetoaorderhandle")
public class AssetOaOrderHandleController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderHandleService iAssetOaOrderHandleService;

    /**
     * 保存
     * @param assetOaOrderHandleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "订单处理提交接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleRequest assetOaOrderHandleRequest)throws Exception{
        logger.info("订单处理提交接口，assetOaOrderHandleRequest:{}", JSON.toJSONString(assetOaOrderHandleRequest));
        iAssetOaOrderHandleService.saveAssetOaOrderHandle(assetOaOrderHandleRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetOaOrderHandleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleRequest assetOaOrderHandleRequest)throws Exception{
        iAssetOaOrderHandleService.updateAssetOaOrderHandle(assetOaOrderHandleRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetOaOrderHandleQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleQuery assetOaOrderHandleQuery)throws Exception{
        return ActionResponse.success(iAssetOaOrderHandleService.findPageAssetOaOrderHandle(assetOaOrderHandleQuery));
    }

    /**
     * 通过ID查询
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "assetOaOrderHandle") @RequestParam Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderHandleService.getById(id));
    }

    /**
     * 通过ID删除
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestParam Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderHandleService.deleteById(id));
    }

    /**
     * 上传文件到hdfs
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ActionResponse upload(@ApiParam(value = "file", required = true) MultipartFile file) throws Exception {
        //定义文件返回对象
        List<FileRespVO> fileRespVOS = new ArrayList<>();
        iAssetOaOrderHandleService.uploadToHdfs(file, fileRespVOS);
        return ActionResponse.success(fileRespVOS);
    }

    /**
     * 文件下载
     *
     * @param fileUrl fileUrl地址
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件下载通用接口", notes = "传入下载参数信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),})
    @GetMapping(value = "/download")
    public void download(@ApiParam(value = "fileUrl") @RequestParam String fileUrl, @ApiParam(value = "fileName") @RequestParam String fileName, HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        ParamterExceptionUtils.isBlank(fileUrl, "url地址不能为空");
        // 文件名为空则取下载路径的
        if (StringUtils.isEmpty(fileName)) {
            fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        }
        // 下载文件
        iAssetOaOrderHandleService.downloadFromHdfs(request, response, fileName, fileUrl);
    }
}

