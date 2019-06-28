package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.vo.enums.FileUseEnum;
import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @SpyBean
    public FileUtils              fileUtils;
    private MockMvc               mockMvc;
    private static FileRespVO     fileRespVOExpected = new FileRespVO();
    static {
        fileRespVOExpected.setOriginFileName("fileList");
        fileRespVOExpected.setFileSize(21);
        fileRespVOExpected.setFileUrl("aa");
        fileRespVOExpected.setMd5("aaa");
        fileRespVOExpected.setCurrFileName("test.doc");
    }
    private FileResponse<FileRespVO> fileResponseExpected = FileResponse.of(RespBasicCode.SUCCESS, fileRespVOExpected);

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void upload() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test.doc", "text/plain",
            "test file 12312312312".getBytes());
        doReturn(fileResponseExpected).when(fileUtils).upload(Mockito.anyString(), Mockito.any());
        MvcResult mvcResult = mockMvc
            .perform(multipart("/api/v1/asset/file/upload").file(multipartFile).param("md5", "123e2e2e2e")
                .param("fileUse", "INSTALL_INTRODUCE_MANUAL"))
            .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        ActionResponse obj = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        JSONObject object = (JSONObject) ((JSONArray) obj.getBody()).get(0);
        Assert.assertThat(object.getString("currFileName"), Matchers.is("test.doc"));
    }

    @Test
    public void download() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("files", "test.doc", "text/plain",
            "test file 12312312312".getBytes());
        FileResponse<FileRespVO> fileResponseExpected = FileResponse.of(RespBasicCode.SUCCESS, fileRespVOExpected);
        doReturn(FileResponse.of(RespBasicCode.SUCCESS, multipartFile.getInputStream())).when(fileUtils)
            .download(Mockito.anyString());
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.get("/api/v1/asset/file/download")
                .param("url", "/vul/20190307/c4e98fc6739545d3b9db8cca48b0e3eb.docx").param("fileName", "目录结构解析.docx"))
            .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(mvcResult.getClass());
        System.out.println(mvcResult);
        String object = mvcResult.getResponse().getContentAsString();
        System.out.println(object);
        Assert.assertThat(object, Matchers.is("test file 12312312312"));
    }
}