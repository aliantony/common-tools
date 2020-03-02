package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetHomePageDao;
import com.antiy.asset.entity.AssetImportanceDegreeCondition;
import com.antiy.asset.entity.AssetIncludeManageCondition;
import com.antiy.asset.entity.AssetOnlineChartCondition;
import com.antiy.asset.factory.ConditionFactory;
import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.util.TwelveTimeUtil;
import com.antiy.asset.vo.enums.AssetImportanceDegreeEnum;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;

/**
 * @author zhangyajun
 * @create 2020-02-28 11:12
 **/
@Service
public class IHomePageServiceImpl implements IHomePageService {

    @Resource
    private AssetHomePageDao homePageDao;

    @Override
    public AssetCountIncludeResponse countIncludeManage() throws Exception {
        AssetIncludeManageCondition condition = ConditionFactory.createCondition(AssetIncludeManageCondition.class);
        Integer include = homePageDao.countIncludeManage(condition);
        Integer uninclude = homePageDao.countUnincludeManage(condition);
        AssetCountIncludeResponse includeResponse = new AssetCountIncludeResponse();
        includeResponse.setIncludeAmount(include);
        includeResponse.setUnIncludeAmount(uninclude);
        return includeResponse;
    }

    @Override
    public AssetOnlineChartResponse assetOnlineChart() throws Exception {

        AssetOnlineChartResponse onlineChartResponse = new AssetOnlineChartResponse();
        // 初始化12时辰
        List<String> abscissa = TwelveTimeUtil.getAbscissa();
        List<List<Long>> timeDuration = TwelveTimeUtil.getTwelveTime();
        NameValueVo<NameValue> nameValueVo = new NameValueVo<>();
        List<NameValue> nameValueList = new ArrayList<>();
        for (String point : abscissa) {
            NameValue nameValue = new NameValue();
            nameValue.setName(point);
            nameValueList.add(nameValue);
        }
        int n = 0;

        AssetOnlineChartCondition condition = ConditionFactory.createCondition(AssetOnlineChartCondition.class);
        for (List<Long> duration : timeDuration) {
            condition.setStartTime(duration.get(0));
            condition.setEndTime(duration.get(1));
            Integer num = homePageDao.assetOnlineChart(condition);

            if (n <= nameValueList.size()) {
                nameValueList.get(n).setValue(num);
            }
            n++;
        }
        nameValueVo.setName("资产在线情况");
        nameValueVo.setData(nameValueList);

        onlineChartResponse.setxData(abscissa);
        onlineChartResponse.setCoordinate(nameValueVo);
        return onlineChartResponse;
    }

    @Override
    public List<EnumCountResponse> assetImportanceDegreePie() throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        AssetImportanceDegreeCondition condition = ConditionFactory
            .createCondition(AssetImportanceDegreeCondition.class);

        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();

        for (AssetImportanceDegreeEnum degreeEnum : AssetImportanceDegreeEnum.values()) {
            condition.setImportanceDegree(degreeEnum.getCode());

            AssetImportancePie assetImportancePie = homePageDao.assetImportanceDegreePie(condition);
            EnumCountResponse enumCountResponse = new EnumCountResponse();
            AssetImportanceDegreeEnum importanceDegreeEnum = AssetImportanceDegreeEnum
                .getByCode(assetImportancePie.getImportanceDegree());
            if (importanceDegreeEnum != null) {
                enumCountResponse.setMsg(importanceDegreeEnum.getMsg());
                enumCountResponse.setNumber(assetImportancePie.getAmount());
                enumCountResponse.setCode(Lists.newArrayList(degreeEnum.getMsg()));
            }

            enumCountResponseList.add(enumCountResponse);
        }

        return enumCountResponseList;
    }
}
