package com.antiy.asset.intergration;

import com.antiy.asset.vo.response.BaselineCategoryModelNodeResponse;
import com.antiy.common.base.ActionResponse;

import java.util.List;
import java.util.Map;

/**
 * 操作系统查询接口
 * @author zhangyajun
 * @create 2019-02-21 15:47
 **/
public interface OperatingSystemClient {
    ActionResponse getOperatingSystem();

    List<Map> getInvokeOperatingSystem();

    ActionResponse getOperatingSystemTree();

    List<Map> getInvokeOperatingSystemTree();
}
