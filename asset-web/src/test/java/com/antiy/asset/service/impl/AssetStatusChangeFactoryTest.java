package com.antiy.asset.service.impl;

import com.antiy.common.utils.SpringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class AssetStatusChangeFactoryTest {
    @Test
    public void getAssetChangeRecordProcess() {

        try {
            Assert.assertNotNull(
                    AssetChangeRecordFactory.getAssetChangeRecordProcess(NetworkEquipmentFieldCompareImpl.class));
        } catch (Exception e) {
            Assert.assertEquals("获取实例失败", e.getMessage());
        }
        PowerMockito.mockStatic(SpringUtil.class);
        Mockito.when(SpringUtil.getBean(Mockito.anyString())).thenReturn(new NetworkEquipmentFieldCompareImpl());
        Assert.assertNotNull(
            AssetChangeRecordFactory.getAssetChangeRecordProcess(NetworkEquipmentFieldCompareImpl.class));
    }
}
