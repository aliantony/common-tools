package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysArea;
import com.antiy.common.base.SysUser;
import org.junit.Ignore;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class StorageMediumFieldCompareImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private StorageMediumFieldCompareImpl compare;

    /**
     * 模拟数据库访问类
     */
    @Mock
    private RedisUtil redisUtil;

    @Mock
    private AssetChangeRecordDao assetChangeRecordDao;

    /**
     * 执行具体逻辑
     */
    @Spy
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

    /**
     * 比较公共信息测试
     * 情景1：传入设备id，从数据库读取的信息大于2，信息内容不同，从redis读取信息，信息内容不同，返回不同部分的信息列表
     * 断言列表不为空
     *
     * @throws Exception except
     */
    @Test
    @Ignore
    public void compareCommonBusinessInfo() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetStorageMedium\":{\"maximumStorage\":\"123\", \"diskNumber\":\"123\"}}");
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"001\", \"name\":\"Joe\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetStorageMedium\":{\"maximumStorage\":\"321\", \"diskNumber\":\"321\"}}");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(null);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        assertThat(list, notNullValue());
    }

    /**
     * 比较公共信息测试
     * 情景2：传入设备id，从数据库读取的信息为空，信息内容不同，SysUser不为空，从redis读取信息，信息内容不同，返回空信息列表
     * 断言列表为空
     *
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfoNull() throws Exception {
        List<String> strings = new ArrayList<>();
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        SysUser sysUser = new SysUser();
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 比较公共信息测试
     * 情景2：传入设备id，从数据库读取的信息等于1，信息内容不同，SysUser不为空，从redis读取信息，信息内容不同，返回空信息列表
     * 断言列表为空
     *
     * @throws Exception except
     */
    @Test
    public void compareCommonBusinessInfoLessSize() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetStorageMedium\":{\"maximumStorage\":\"123\", \"diskNumber\":\"123\"}}");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        SysUser sysUser = new SysUser();
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 比较公共信息测试
     * 情景1：传入设备id，从数据库读取的信息大于2，信息内容不同，SysUser不为空，从redis读取信息，信息内容不同，返回不同部分的信息列表
     * 断言列表不为空
     *
     * @throws Exception except
     */
    @Test
    @Ignore
    public void compareCommonBusinessInfoElse() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"110\", \"name\":\"Leo\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetStorageMedium\":{\"maximumStorage\":\"123\", \"diskNumber\":\"123\"}}");
        strings.add("{\"asset\": {\"areaId\":\"2333\", \"responsibleUserId\":\"001\", \"name\":\"Joe\", \"manufacturer\":\"110\", \"serial\":\"110\"}, \"assetStorageMedium\":{\"maximumStorage\":\"321\", \"diskNumber\":\"321\"}}");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Leo");
        SysUser sysUser = new SysUser();
        sysUser.setName("Leo");
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(strings);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysArea.class))).thenReturn(sysArea);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.eq(SysUser.class))).thenReturn(sysUser);

        List<Map<String, Object>> list = compare.compareCommonBusinessInfo(2333);
        assertThat(list, notNullValue());
    }

    /**
     * 比较成员信息测试
     * 返回空
     * 断言返回为空
     */
    @Test
    public void compareComponentInfo() {
        List<? extends BaseRequest> list = compare.compareComponentInfo();
        assertThat(list, nullValue());
    }
}