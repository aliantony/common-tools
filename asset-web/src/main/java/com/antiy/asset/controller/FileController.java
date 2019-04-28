package com.antiy.asset.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.util.FileUtil;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.FileUseEnum;
import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
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
    private Logger              logger = LogUtils.get(this.getClass());

    @Resource
    public FileUtils            fileUtils;

    @Value("${modelName}")
    private String              modelName;

    @Value("${hdfs.fsUri}")
    private String              defaultHDFSUrl;
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
    public ActionResponse upload(@ApiParam(value = "fileList") List<MultipartFile> fileList,
                                 @ApiParam("MD5值") String md5,
                                 @ApiParam("文件用途") FileUseEnum fileUseEnum) throws Exception {
        ParamterExceptionUtils.isNull(fileUseEnum, "文件用途不能为空");
        // 判断文件集合，调用方法处理，进行上传
        if (!Objects.isNull(fileList) && !fileList.isEmpty()) {
            // 定义文件返回对象
            List<FileRespVO> fileRespVOS = new ArrayList<>();
            fileList.forEach(tmpFile -> {
                // 调用上传方法
                try {
                    // 记录操作日志
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.FILE_UPLOAD.getName(), null, null,
                        fileRespVOS, BusinessModuleEnum.COMMON, BusinessPhaseEnum.NONE));
                    uploadToHdfs(tmpFile, fileRespVOS, md5, fileUseEnum);
                } catch (Exception e) {
                    // 记录运行日志
                    LogUtils.info(logger, AssetEventEnum.FILE_UPLOAD.getName() + " {}", fileRespVOS);
                    throw new BusinessException(e.getMessage());
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
    public ActionResponse download(@ApiParam(value = "url地址") String url, String fileName,
                                   HttpServletResponse response) throws Exception {
        ParamterExceptionUtils.isBlank(url, "url地址不能为空");
        ParamterExceptionUtils.isBlank(fileName, "文件名字不能为空");
        // 清空response
        response.reset();
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition",
            "attachment; filename=" + new String(fileName.getBytes(UTF8), "ISO8859-1"));
        // 记录操作日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.FILE_DOWNLOAD.getName(), null, null, fileName,
            BusinessModuleEnum.COMMON, BusinessPhaseEnum.NONE));
        // 记录运行日志
        LogUtils.info(logger, AssetEventEnum.FILE_DOWNLOAD.getName() + " {}", fileName);
        FileResponse fileResponse = fileUtils.download(defaultHDFSUrl.concat(url));
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
    private void uploadToHdfs(MultipartFile tmpFile, List<FileRespVO> fileRespVOS, String md5,
                              FileUseEnum fileUseEnum) throws Exception {

        File file = fileUtils.tranferToFile(tmpFile);

        long fileSize = file.length();

        // 文件大小校验
        if (FileUseEnum.INSTALL_PACKAGE.getCode().equals(fileUseEnum.getCode())) {
            if (fileSize > FileUseEnum.INSTALL_PACKAGE.getSize()) {
                throw new BusinessException("文件过大");
            }

            if (!FileUseEnum.INSTALL_PACKAGE.getFormat().contains(FileUtil.getExtensionName(file.getName()))) {
                throw new BusinessException("文件格式错误");
            }
        } else if (FileUseEnum.INSTALL_INTRODUCE_MANUAL.getCode().equals(fileUseEnum.getCode())) {
            if (fileSize > FileUseEnum.INSTALL_INTRODUCE_MANUAL.getSize()) {
                throw new BusinessException("文件过大");
            }

            if (!FileUseEnum.INSTALL_INTRODUCE_MANUAL.getFormat().contains(FileUtil.getExtensionName(file.getName()))) {
                throw new BusinessException("文件格式错误");
            }
        }

        logger.info("单个文件上传开始");

        // 调用上传接口
        FileResponse fileResponse = fileUtils.upload(modelName, file);

        if (RespBasicCode.SUCCESS.getResultCode().equals(fileResponse.getCode())) {
            FileRespVO fileRep = (FileRespVO) fileResponse.getData();
            if (!(StringUtils.isNotEmpty(md5) && fileRep.getMd5().equals(md5))) {
                fileUtils.delete(fileRep.getFileUrl());
                throw new BusinessException("MD5校验失败");
            }
            fileRespVOS.add(fileRep);
        } else {
            // 记录运行日志
            LogUtils.info(logger, AssetEventEnum.FILE_UPLOAD.getName() + " {}",
                RespBasicCode.BUSSINESS_EXCETION.getResultDes());
        }

        // 删除临时文件
        fileUtils.deleteFile(file.toURI());

        logger.info("单个文件上传结束");
    }

}
