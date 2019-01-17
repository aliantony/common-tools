package com.antiy.asset.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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

    /**
     * 保存
     *
     * @return actionResponse
     */
    @ApiOperation(value = "文件上传接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ActionResponse upload(@ApiParam(value = "fileList") List<MultipartFile> fileList) throws Exception {
        // 判断文件集合，调用方法处理，进行上传
        if (!Objects.isNull(fileList) && !fileList.isEmpty()) {
            // 定义文件返回对象
            List<FileRespVO> fileRespVOS = new ArrayList<>();
            fileList.forEach(tmpFile -> {
                // 调用上传方法
                uploadToHdfs(tmpFile, fileRespVOS);
            });
            return ActionResponse.success(fileRespVOS);
        }
        return ActionResponse.fail(RespBasicCode.ERROR);
    }

    /**
     * @Description 文件上传至文件服务器
     * @Date 10:19 2019/1/16
     * @param tmpFile
     * @param fileRespVOS
     * @return void
     */
    private void uploadToHdfs(MultipartFile tmpFile, List<FileRespVO> fileRespVOS) {
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
