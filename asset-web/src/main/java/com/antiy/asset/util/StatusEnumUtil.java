package com.antiy.asset.util;

import java.util.ArrayList;
import java.util.List;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.google.common.collect.Lists;

/**
 * 状态枚举的工具类，可以获取特定的状态列表
 */
public class StatusEnumUtil {
    /**
     * 获取软件未退役的状态code list
     * @return
     */
    public static List getSoftwareNotRetireStatusList() {
        List<Integer> status = new ArrayList();
        SoftwareStatusEnum[] softwareStatusEnums = SoftwareStatusEnum.values();
        for (int i = 0; i < softwareStatusEnums.length; i++) {
            if (!SoftwareStatusEnum.RETIRE.equals(softwareStatusEnums[i])) {
                status.add(softwareStatusEnums[i].getCode());
            }
        }
        return status;
    }

    /**
     * 获取硬件未退役的状态code list
     * @return
     */
    public static List<Integer> getAssetNotRetireStatus() {
        List<Integer> status = new ArrayList<>();
        AssetStatusEnum[] assetStatusEnums = AssetStatusEnum.values();
        for (int i = 0; i < assetStatusEnums.length; i++) {
            if (!assetStatusEnums[i].equals(AssetStatusEnum.RETIRE)) {
                status.add(assetStatusEnums[i].getCode());
            }
        }
        return status;
    }

    /**
     * 获取硬件未退役的状态code list
     * @return
     */
    public static List<Integer> getAssetUseableStatus() {
        List<Integer> status = new ArrayList<>();
        AssetStatusEnum[] assetStatusEnums = AssetStatusEnum.values();
        for (AssetStatusEnum assetStatusEnum : assetStatusEnums) {
            if (!assetStatusEnum.equals(AssetStatusEnum.RETIRE)
                && !assetStatusEnum.equals(AssetStatusEnum.NOT_REGISTER)) {
                status.add(assetStatusEnum.getCode());
            }
        }
        return status;
    }

    /**
     * 已入网+变更中+退役待审批+退役审批未通过的状态资产类型分布情况
     * @return
     */
    public static List<Integer> getAssetTypeStatus() {
        List<Integer> status = Lists.newArrayList(AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.IN_CHANGE.getCode(),
            AssetStatusEnum.CORRECTING.getCode(), AssetStatusEnum.NET_IN_CHECK.getCode(),
            AssetStatusEnum.WAIT_SCRAP.getCode(), AssetStatusEnum.SCRAP.getCode(),
                AssetStatusEnum.RETIRE.getCode(), AssetStatusEnum.WAIT_RETIRE.getCode());
        return status;
    }

}
