package com.antiy.asset.manage.controller;

import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.*;
import org.springframework.stereotype.Component;

/**
 * @author zhouye
 * @date 2019-04-10
 * 提供Softwore Controller用到的相关vo初始化
 * 注：业务上关心的字段传参，不关心的字段给默认值。
 */
@Component
public class SoftwareControllerManager {
    /**
     * @param name 软件名称
     * @return 软件信息vo对象
     */
    public AssetSoftwareRequest initSoftwareRequest(String name) {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        ActivityHandleRequest handleRequest = new ActivityHandleRequest();

        request.setSoftwareLicenseRequest(new AssetSoftwareLicenseRequest());
        request.setAssetPortProtocolRequest(new AssetPortProtocolRequest());
        handleRequest.setTaskId("1");
        //activityRequest.setFormData("123");
        activityRequest.setBusinessId("1");
        activityRequest.setProcessDefinitionKey("2");
        request.setName(name);
        // request.setOperationSystem("WINDOWS7-32-64");
        request.setCategoryModel("1");
        request.setUploadSoftwareName("file1");
        request.setPath("url");
        request.setVersion("1");
        request.setMd5Code("12456464");
        request.setManufacturer("123456");
        request.setDescription("135");
        request.setSerial("123");

        return request;
    }

    /**
     * @param name 软件名称
     * @return 软件信息vo对象
     */
    public AssetSoftwareRequest initUpdateSoftwareRequest(String id, String name) {
        AssetSoftwareRequest request = initSoftwareRequest(name);
        request.setId(id);
        return request;
    }

    /**
     * 初始化软件查询vo对象
     *
     * @return 软件查询vo对象
     */
    public AssetSoftwareQuery initSoftwareQuery() {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        return query;
    }

    /**
     * 初始化资产配置vo对象
     *
     * @return 配置vo对象
     */
    public ConfigRegisterRequest initRegisterRequest() {
        return new ConfigRegisterRequest();
    }

    /**
     * @param areaId  区域id
     * @param userIds 用户列表
     * @return 资产导入信息vo
     */
    public AssetImportRequest initImportRequest(String areaId, String[] userIds) {
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId(areaId);
        importRequest.setUserId(userIds);
        importRequest.setMemo("test");
        return importRequest;
    }
}
