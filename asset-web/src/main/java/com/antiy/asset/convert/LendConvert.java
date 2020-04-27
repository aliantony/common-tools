package com.antiy.asset.convert;

import com.antiy.asset.templet.AssetLendRelationEntity;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class LendConvert extends BaseConverter<AssetLendRelationResponse, AssetLendRelationEntity> {
    private final Logger logger = LogUtils.get();

    @Override
    protected void convert(AssetLendRelationResponse asset, AssetLendRelationEntity assetEntity) {
        assetEntity.setLendTime(longToDateString(asset.getLendTime()));
        // assetEntity
        // .setLendPeriods(longToDateString(asset.getLendTime()) + "-" + longToDateString(asset.getLendPeriods()));
        super.convert(asset, assetEntity);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }

}
