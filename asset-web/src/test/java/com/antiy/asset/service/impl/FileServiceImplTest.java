package com.antiy.asset.service.impl;

import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.base.RespBasicCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author zhangxin
 * @date 2019/7/5
 */
@RunWith(PowerMockRunner.class)
public class FileServiceImplTest {
    @InjectMocks
    private FileServiceImpl fileService;
    @Mock
    private FileUtils       fileUtils;

    @Mock
    private Logger          logger;

    @Test
    public void deleteRemoteFileTest() throws Exception {
        // 删除失败
        FileResponse fileResponse = FileResponse.of(RespBasicCode.ERROR);
        when(fileUtils.delete(Mockito.anyString())).thenReturn(fileResponse);

        BasicRequest request = new BasicRequest();
        fileService.deleteRemoteFile(request, "url", logger);
    }

    /**
     * 删除成功
     * @throws Exception
     */
    @Test
    public void deleteRemoteFileTest02() throws Exception {
        FileResponse fileResponse = FileResponse.of(RespBasicCode.SUCCESS);
        when(fileUtils.delete(Mockito.anyString())).thenReturn(fileResponse);

        BasicRequest request = new BasicRequest();
        fileService.deleteRemoteFile(request, "url", logger);
    }

}