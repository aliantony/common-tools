package com.antiy.asset.service.impl;

import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.enums.AssetTypeEnum;
import com.antiy.asset.vo.query.AssetIDAndSchemeTypeQuery;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.PageResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchemeServiceImplTest {

    @MockBean
    private SchemeDao schemeDao;


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @SpyBean
    private ISchemeService iSchemeService;

    @Before
    public void login(){
        LoginUtil.generateDefaultLoginUser();
    }
    @Test
    public void saveScheme() throws Exception {
        AssetStatusReqeust assetStatusReqeust=new AssetStatusReqeust ();

        String result = iSchemeService.saveScheme(assetStatusReqeust);
        Assert.assertEquals(null,result);
    }

    @Test
    public void updateScheme() throws Exception {
        SchemeRequest request=new SchemeRequest();
        Integer result = iSchemeService.updateScheme(request);
        Assert.assertEquals(null,result);

    }

    @Test
    public void findListScheme() throws Exception {
        SchemeQuery query=new SchemeQuery();
        List<Scheme> list=new ArrayList<>();
        when(schemeDao.findQuery(query)).thenReturn(list);
        List<SchemeResponse> listScheme = iSchemeService.findListScheme(query);
        Assert.assertEquals(0,listScheme.size());

    }

    @Test
    public void findPageScheme() throws Exception {
        SchemeQuery query=new SchemeQuery();
        when( schemeDao.findCount(query)).thenReturn(100);

        List<Scheme> list=new ArrayList<>();
        when(schemeDao.findQuery(query)).thenReturn(list);
        PageResult<SchemeResponse> pageScheme = iSchemeService.findPageScheme(query);
        Assert.assertEquals(10,pageScheme.getTotalPages());

    }

    @Test
    public void findSchemeByAssetIdAndType() throws Exception {
        AssetIDAndSchemeTypeQuery query=new AssetIDAndSchemeTypeQuery ();
        Scheme scheme=new Scheme();
        scheme.setAssetId("111");
        when(schemeDao.findSchemeByAssetIdAndType(query)).thenReturn(scheme);
        SchemeResponse schemeByAssetIdAndType = iSchemeService.findSchemeByAssetIdAndType(query);
        Assert.assertEquals("111",schemeByAssetIdAndType.getAssetId());
    }

    @Test
    public void queryMemoById() {
        Scheme scheme=new Scheme();
        SchemeQuery query=new SchemeQuery();
        query.setAssetId("11");
        query.setAssetStatus(1);
        query.setAssetTypeEnum(AssetTypeEnum.HARDWARE);
        when(schemeDao.findMemoById(query)).thenReturn(scheme);
        SchemeResponse schemeResponse = iSchemeService.queryMemoById(query);
        Assert.assertNotNull(schemeResponse);


    }
}