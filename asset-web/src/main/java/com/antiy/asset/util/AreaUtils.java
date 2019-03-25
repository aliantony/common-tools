package com.antiy.asset.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.antiy.common.base.SysArea;
import com.antiy.common.utils.LoginUserUtil;

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
    private List<Integer> getNextArea(Integer areaId) {
        List<SysArea> childAreaList = null;
        if (null != areaId) {
            childAreaList = LoginUserUtil.getLoginUser().getAreas().stream()
                .filter(area -> area.getParentId().equals(areaId + "")).collect(Collectors.toList());
        }
        childAreaList = LoginUserUtil.getLoginUser().getAreas();

        if (CollectionUtils.isEmpty(childAreaList)) {
            return childAreaList.stream().map(SysArea::getId).collect(Collectors.toList());
        }

        ArrayList result = new ArrayList<Integer>();
        result.add(areaId);

        return result;
    }
}
