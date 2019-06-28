package com.antiy.asset.controller;

import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import com.antiy.asset.controller.FileController;
import com.antiy.asset.util.ControllerUtil;
import com.antiy.asset.vo.enums.FileUseEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.biz.file.FileRespVO;
import com.antiy.biz.file.FileResponse;
import com.antiy.biz.file.FileUtils;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangxin
 * @date 2019/6/20
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ LogUtils.class })
public class FileControllerTest {

    @InjectMocks
    private FileController   fileController;

    @Mock
    private FileUtils        fileUtils;

    private MockMvc          mockMvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // @Mock
    // private String modelName = "safety";

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LogUtils.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        PowerMockito.doNothing().when(LogUtils.class, "info", Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void upload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("fileList", "123.rar", "application/x-rar-compressed",
            "132".getBytes());

        FileResponse<FileRespVO> fileResponse = FileResponse.of();
        FileRespVO fileRespVO = new FileRespVO();
        fileRespVO.setMd5("35CFF75491697930993E99EADD63A75F");
        fileRespVO.setOriginFileName("123.rar");
        fileResponse.setData(fileRespVO);

        when(fileUtils.uploadFileFromLocal(Mockito.any(MultipartFile.class), Mockito.any())).thenReturn(fileResponse);

        // 安装包-成功
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload").file(file)
            .param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode()).param("md5", "35CFF75491697930993E99EADD63A75F"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "132".getBytes());

        // 说明书-成功
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload").file(file)
            .param("fileUse", FileUseEnum.INSTALL_INTRODUCE_MANUAL.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult2).getHead().getCode());

        // SCHEME_FILE-成功
        MvcResult mvcResult3 = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload").file(file)
                .param("fileUse", FileUseEnum.SCHEME_FILE.getCode()).param("md5", "35CFF75491697930993E99EADD63A75F"))
            .andReturn();
         Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
         ControllerUtil.getResponse(mvcResult3).getHead().getCode());

    }

    @Test
    public void uploadException01() throws Exception {
        expectedException.expectMessage("文件格式错误");
        uploadPassCommon();
        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "132".getBytes());
        // 安装包-失败-格式不正确
        MvcResult mvcResultFail = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .file(inCorrectTypeFile).param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
    }

    @Test
    public void uploadException02() throws Exception {
        expectedException.expectMessage("文件格式错误");
        uploadPassCommon();
        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "132".getBytes());
        // 说明书-失败-格式不正确
        MvcResult mvcResultFail2 = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .file(inCorrectTypeFile).param("fileUse", FileUseEnum.INSTALL_INTRODUCE_MANUAL.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
    }

    @Test
    public void uploadException03() throws Exception {
        expectedException.expectMessage("文件格式错误");
        uploadPassCommon();
        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "132".getBytes());
        // SCHEME_失败-格式不正确
        MvcResult mvcResultFail3 = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload").file(inCorrectTypeFile)
                .param("fileUse", FileUseEnum.SCHEME_FILE.getCode()).param("md5", "35CFF75491697930993E99EADD63A75F"))
            .andReturn();
    }

    @Test
    public void uploadException04() throws Exception {
        expectedException.expectMessage("文件格式错误");
        uploadPassCommon();
        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "132".getBytes());
        // 安装包-失败-格式不正确
        MvcResult mvcResultFail = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .file(inCorrectTypeFile).param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
    }

    /**
     * 空文件(大小为0)
     * @throws Exception
     */
    @Test
    public void uploadException05() throws Exception {
        expectedException.expectMessage("上传文件不能为空，请重新选择");
        uploadPassCommon();
        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.ra2r",
            "application/x-rar-compressed", "".getBytes());
        MvcResult mvcResultFail = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .file(inCorrectTypeFile).param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
    }

    /**
     * 空文件(未上传)
     * @throws Exception
     */
    @Test
    public void uploadException051() throws Exception {
        MvcResult mvcResultFail = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode()).param("md5", "35CFF75491697930993E99EADD63A75F"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.ERROR.getResultCode(),
            ControllerUtil.getResponse(mvcResultFail).getHead().getCode());
    }

    /**
     * 调用hdfs上传返回结果为失败->打印日志
     * @throws Exception
     */
    @Test
    public void uploadException06() throws Exception {

        FileResponse<FileRespVO> fileResponseFail = FileResponse.of(RespBasicCode.BUSSINESS_EXCETION);
        FileRespVO fileRespVOFail = new FileRespVO();
        fileRespVOFail.setMd5("35CFF75491697930993E99EADD63A75F");
        fileRespVOFail.setOriginFileName("123.rar");
        fileResponseFail.setData(fileRespVOFail);

        when(fileUtils.uploadFileFromLocal(Mockito.any(MultipartFile.class), Mockito.any()))
            .thenReturn(fileResponseFail);

        MockMultipartFile inCorrectTypeFile = new MockMultipartFile("fileList", "123.rar",
            "application/x-rar-compressed", "132".getBytes());
        // 安装包-失败-格式不正确
        MvcResult mvcResultFail = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/file/upload")
            .file(inCorrectTypeFile).param("fileUse", FileUseEnum.INSTALL_PACKAGE.getCode())
            .param("md5", "35CFF75491697930993E99EADD63A75F")).andReturn();
    }

    /**
     * 删除成功
     */
    @Test
    public void deleteFileByUrl() throws Exception {
        FileResponse<FileRespVO> fileResponse = FileResponse.of();
        when(fileUtils.delete(Mockito.anyString())).thenReturn(fileResponse);
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/api/v1/asset/file/delete").param("delUrls", "hhhh")).andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    /**
     * 删除失败
     */
    @Test
    public void deleteFileByUrlFail() throws Exception {
        FileResponse<FileRespVO> fileResponse = FileResponse.of(RespBasicCode.BUSSINESS_EXCETION);
        when(fileUtils.delete(Mockito.anyString())).thenReturn(fileResponse);
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/api/v1/asset/file/delete").param("delUrls", "hhhh")).andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    /**
     * 下载后返回结果 ActionResponse.success();会导致不能找到合适的表现层 HttpMediaTypeNotAcceptableException
     * @throws Exception
     */
    @Test(expected = HttpMediaTypeNotAcceptableException.class)
    public void downloadTest() throws Exception {
        FileResponse<InputStream> fileResponse = FileResponse.of();
        FileRespVO fileRespVOFail = new FileRespVO();
        fileRespVOFail.setMd5("35CFF75491697930993E99EADD63A75F");
        fileRespVOFail.setOriginFileName("123.rar");

        MockMultipartFile file = new MockMultipartFile("fileList", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        fileResponse.setData(file.getInputStream());
        PowerMockito.mock(OutputStream.class);

        ReflectionTestUtils.setField(fileController, "defaultHDFSUrl", "hhh");

        when(fileUtils.download(Mockito.anyString())).thenReturn(fileResponse);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/asset/file/download").param("url", "hhhh").param("fileName", "hhh"));

    }

    private void uploadPassCommon() throws Exception {
        FileResponse<FileRespVO> fileResponse = FileResponse.of();
        FileRespVO fileRespVO = new FileRespVO();
        fileRespVO.setMd5("35CFF75491697930993E99EADD63A75F");
        fileRespVO.setOriginFileName("123.rar");
        fileResponse.setData(fileRespVO);
        when(fileUtils.uploadFileFromLocal(Mockito.any(MultipartFile.class), Mockito.any())).thenReturn(fileResponse);
    }

    private void uploadOverFileSize() throws Exception {
        MockMultipartFile file = new MockMultipartFile("fileList", "123.rar", "application/x-rar-compressed",
            "132".getBytes());

        FileResponse<FileRespVO> fileResponse = FileResponse.of();
        FileRespVO fileRespVO = new FileRespVO();
        fileRespVO.setMd5("35CFF75491697930993E99EADD63A75F");
        fileRespVO.setOriginFileName("123.rar");
        fileRespVO.setFileSize(999999999999999L);
        fileResponse.setData(fileRespVO);

        when(fileUtils.uploadFileFromLocal(Mockito.any(MultipartFile.class), Mockito.any())).thenReturn(fileResponse);
    }
}