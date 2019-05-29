package com.antiy.asset.convert;

import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.vo.enums.AdmittanceStatusEnum;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AccessExportConvert extends BaseConverter<AssetResponse, AccessExport> {
    @Override
    protected void convert(AssetResponse assetResponse, AccessExport accessExport) {
        if (assetResponse.getAdmittanceStatus() != null) {
            accessExport.setAdmittanceStatusString(
                AdmittanceStatusEnum.getAdmittanceStatusEnum(assetResponse.getAdmittanceStatus()));
        }
        super.convert(assetResponse, accessExport);
    }
}
