package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.dao.AssetHomePageDao;
import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.util.TwelveTimeUtil;
import com.antiy.asset.vo.response.AssetCountIncludeResponse;
import com.antiy.asset.vo.response.AssetOnlineChartResponse;
import com.antiy.asset.vo.response.NameValue;
import com.antiy.asset.vo.response.NameValueVo;

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
        nameValueVo.setData(nameValueList);

        onlineChartResponse.setxData(abscissa);
        onlineChartResponse.setCoordinate(nameValueVo);
        return onlineChartResponse;
    }
}
