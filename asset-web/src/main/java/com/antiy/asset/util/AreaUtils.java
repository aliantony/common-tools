package com.antiy.asset.util;

import com.antiy.common.base.SysArea;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: zhangbing
 * @date: 2019/3/25 15:58
 * @description: 区域工具类
 */
public class AreaUtils {

    /**
     * 获取当前节点的第一级子节点
     * @param areaId
     * @return 如果返回null，则说明不存在下级节点
     */
    private static List<String> getNextArea(String areaId) {
        List<SysArea> childAreaList = LoginUserUtil.getLoginUser().getAreas();;
        if (null != areaId) {
            childAreaList = LoginUserUtil.getLoginUser().getAreas().stream()
                .filter(area -> area.getParentId().equals(areaId)).collect(Collectors.toList());
        }

        if (CollectionUtils.isNotEmpty(childAreaList)) {
            return childAreaList.stream().map(e->e.getId()).collect(Collectors.toList());
        }

        ArrayList result = new ArrayList<Integer>();
        result.add(areaId);

        return result;
    }

    /**
     * 获取某节点所有下级节点，包括该节点本身
     * @param areaId
     * @return
     */
    public static List<String> getWholeNextArea(String areaId) {
        Objects.requireNonNull(areaId,"区域id不能为空");
        List<String> areaIds =getNextArea(areaId);
        if (areaIds.contains(areaId)){
            return areaIds;
        }
        for (int i = 0; i < areaIds.size(); i++) {
            List<String> nextAreaIds = getNextArea(areaIds.get(i));
            if (!nextAreaIds.contains(areaIds.get(i))){
                areaIds.addAll(nextAreaIds);
            }
        }
        //添加自身节点
        areaIds.add(areaId);
        return areaIds;
    }
}
