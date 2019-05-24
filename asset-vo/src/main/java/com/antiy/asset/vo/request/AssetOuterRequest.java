package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.compress.utils.Lists;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
     * 主板
     */
    @ApiModelProperty(value = "主板")
    @Valid
    private List<AssetMainboradRequest>      mainboard;

    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    @Valid
    private List<AssetMemoryRequest>         memory;

    /**
     * 硬盘
     */
    @ApiModelProperty(value = "硬盘")
    @Valid
    private List<AssetHardDiskRequest>       hardDisk;

    /**
     * cpu
     */
    @ApiModelProperty(value = "cpu")
    @Valid
    private List<AssetCpuRequest>            cpu;
    /**
     * 网卡
     */
    @ApiModelProperty(value = "网卡")
    @Valid
    private List<AssetNetworkCardRequest>    networkCard;
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

    public List<AssetMainboradRequest> getMainboard() {
        return mainboard;
    }

    public void setMainboard(List<AssetMainboradRequest> mainboard) {

        this.mainboard = mainboard;
    }

    public List<AssetMemoryRequest> getMemory() {
        return memory;
    }

    public void setMemory(List<AssetMemoryRequest> memory) {

        this.memory = memory;
    }

    public List<AssetHardDiskRequest> getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(List<AssetHardDiskRequest> hardDisk) {

        this.hardDisk = hardDisk;
    }

    public List<AssetCpuRequest> getCpu() {
        return cpu;
    }

    public void setCpu(List<AssetCpuRequest> cpu) {

        this.cpu = cpu;
    }

    public List<AssetNetworkCardRequest> getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(List<AssetNetworkCardRequest> networkCard) {

        this.networkCard = networkCard;
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
        return "AssetOuterRequest{" + "asset=" + asset + ", mainboard=" + mainboard + ", memory=" + memory
               + ", hardDisk=" + hardDisk + ", cpu=" + cpu + ", networkCard=" + networkCard + ", networkEquipment="
               + networkEquipment + ", safetyEquipment=" + safetyEquipment + ", assetStorageMedium="
               + assetStorageMedium + ", softwareReportRequestList=" + softwareReportRequestList
               + ", manualStartActivityRequest=" + manualStartActivityRequest + ", activityHandleRequest="
               + activityHandleRequest + ", assetOthersRequest=" + assetOthersRequest + '}';
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
}
