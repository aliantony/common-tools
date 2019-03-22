package com.antiy.asset.intergration;

import com.antiy.asset.vo.query.ActivityCodeAndAreaIdQuery;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.SysUser;

import java.util.List;

/**
 * 区域查询接口
 * @author zhangyajun
 * @create 2019-02-21 15:47
 **/
public interface AreaClient {
    ActionResponse queryAreaById(String id);
    List<SysUser> queryCdeAndAreaId(ActivityCodeAndAreaIdQuery codeAndAreaIdQuery);

    Object getInvokeResult(String id);
}
