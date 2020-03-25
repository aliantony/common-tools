package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.AssetApplication;
import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AssetBusinessServiceImplTest {

    @Resource
    private IAssetBusinessService iAssetBusinessService;
   // @Test
    public void saveAssetBusiness() throws Exception {

        String json="{\"name\":\"这是一个测试324\",\"description\":\"这是一个描述311\",\"importance\":2,\"assetRelaList\":[{\"assetId\":\"659\",\"businessInfluence\":1}]}";
        AssetBusinessRequest request=new AssetBusinessRequest();
        AssetBusinessRequest assetBusinessRequest = JSON.parseObject(json, AssetBusinessRequest.class);
        iAssetBusinessService.saveAssetBusiness(assetBusinessRequest);

    }

    @Test
    public void updateAssetBusiness() throws Exception {
        String json="{\"id\":12,\"uniqueId\":\"686959312098033664\",\"name\":\"这是一个测试最新324\",\"description\":\"这个描述35458最新\",\"importance\":2,\"assetRelaList\":[{\"assetId\":\"658\",\"businessInfluence\":1},{\"assetId\":\"657\",\"businessInfluence\":2}]}";
        AssetBusinessRequest request=new AssetBusinessRequest();
        AssetBusinessRequest assetBusinessRequest = JSON.parseObject(json, AssetBusinessRequest.class);
        iAssetBusinessService.updateAssetBusiness(assetBusinessRequest);
    }
    @Test
    public void queryListAssetBusiness() {

    }

    @Test
    public void queryPageAssetBusiness() {
    }

    @Test
    public void queryAssetBusinessById() {
    }

    @Test
    public void deleteAssetBusinessById() {
    }

    @Test
    public void queryAsset() throws Exception {
        AssetAddOfBusinessQuery assetAddOfBusinessQuery=new AssetAddOfBusinessQuery();
        iAssetBusinessService.queryAsset(assetAddOfBusinessQuery);
    }

    @Test
    public void queryAssetByBusinessId() throws Exception {
        AssetAddOfBusinessQuery assetAddOfBusinessQuery=new AssetAddOfBusinessQuery();
        assetAddOfBusinessQuery.setUniqueId("686959312098033664");
        List<AssetBusinessRelationResponse> assetBusinessRelationResponses = iAssetBusinessService.queryAssetByBusinessId(assetAddOfBusinessQuery);
        System.out.println(JSON.toJSONString(assetBusinessRelationResponses));
    }

    @Test
    public void getByUniqueId() {
        AssetBusinessResponse byUniqueId = iAssetBusinessService.getByUniqueId("2dfgfgedwdwwd");
        System.out.println(JSON.toJSONString(byUniqueId));
    }

    @Test
    public void updateStatusByUniqueId() {
        Integer integer = iAssetBusinessService.updateStatusByUniqueId(Arrays.asList("692011214217150464"));
        System.out.println(integer);
    }
}