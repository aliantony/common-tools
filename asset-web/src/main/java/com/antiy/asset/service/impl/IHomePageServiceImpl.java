package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetHomePageDao;
import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.vo.response.AssetCountIncludeResponse;

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
}
