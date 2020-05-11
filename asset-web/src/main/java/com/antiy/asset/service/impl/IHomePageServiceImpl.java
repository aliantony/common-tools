package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetHomePageDao;
import com.antiy.asset.dao.AssetRunRecordDao;
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

    @Value("${login.user.debug}")
    private static Boolean   enable;
    @Value("${monitoringDayAmount}")
    private static Long       monitoringDayAmount;

    @Resource
    private AssetHomePageDao homePageDao;

    @Resource
    private AssetRunRecordDao runRecordDao;

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
                //TODO 演示数据
                nameValueList.get(n).setValue(RandomUtils.nextInt(99));
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
            if (assetImportancePie.getImportanceDegree() != null) {
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
        }

        return enumCountResponseList;
    }

    @Override
    public Long getRunDay() throws Exception {
        long day = 0L;
        // 单位为毫秒的总运行时间转天数，24小时算一天
        Long totalTime = runRecordDao.getRunDayTime();

        Long dayTime = 24 * 60 * 60 * 1000L;
        if (totalTime > dayTime) {
            day = totalTime / dayTime;
        }
        return day;
    }
}
