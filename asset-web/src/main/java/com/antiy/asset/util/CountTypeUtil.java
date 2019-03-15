package com.antiy.asset.util;

import com.antiy.asset.vo.response.AssetCountColumnarResponse;
import com.antiy.asset.vo.response.AssetCountResponse;
import com.antiy.asset.vo.response.EnumCountResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
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
            assetCountResponse.setMap(ArrayTypeUtil.objectArrayToEntryArray(result.entrySet().toArray()));
            return assetCountResponse;
        }
        return null;
    }

    public static List<EnumCountResponse> getEnumCountResponse(int maxNum, List<Map<String, Object>> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<EnumCountResponse> enumCountResponses = new ArrayList<>();
            // 处理返回结果，若结果条数大于maxNum，则归为其他
            setResultMap(maxNum, list, enumCountResponses);
            return enumCountResponses;
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
        assetCountResponse.setMap(ArrayTypeUtil.objectArrayToEntryArray(result.entrySet().toArray()));
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

    private static void setResultMap(int maxNum, List<Map<String, Object>> list, List<EnumCountResponse> result) {
        int i = 0;// 数据条数
        long sum = 0;// 其他种类数
        List<String> other = new ArrayList<>();
        for (Map<String, Object> map : list) {
            if (StringUtils.isNotEmpty((String) map.get("key"))) {
                if (i < maxNum) {
                    EnumCountResponse enumCountResponse = new EnumCountResponse((String) map.get("key"),
                        (String) map.get("key"), (long) map.get("value"));
                    i++;
                    result.add(enumCountResponse);
                } else {
                    sum = sum + (Long) map.get("value");
                    other.add((String) map.get("key"));
                }
            }
        }
        if (sum != 0) {
            EnumCountResponse otherCountResponse = new EnumCountResponse("其它", other, sum);
            result.add(otherCountResponse);
        }
    }

    /**
     * 设置数据格式为柱状图数据格式
     * @param result
     * @return
     */
    public static AssetCountColumnarResponse getAssetCountColumnarResponse(Map<String, Long> result) {
        AssetCountColumnarResponse assetCountColumnarResponse = new AssetCountColumnarResponse();
        assetCountColumnarResponse.setKeys(ArrayTypeUtil.objectArrayToStringArray(result.keySet().toArray()));
        assetCountColumnarResponse.setValues(ArrayTypeUtil.objectArrayToLongArray(result.values().toArray()));
        return assetCountColumnarResponse;
    }
}
