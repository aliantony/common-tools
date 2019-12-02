package com.antiy.asset.convert;

import com.antiy.asset.templet.PatchInfoTemplate;
import com.antiy.asset.vo.response.PatchInfoResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class TemplatePatchConvert extends BaseConverter<PatchInfoResponse, PatchInfoTemplate> {
    @Override
    protected void convert(PatchInfoResponse assetInstallTemplate, PatchInfoTemplate assetInstallTemplateForBase) {

        super.convert(assetInstallTemplate, assetInstallTemplateForBase);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }
}
