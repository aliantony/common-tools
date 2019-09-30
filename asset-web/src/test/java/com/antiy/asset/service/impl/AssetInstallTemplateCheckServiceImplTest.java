package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateCheckDao;
import com.antiy.asset.entity.AssetInstallTemplateCheck;
import com.antiy.asset.util.LoginUtil;
import com.antiy.asset.vo.enums.AssetInstallTemplateCheckStautsEnum;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssetInstallTemplateCheckServiceImplTest {

    @MockBean
    private AssetInstallTemplateCheckDao assetInstallTemplateCheckDao;
    @SpyBean
    private AssetInstallTemplateCheckServiceImpl assetInstallTemplateCheckService;
    @MockBean
    private RedisUtil redisUtil;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();
    }
    @Test
    public void queryAssetInstallTemplateCheckById() {

    }

    @Test
    public void queryTemplateCheckByTemplateId() throws Exception {
        QueryCondition queryCondition=new QueryCondition();
        queryCondition.setPrimaryKey("1");
        /**
         * 构建数据
         */
        List<AssetInstallTemplateCheck> installTemplateCheckList=new ArrayList<>();
        AssetInstallTemplateCheck assetInstallTemplateCheck=new AssetInstallTemplateCheck();
        assetInstallTemplateCheck.setResult(1);
        assetInstallTemplateCheck.setId(1);
        assetInstallTemplateCheck.setAdvice("审核通过");
        AssetInstallTemplateCheck assetInstallTemplateCheck2=new AssetInstallTemplateCheck();
        assetInstallTemplateCheck2.setResult(1);
        assetInstallTemplateCheck2.setId(1);
        assetInstallTemplateCheck2.setAdvice("审核通过");
        installTemplateCheckList.add(assetInstallTemplateCheck);
        installTemplateCheckList.add(assetInstallTemplateCheck2);
        when(assetInstallTemplateCheckDao.queryTemplateCheckByTemplateId(anyString())).thenReturn(installTemplateCheckList);
        SysUser sysUser = new SysUser();
        sysUser.setName("chen");
        // 1 user不为空的情况
        when(redisUtil.getObject(any(), any())).thenReturn(sysUser);

        assetInstallTemplateCheckDao.queryTemplateCheckByTemplateId(queryCondition.getPrimaryKey());
        List<AssetInstallTemplateCheckResponse> assetInstallTemplateCheckResponses = assetInstallTemplateCheckService.queryTemplateCheckByTemplateId(queryCondition);

        List<String> collect = assetInstallTemplateCheckResponses.stream().map(AssetInstallTemplateCheckResponse::getResultStr).collect(Collectors.toList());
        collect.forEach(k->
            Assert.assertEquals(AssetInstallTemplateCheckStautsEnum.SUBMIT_AUDIT.getMsg(),k));

        List<String> collect2 = assetInstallTemplateCheckResponses.stream().map(AssetInstallTemplateCheckResponse::getName).collect(Collectors.toList());
        collect2.forEach(k->
                Assert.assertEquals("chen",k));

        //2 user为空的情况   状态枚举类为空的情况
        when(redisUtil.getObject(any(), any())).thenReturn(null);
        assetInstallTemplateCheck.setResult(100);
        List<AssetInstallTemplateCheckResponse> assetInstallTemplateCheckResponses2 = assetInstallTemplateCheckService.queryTemplateCheckByTemplateId(queryCondition);
        List<String> collect3 = assetInstallTemplateCheckResponses2.stream().map(AssetInstallTemplateCheckResponse::getName).collect(Collectors.toList());
        collect3.forEach(k->
                Assert.assertEquals("",k));

    }
}