package com.antiy.asset.convert;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author zhangyajun
 * @create 2019-01-22 17:42
 **/
@Component
public class SchemeRequestToSchemeConverter extends BaseConverter<SchemeRequest, Scheme> {
    private static final Logger logger   = LogUtils.get(SchemeRequestToSchemeConverter.class);

    @Override
    protected void convert(SchemeRequest schemeRequest, Scheme scheme){
        try {
            scheme.setId(DataTypeUtils.stringToInteger(schemeRequest.getId()));
            scheme.setPutintoUserId(DataTypeUtils.stringToInteger(schemeRequest.getPutintoUserId()));
        } catch (Exception e) {
            logger.error("String转Integer出错");
        }
        super.convert(schemeRequest, scheme);
    }
}
