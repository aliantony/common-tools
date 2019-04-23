package com.antiy.asset.service.impl;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.SpringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class})
public class AssetChangeRecordFactoryTest {

    @Test
    public void getAssetChangeRecordProcessTest1() throws Exception {
        ComputerEquipmentFieldCompareImpl expect = new ComputerEquipmentFieldCompareImpl();
        PowerMockito.mockStatic(SpringUtil.class);
        PowerMockito.when(SpringUtil.class, "getBean", Mockito.anyString()).thenReturn(expect);
        Assert.assertEquals(expect, AssetChangeRecordFactory.getAssetChangeRecordProcess(ComputerEquipmentFieldCompareImpl.class));
    }

    @Test(expected = BusinessException.class)
    public void getAssetChangeRecordProcessTest2() {
        AssetChangeRecordFactory.getAssetChangeRecordProcess(ComputerEquipmentFieldCompareImpl.class);
    }

}
