package com.antiy.asset.intergration;

import java.util.LinkedHashMap;
import java.util.List;

import com.antiy.asset.vo.response.BaselineCategoryModelNodeResponse;
import com.antiy.asset.vo.response.BaselineCategoryModelResponse;
import com.antiy.common.base.ActionResponse;

/**
 * 操作系统查询接口
 * @author zhangyajun
 * @create 2019-02-21 15:47
 **/
public interface OperatingSystemClient {
    ActionResponse<List<BaselineCategoryModelResponse>> getOperatingSystem();

    List<BaselineCategoryModelResponse> getInvokeOperatingSystem();

    ActionResponse getOperatingSystemTree();

    List<BaselineCategoryModelNodeResponse> getInvokeOperatingSystemTree();
}
