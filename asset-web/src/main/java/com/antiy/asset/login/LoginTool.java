package com.antiy.asset.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.antiy.common.base.LoginUser;
import com.antiy.common.base.SysArea;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;

/**
 * @author zhangyajun
 * @create 2020-03-03 11:09
 **/
@Component
public class LoginTool {
    @Value("${login.user.debug}")
    private static boolean enable;

    public static LoginUser getLoginUser() {
        if (!enable) {
            LoginUser loginUser = LoginUserUtil.getLoginUser();
            if (loginUser == null) {
                throw new BusinessException("用户获取失败");
            } else {
                return loginUser;
            }
        } else {
            // 设置debug
            LoginUser loginUser = new LoginUser();
            SysArea sysArea = new SysArea();
            sysArea.setId("001001001001");
            loginUser.setId(1);
            loginUser.setAreaId("001001001001");
            loginUser.setAreas(Lists.newArrayList(sysArea));
            return loginUser;
        }
    }
}
