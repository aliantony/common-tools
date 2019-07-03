package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DepartmentRequestConvert extends BaseConverter<AssetDepartmentRequest, AssetDepartment> {
    private Logger logger = LogUtils.get(DepartmentRequestConvert.class);

    @Override
    protected void convert(AssetDepartmentRequest assetDepartmentRequest, AssetDepartment assetDepartment) {
        try {
            assetDepartment.setId(Integer.valueOf(assetDepartmentRequest.getId()));
        } catch (Exception e) {
            logger.error("String转Integer出错");
        }
        super.convert(assetDepartmentRequest, assetDepartment);
    }
}
