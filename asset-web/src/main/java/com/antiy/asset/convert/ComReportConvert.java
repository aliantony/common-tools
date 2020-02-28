package com.antiy.asset.convert;

import com.antiy.asset.templet.AssetComReportEntity;
import com.antiy.asset.vo.enums.AssetImportanceDegreeEnum;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class ComReportConvert extends BaseConverter<AssetCompositionReportResponse, AssetComReportEntity> {
    private final Logger logger = LogUtils.get();

    @Override
    protected void convert(AssetCompositionReportResponse asset, AssetComReportEntity assetEntity) {

        AssetImportanceDegreeEnum degreeEnum = AssetImportanceDegreeEnum.getByCode(asset.getImportanceDegree());
        if (degreeEnum != null) {
            assetEntity.setImportanceDegree(degreeEnum.getMsg());
        } else {
            assetEntity.setImportanceDegree(null);
        }
        assetEntity.setResponsibleUserName(asset.getResponsibleUserName());
        assetEntity.setFirstEnterNett(longToDateString(asset.getFirstEnterNett()));
        super.convert(asset, assetEntity);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }

}
