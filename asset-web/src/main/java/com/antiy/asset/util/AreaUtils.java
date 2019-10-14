package com.antiy.asset.util;

import com.antiy.common.base.SysArea;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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
    private List<String> getNextArea(Integer areaId) {
        List<SysArea> childAreaList = LoginUserUtil.getLoginUser().getAreas();;
        if (null != areaId) {
            childAreaList = LoginUserUtil.getLoginUser().getAreas().stream()
                .filter(area -> area.getParentId().equals(areaId + "")).collect(Collectors.toList());
        }

        if (CollectionUtils.isNotEmpty(childAreaList)) {
            return childAreaList.stream().map(e->DataTypeUtils.integerToString(e.getId())).collect(Collectors.toList());
        }

        ArrayList result = new ArrayList<Integer>();
        result.add(areaId);

        return result;
    }
}
