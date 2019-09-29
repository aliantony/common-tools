package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetSysServiceLib;
import com.antiy.asset.vo.enums.AssetServiceType;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class SysServiceLibResponseConvert extends BaseConverter<AssetSysServiceLib, AssetSysServiceLibResponse> {
    @Override
    protected void convert(AssetSysServiceLib sysServiceLib, AssetSysServiceLibResponse sysServiceLibResponse) {
        if (sysServiceLib.getServiceClasses() != null) {
            sysServiceLibResponse.setServiceClasses(sysServiceLib.getServiceClasses().toString());
            sysServiceLibResponse.setServiceClassesStr(
                AssetServiceType.getNameByCode(Integer.valueOf(sysServiceLib.getServiceClasses())));
        }
        if (sysServiceLib.getBusinessId() != null) {
            sysServiceLibResponse.setBusinessId(sysServiceLib.getBusinessId().toString());
        }
        super.convert(sysServiceLib, sysServiceLibResponse);
    }
}
