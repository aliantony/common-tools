package com.antiy.asset.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author chenguoming
 * @since 2019-01-14
 */
@Api(value = "file", description = "漏洞/补丁文件上传通用接口")
@RestController
@RequestMapping("/api/v1/asset")
public class FileController {
    private static final Logger logger = LogUtils.get(FileController.class);

    @Resource
    public FileUtils            fileUtils;

    @Value("${modelName}")
    private String              modelName;
    private static final String UTF8   = "UTF-8";

    /**
     * 保存
     *
     * @return actionResponse
     */
    @ApiOperation(value = "文件上传接口", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:file:upload')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ActionResponse upload(@ApiParam(value = "fileList") List<MultipartFile> fileList) throws Exception {
        // 判断文件集合，调用方法处理，进行上传
        if (!Objects.isNull(fileList) && !fileList.isEmpty()) {
            // 定义文件返回对象
            List<FileRespVO> fileRespVOS = new ArrayList<>();
            fileList.forEach(tmpFile -> {
                // 调用上传方法
                try {
                    uploadToHdfs(tmpFile, fileRespVOS);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });
            return ActionResponse.success(fileRespVOS);
        }
        return ActionResponse.fail(RespBasicCode.ERROR);
    }

    /**
     * 文件下载
     * @param url url地址
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件下载", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:file:download')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/file/download", method = RequestMethod.GET)
    public ActionResponse download(@ApiParam(value = "url地址") String url, String fileName, HttpServletResponse response)
                                                                                                                        throws Exception {
        ParamterExceptionUtils.isBlank(url, "url地址不能为空");
        ParamterExceptionUtils.isBlank(fileName, "文件名字不能为空");
        // 清空response
        response.reset();
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename="
                                                  + new String(fileName.getBytes(UTF8), "ISO8859-1"));
        FileResponse fileResponse = fileUtils.download(url);
        if (fileResponse != null && RespBasicCode.SUCCESS.getResultCode().equals(fileResponse.getCode())) {
            InputStream inputStream = (InputStream) fileResponse.getData();

            byte[] buffer = readInputStream(inputStream);
            if (null != buffer && buffer.length > 0) {
                // 设置response的Header
                response.addHeader("Content-Length", "" + buffer.length);
                OutputStream toClient = response.getOutputStream();
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            }
        }
        return ActionResponse.success();
    }

    private byte[] readInputStream(InputStream fis) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        fis.close();
        return outStream.toByteArray();
    }

    /**
     * @Description 文件上传至文件服务器
     * @Date 10:19 2019/1/16
     * @param tmpFile
     * @param fileRespVOS
     * @return void
     */
    private void uploadToHdfs(MultipartFile tmpFile, List<FileRespVO> fileRespVOS) throws Exception {
        logger.info("单个文件上传开始");

        File file = fileUtils.tranferToFile(tmpFile);

        // 调用上传接口
        FileResponse fileResponse = fileUtils.upload(modelName, file);
        if (RespBasicCode.SUCCESS.getResultCode().equals(fileResponse.getCode())) {
            FileRespVO fileRep = (FileRespVO) fileResponse.getData();
            fileRespVOS.add(fileRep);
        } else {
            logger.info(RespBasicCode.BUSSINESS_EXCETION.getResultDes());
        }

        // 删除临时文件
        fileUtils.deleteFile(file.toURI());

        logger.info("单个文件上传结束");
    }

}