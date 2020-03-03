package com.antiy.asset.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.antiy.asset.base.AreaBase;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.util.StatusEnumUtil;

/**
 * @author zhangyajun
 * @create 2020-03-02 13:21
 **/
@Component
public class ConditionFactory {
    public static <T extends AreaBase> T createCondition(Class<T> c) throws Exception {
        T subBase = null;
        subBase = (T) Class.forName(c.getName()).newInstance();
        List<String> areaList = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
        List<Integer> statusList = StatusEnumUtil.getAssetTypeStatus();
        subBase.setAreaList(areaList);
        subBase.setStatusList(statusList);
        return subBase;
    }
}
