package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractChangeRecordCompareImplTest {
    @InjectMocks
    private AbstractChangeRecordCompareImpl abstractChangeRecordCompareService =
            Mockito.mock(AbstractChangeRecordCompareImpl.class,Mockito.CALLS_REAL_METHODS);
    @Mock
    private AssetChangeRecordDao assetChangeRecordDao;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTwoRecentChangeValTest(){
        List<String> expected = new ArrayList<>();
        expected.add("{json串}");
        when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(expected);
        List<String> actual = abstractChangeRecordCompareService.getTwoRecentChangeVal(1,1);
        Assert.assertTrue(expected.size() == actual.size());
    }
}
