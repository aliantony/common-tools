package com.antiy.asset.util;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;

import java.util.ArrayList;
import java.util.List;

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
}
