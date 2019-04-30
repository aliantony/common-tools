package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 升级包表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-04
 */
@Component
public class FileServiceImpl {

    @Resource
    private FileUtils fileUtils;
    @Value("${hdfs.fsUri}")
    private String    defaultHDFSUrl;

    void deleteRemoteFile(BasicRequest request, String fileUrl, Logger logger) throws Exception {
        FileResponse fileResponse;
        if (StringUtils.isNotBlank(fileUrl)) {
            fileResponse = fileUtils.delete(defaultHDFSUrl + fileUrl);
            if (!fileResponse.getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                LogUtils.info(logger, "{} 删除文件失败", RespBasicCode.NOT_FOUND.getResultDes(), fileResponse);
                LogUtils.info(logger, "{} 删除文件失败", RespBasicCode.BUSSINESS_EXCETION.getResultDes(), request);
            }
        }
    }
}
