package com.antiy.asset.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.common.utils.LicenseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.response.BaselineCategoryModelResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;
import org.springframework.web.context.request.RequestContextHolder;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * <p> 资产软件关系信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class})
//@SpringBootTest
public class AssetSoftwareRelationServiceImplTest {
    @Mock
    private AssetSoftwareRelationDao                                            assetSoftwareRelationDao;
    @Mock
    private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation>  requestConverter;
    @Mock
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationResponse> responseConverter;
    @Mock
    private BaseConverter<AssetSoftware, AssetSoftwareResponse>                 responseSoftConverter;
    @Mock
    private BaseConverter<AssetSoftwareInstall, AssetSoftwareInstallResponse>   responseInstallConverter;
    @Mock
    private TransactionTemplate                                                 transactionTemplate;
    @Mock
    private IRedisService                                                       redisService;
    @Mock
    private AssetSoftwareDao                                                    assetSoftwareDao;
    @Mock
    private AssetDao                                                            assetDao;
    @Mock
    private CommandClientImpl                                                   commandClient;
    @Mock
    private AssetSoftwareRelationServiceImpl softwareRelationService;

    private void setOperationName(AssetSoftwareRelationResponse assetSoftwareRelationResponse) throws Exception {
        if (StringUtils.isNotEmpty(assetSoftwareRelationResponse.getOperationSystem())) {
            String[] ops = assetSoftwareRelationResponse.getOperationSystem().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            List<BaselineCategoryModelResponse> categoryOsResponseList = redisService.getAllSystemOs();
            for (String os : ops) {
                for (BaselineCategoryModelResponse categoryModelResponse : categoryOsResponseList) {
                    if (os.equals(categoryModelResponse.getStringId())) {
                        stringBuilder.append(categoryModelResponse.getName()).append(",");
                    }
                }
            }
            assetSoftwareRelationResponse.setOperationSystemName(
                stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(",")));
        }
    }

    @Test
    public void countAssetBySoftId() {
        Mockito.when(assetSoftwareRelationDao.countAssetBySoftId(Mockito.anyInt())).thenReturn(0);
    }

    @Test
    public void findOS() throws Exception {
        Mockito.when(assetSoftwareRelationDao.findOS(Mockito.any())).thenReturn(Arrays.asList(new String()));
    }

    @Test
    public void queryInstalledList() throws Exception {
        QueryCondition query = new QueryCondition();
        query.setPrimaryKey("1");
        // 查询资产已关联的软件列表
        List<AssetSoftwareInstallResponse> responses = assetSoftwareRelationDao.queryInstalledList(query);
        Assert.assertNull(responses);
    }

    @Test
    public void queryInstallableList() {
        InstallQuery installQuery = mock(InstallQuery.class);
        when(installQuery.getIsBatch()).thenReturn(false);
        when(assetSoftwareRelationDao.queryNameListType(installQuery)).thenReturn(2);
        softwareRelationService.queryInstallableList(new InstallQuery());
    }

    @Test
    public void batchRelation() {

    }

    private Integer countByAssetId(Integer assetId) {
        return assetSoftwareRelationDao.countSoftwareByAssetId(assetId);
    }

}
