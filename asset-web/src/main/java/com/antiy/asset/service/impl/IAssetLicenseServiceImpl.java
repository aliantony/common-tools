package com.antiy.asset.service.impl;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.service.IAssetLicenseService;
import com.antiy.asset.vo.response.AssetLicenseResponse;
import com.antiy.common.utils.LicenseUtil;

/**
 * @author zhangyajun
 * @create 2019-06-22 14:21
 **/
@Service
public class IAssetLicenseServiceImpl implements IAssetLicenseService {
    @Resource
    private AssetDao assetDao;

    @Override
    public AssetLicenseResponse validateAuthNum() throws Exception {
        Integer authNum = LicenseUtil.getLicense().getAssetNum();
        AssetLicenseResponse licenseResponse = new AssetLicenseResponse();
        Integer num = assetDao.countAsset();
        if (!Objects.isNull(authNum)) {
            if (authNum <= num) {
                licenseResponse.setMsg("资产数量已超过授权数量，请联系客服人员！");
                licenseResponse.setResult(false);
            } else {
                licenseResponse.setResult(true);
            }
        } else {
            licenseResponse.setMsg("license异常，请联系客服人员！");
            licenseResponse.setResult(false);
        }
        return licenseResponse;
    }
}
