package com.antiy.asset.util;

import com.antiy.asset.vo.response.AssetCountColumnarResponse;
import com.antiy.asset.vo.response.AssetCountResponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计部分的数据格式转换类
 */
public class CountTypeUtil {
    /**
     * 设置数据格式为饼状图数据格式,超过maxNum的归为其他
     * @param
     * @return AssetCountResponse
     */
    public static AssetCountResponse getAssetCountResponse(int maxNum, List<Map<String, Long>> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            Map result = new HashMap();
            // 处理返回结果，若结果条数大于maxNum，则归为其他
            setResultMap(maxNum, list, result);
            AssetCountResponse assetCountResponse = new AssetCountResponse();
            assetCountResponse.setMap(ArrayTypeUtil.ObjectArrayToEntryArray(result.entrySet().toArray()));
            return assetCountResponse;
        }
        return null;
    }
    /**
     * 设置数据格式为饼状图数据格式,超过maxNum的归为其他
     * @param
     * @return AssetCountResponse
     */
    public static AssetCountResponse getAssetCountResponse(Map<String, Long> result) {
        AssetCountResponse assetCountResponse = new AssetCountResponse();
        assetCountResponse.setMap(ArrayTypeUtil.ObjectArrayToEntryArray(result.entrySet().toArray()));
        return assetCountResponse;
    }

    private static void setResultMap(int maxNum, List<Map<String, Long>> list, Map result) {
        int i = 0;// 数据条数
        long sum = 0;// 其他种类数
        for (Map map : list) {
            if (i < maxNum) {
                result.put(map.get("key"), map.get("value"));
                i++;
            } else {
                sum = sum + (Long) map.get("value");
            }
        }
        if (sum != 0) {
            result.put("其他", sum);
        }
    }

    /**
     * 设置数据格式为柱状图数据格式
     * @param result
     * @return
     */
    public static AssetCountColumnarResponse getAssetCountColumnarResponse(Map<String, Long> result) {
        AssetCountColumnarResponse assetCountColumnarResponse = new AssetCountColumnarResponse();
        assetCountColumnarResponse.setKeys(ArrayTypeUtil.ObjectArrayToStringArray(result.keySet().toArray()));
        assetCountColumnarResponse.setValues(ArrayTypeUtil.ObjectArrayToLongArray(result.values().toArray()));
        return assetCountColumnarResponse;
    }
}
