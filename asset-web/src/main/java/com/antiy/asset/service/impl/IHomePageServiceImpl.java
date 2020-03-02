package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.dao.AssetHomePageDao;
import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.util.TwelveTimeUtil;
import com.antiy.asset.vo.enums.AssetImportanceDegreeEnum;
import com.antiy.asset.vo.response.*;
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
        Integer include = homePageDao.countIncludeManage();
        Integer uninclude = homePageDao.countUnincludeManage();
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
        System.out.println(JSON.toJSONString(timeDuration));
        int n = 0;
        for (List<Long> duration : timeDuration) {
            Integer num = homePageDao.assetOnlineChart(duration.get(0), duration.get(1));

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
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        for (AssetImportanceDegreeEnum degreeEnum : AssetImportanceDegreeEnum.values()) {
            AssetImportancePie assetImportancePie = homePageDao.assetImportanceDegreePie(degreeEnum.getCode());
            EnumCountResponse enumCountResponse = new EnumCountResponse();
            AssetImportanceDegreeEnum importanceDegreeEnum = AssetImportanceDegreeEnum
                .getByCode(assetImportancePie.getImportanceDegree());
            if (importanceDegreeEnum != null) {
                String importanceDegree = AssetImportanceDegreeEnum.getByCode(assetImportancePie.getImportanceDegree())
                    .getMsg();
                enumCountResponse.setMsg(importanceDegree);
                enumCountResponse.setNumber(assetImportancePie.getAmount());
                enumCountResponse.setCode(Lists.newArrayList(degreeEnum.getMsg()));
            }

            enumCountResponseList.add(enumCountResponse);
        }

        return enumCountResponseList;
    }

}