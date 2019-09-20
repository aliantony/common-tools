package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "软件请求")
public class AssetSoftwareReportRequest extends BasicRequest implements ObjectValidator {

    private Integer softId;

    public Integer getSoftId() {
        return softId;
    }

    public void setSoftId(Integer softId) {
        this.softId = softId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}