package com.antiy.asset.vo.request;

import java.io.*;
import java.util.List;

import javax.validation.Valid;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOuterRequest extends BasicRequest implements ObjectValidator, Serializable {

    /**
     * 资产主表信息
     */
    @Valid
    @ApiModelProperty(value = "资产主表信息")
    private AssetRequest                     asset;

    /**
     * mac
     */
    @ApiModelProperty(value = "mac")
    @Valid
    private List<AssetMacRelationRequest>    macRelationRequests;
    /**
     * ip
     */
    @ApiModelProperty(value = "ip/net")
    @Valid
    private List<AssetIpRelationRequest>     ipRelationRequests;

    /**
     * 组件
     */
    @ApiModelProperty(value = "组件")
    @Valid
    private List<AssetAssemblyRequest>       assemblyRequestList;

    /**
     * 网络设备
     */
    @ApiModelProperty(value = "网络设备")
    @Valid
    private AssetNetworkEquipmentRequest     networkEquipment;
    /**
     * 安全设备
     */
    @ApiModelProperty(value = "安全设备")
    @Valid
    private AssetSafetyEquipmentRequest      safetyEquipment;
    /**
     * 存储介质
     */
    @ApiModelProperty(value = "存储介质")
    @Valid
    private AssetStorageMediumRequest        assetStorageMedium;
    /**
     * 资产软件关系表
     */
    @ApiModelProperty(value = "资产软件关系表")
    @Valid
    private List<AssetSoftwareReportRequest> softwareReportRequestList;

    @ApiModelProperty(value = "启动流程数据")
    @Valid
    private ManualStartActivityRequest       manualStartActivityRequest;
    @ApiModelProperty(value = "处理流程数据")
    @Valid
    private ActivityHandleRequest            activityHandleRequest;
    /**
     * 其他设备
     */
    @ApiModelProperty(value = "其他设备")
    @Valid
    private AssetOthersRequest               assetOthersRequest;

    public ActivityHandleRequest getActivityHandleRequest() {
        return activityHandleRequest;
    }

    public void setActivityHandleRequest(ActivityHandleRequest activityHandleRequest) {
        this.activityHandleRequest = activityHandleRequest;
    }

    public ManualStartActivityRequest getManualStartActivityRequest() {
        return manualStartActivityRequest;
    }

    public void setManualStartActivityRequest(ManualStartActivityRequest manualStartActivityRequest) {
        this.manualStartActivityRequest = manualStartActivityRequest;
    }

    public AssetRequest getAsset() {
        return asset;
    }

    public void setAsset(AssetRequest asset) {
        this.asset = asset;
    }

    public AssetNetworkEquipmentRequest getNetworkEquipment() {
        return networkEquipment;
    }

    public void setNetworkEquipment(AssetNetworkEquipmentRequest networkEquipment) {
        this.networkEquipment = networkEquipment;
    }

    public AssetSafetyEquipmentRequest getSafetyEquipment() {
        return safetyEquipment;
    }

    public void setSafetyEquipment(AssetSafetyEquipmentRequest safetyEquipment) {
        this.safetyEquipment = safetyEquipment;
    }

    public AssetStorageMediumRequest getAssetStorageMedium() {
        return assetStorageMedium;
    }

    public void setAssetStorageMedium(AssetStorageMediumRequest assetStorageMedium) {
        this.assetStorageMedium = assetStorageMedium;
    }

    public List<AssetSoftwareReportRequest> getSoftwareReportRequestList() {
        return softwareReportRequestList;
    }

    public void setSoftwareReportRequestList(List<AssetSoftwareReportRequest> softwareReportRequestList) {
        this.softwareReportRequestList = softwareReportRequestList;
    }

    public AssetOthersRequest getAssetOthersRequest() {
        return assetOthersRequest;
    }

    public void setAssetOthersRequest(AssetOthersRequest assetOthersRequest) {
        this.assetOthersRequest = assetOthersRequest;
    }

    @Override
    public String toString() {
        return "AssetOuterRequest{" + "asset=" + asset + ", macRelationRequests=" + macRelationRequests
               + ", ipRelationRequests=" + ipRelationRequests + ", assemblyRequestList=" + assemblyRequestList
               + ", networkEquipment=" + networkEquipment + ", safetyEquipment=" + safetyEquipment
               + ", assetStorageMedium=" + assetStorageMedium + ", softwareReportRequestList="
               + softwareReportRequestList + ", manualStartActivityRequest=" + manualStartActivityRequest
               + ", activityHandleRequest=" + activityHandleRequest + ", assetOthersRequest=" + assetOthersRequest
               + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {
        // ParamterExceptionUtils.isTrue(!(manualStartActivityRequest == null && activityHandleRequest == null),
        // "流程数据不能为空");
    }

    @Override
    public Object clone() {
        AssetOuterRequest outer = null;
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (AssetOuterRequest) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return outer;
    }

    public List<AssetAssemblyRequest> getAssemblyRequestList() {
        return assemblyRequestList;
    }

    public void setAssemblyRequestList(List<AssetAssemblyRequest> assemblyRequestList) {
        this.assemblyRequestList = assemblyRequestList;
    }

    public List<AssetMacRelationRequest> getMacRelationRequests() {
        return macRelationRequests;
    }

    public void setMacRelationRequests(List<AssetMacRelationRequest> macRelationRequests) {
        this.macRelationRequests = macRelationRequests;
    }

    public List<AssetIpRelationRequest> getIpRelationRequests() {
        return ipRelationRequests;
    }

    public void setIpRelationRequests(List<AssetIpRelationRequest> ipRelationRequests) {
        this.ipRelationRequests = ipRelationRequests;
    }
}
