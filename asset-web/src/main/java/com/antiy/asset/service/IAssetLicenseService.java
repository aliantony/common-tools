package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetLicenseResponse;

/**
 * <p> 资产报表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface IAssetLicenseService {

    /**
     * 授权数量校验
     * @return
     * @throws Exception
     */
    AssetLicenseResponse validateAuthNum() throws Exception;
}
