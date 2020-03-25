package com.antiy.asset.convert;

import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.vo.enums.AdmittanceStatusEnum;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AccessExportConvert extends BaseConverter<AssetEntryResponse, AccessExport> {
    @Override
    protected void convert(AssetEntryResponse assetResponse, AccessExport accessExport) {
            accessExport.setAdmittanceStatusString(
                AdmittanceStatusEnum.getAdmittanceStatusEnum(assetResponse.getEntryStatus()));
        if (assetResponse.getIp() != null) {
            accessExport.setIp(assetResponse.getIp());
        }
        if (assetResponse.getMac() != null) {
            accessExport.setMac(assetResponse.getMac());
        }
        super.convert(assetResponse, accessExport);
    }
}
