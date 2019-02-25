package com.antiy.asset.service.impl;

import org.apache.commons.lang.StringUtils;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.SpringUtil;

/**
 * 资产变更记录工厂类
 * @author zhangyajun
 * @create 2019-01-22 18:55
 **/
public class AssetChangeRecordFactory {
    public static <T extends AbstractChangeRecordCompareImpl> T getAssetChangeRecordProcess(Class<T> c) {
        AbstractChangeRecordCompareImpl changeRecordCompare;
        try {
            changeRecordCompare = (AbstractChangeRecordCompareImpl) SpringUtil
                .getBean(StringUtils.uncapitalize(c.getSimpleName()));
        } catch (Exception e) {
             throw new BusinessException("获取实例失败");
        }
        return (T) changeRecordCompare;
    }
}
