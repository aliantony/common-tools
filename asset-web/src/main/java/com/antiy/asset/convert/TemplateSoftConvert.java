package com.antiy.asset.convert;

import com.antiy.asset.templet.AssetHardSoftLibTemplate;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class TemplateSoftConvert extends BaseConverter<AssetHardSoftLibResponse, AssetHardSoftLibTemplate> {
    @Override
    protected void convert(AssetHardSoftLibResponse assetInstallTemplate,
                           AssetHardSoftLibTemplate assetInstallTemplateForBase) {
        super.convert(assetInstallTemplate, assetInstallTemplateForBase);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }
}
