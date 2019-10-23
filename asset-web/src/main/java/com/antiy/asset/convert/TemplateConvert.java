package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.templet.AssetInstallTemplateForBase;
import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class TemplateConvert extends BaseConverter<AssetInstallTemplate, AssetInstallTemplateForBase> {
    @Override
    protected void convert(AssetInstallTemplate assetInstallTemplate,
                           AssetInstallTemplateForBase assetInstallTemplateForBase) {
        assetInstallTemplateForBase.setGmtCreate(longToDateString(assetInstallTemplate.getGmtCreate()));
        assetInstallTemplateForBase.setGmtModified(longToDateString(assetInstallTemplate.getGmtModified()));
        assetInstallTemplateForBase
            .setCategoryModel(AssetCategoryEnum.getNameByCode(assetInstallTemplate.getCategoryModel()));
        assetInstallTemplateForBase.setCurrentStatus(
            AssetInstallTemplateStatusEnum.getEnumByCode(assetInstallTemplate.getCurrentStatus()).getStatus());
        super.convert(assetInstallTemplate, assetInstallTemplateForBase);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }
}
