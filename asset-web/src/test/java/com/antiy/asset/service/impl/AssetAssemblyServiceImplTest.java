package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetAssemblyDao;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetAssemblyServiceImplTest {

    @Autowired
    private IAssetAssemblyService assetAssemblyService;

    @Mock
    private AssetAssemblyDao assetAssemblyDao;

    @Test
    public void queryPageAssetAssembly() throws Exception {
        AssetAssemblyQuery query = new AssetAssemblyQuery();
        query.setPageSize(10);
        query.setCurrentPage(1);
        List<String> excludeIds = Lists.newArrayList("1", "2", "3");
        query.setExcludeAssemblyIds(excludeIds);
        List<AssetAssembly> result = new ArrayList<>();
        Mockito.when(assetAssemblyDao.findCount(query)).thenReturn(20);
        Mockito.when(assetAssemblyDao.findQuery(query)).thenReturn(result);
        assetAssemblyService.queryPageAssetAssembly(query);
    }
}