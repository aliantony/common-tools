package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysArea;
import com.antiy.common.base.SysUser;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
public class OtherEquipmentFieldCompareImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private OtherEquipmentFieldCompareImpl compare;

    /**
     * 模拟dao层和redis从数据库查询
     */
    @Mock
    private RedisUtil redisUtil;

    @Mock
    private AssetChangeRecordDao assetChangeRecordDao;

    /**
     * 执行转换的具体逻辑
     */
    @Spy
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

    /**
     * 网络设备字段对比测试
     * 情景1：传入设备id，从数据库读取的信息大于2，信息内容不同，从redis读取信息，信息内容不同，返回不同部分的信息列表
     * 断言列表不为空
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfo() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetOthersRequest\":{\"serial\":\"123\"}}");
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"171\", \"serial\":\"233\"}, \"assetOthersRequest\":{\"serial\":\"321\"}}");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(null);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        Assert.assertThat(list, Matchers.notNullValue());
    }

    /**
     * 网络设备字段对比测试
     * 情景2：传入设备id，从数据库读取的信息为空，返回空信息列表
     * 断言列表大小为0
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfoNull() throws Exception {
        List<String> strings = new ArrayList<>();
        SysUser sysUser = new SysUser();
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        Assert.assertThat(list.size(), Matchers.equalTo(0));
    }

    /**
     * 网络设备字段对比测试
     * 情景3：传入设备id，从数据库读取的信息大小为1，返回空信息列表
     * 断言列表大小为0
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfoLessSize() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetOthersRequest\":{\"serial\":\"123\"}}");
        SysUser sysUser = new SysUser();
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        Assert.assertThat(list.size(), Matchers.equalTo(0));
    }

    /**
     * 网络设备字段对比测试
     * 情景4：传入设备id，从数据库读取的信息大于2，信息内容不同，SysUser不为空，从redis读取信息，信息内容不同，返回不同部分的信息列表
     * 断言列表不为空
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfoElse() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetOthersRequest\":{\"serial\":\"123\"}}");
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"171\", \"serial\":\"233\"}, \"assetOthersRequest\":{\"serial\":\"321\"}}");
        SysUser sysUser = new SysUser();
        sysUser.setName("Leo");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        Assert.assertThat(list, Matchers.notNullValue());
    }

    /**
     * 设备字段对比测试
     * 情景1：返回为空
     * 断言列表为空
     */
    @Test
    public void compareComponentInfo() {
        List<? extends BaseRequest> list = compare.compareComponentInfo();
        Assert.assertThat(list, Matchers.nullValue());
    }
}