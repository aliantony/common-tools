package com.antiy.asset.intergration;

import com.antiy.asset.vo.user.UserStatus;
import com.antiy.common.base.ActionResponse;

import java.util.HashMap;
import java.util.List;

/**
 * 区域查询接口
 * @author zhangyajun
 * @create 2019-02-21 15:47
 **/
public interface SysUserClient {

    ActionResponse queryUserRoleById(String id);

    Object getInvokeResult(String id);

    UserStatus getLoginUserInfo(String userName);
    ActionResponse<List<HashMap<String,String>>> getUsersOfHaveRight(List<String> tag);
}
