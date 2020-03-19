package com.antiy.asset.factory;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.antiy.asset.base.AreaBase;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.util.StatusEnumUtil;
import com.antiy.asset.vo.query.AssetBaseQuery;

/**
 * @author zhangyajun
 * @create 2020-03-02 13:21
 **/
@Component
public class ConditionFactory {
    public static <T extends AreaBase> T createCondition(Class<T> c) throws Exception {
        T subBase;
        subBase = (T) Class.forName(c.getName()).newInstance();
        List<String> areaList = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
        List<Integer> statusList = StatusEnumUtil.getAssetTypeStatus();
        subBase.setAreaList(areaList);
        subBase.setStatusList(statusList);
        return subBase;
    }

    public static AssetBaseQuery createAreaQuery(AssetBaseQuery query) throws Exception {
        if (CollectionUtils.isEmpty(query.getAreaList())){
            List<String> areaList = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
            query.setAreaList(areaList);
        }
        return query;
    }
}
