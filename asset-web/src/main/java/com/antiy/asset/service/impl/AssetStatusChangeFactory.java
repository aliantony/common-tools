package com.antiy.asset.service.impl;

import org.apache.commons.lang.StringUtils;

import com.antiy.common.utils.SpringUtil;

/**
 * 资产状态跃迁静态工厂类
 * @author zhangyajun
 * @create 2019-01-22 18:55
 **/
public class AssetStatusChangeFactory {
    public static <T extends AbstractAssetStatusChangeProcessImpl> T getStatusChangeProcess(Class<T> c) {
        AbstractAssetStatusChangeProcessImpl statusChangeProcess = null;
        try {
            statusChangeProcess = (AbstractAssetStatusChangeProcessImpl) SpringUtil
                .getBean(StringUtils.uncapitalize(c.getSimpleName()));
        } catch (Exception e) {
            // 异常处理
        }
        return (T) statusChangeProcess;
    }
}
