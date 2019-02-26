package com.antiy.asset.service;

import java.util.List;

/**
 * @author zhangyajun
 * @create 2019-02-25 17:30
 **/
public interface IChangeValCompare {

    List<String> getTwoRecentChangeVal(Integer businessId, Integer type);
}
