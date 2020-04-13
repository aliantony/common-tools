package com.antiy.asset.vo.query;

import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * key管理 -- 下拉查询
 *
 * @author chenchaowu
 */
public class KeyPullQuery implements ObjectValidator {

    /**
     * 综合查询条件--资产编号 or 用户名
     */
    @ApiModelProperty("综合查询条件")
    @Size(max = 30, message = "综合查询条件不能超过30")
    private String multipleQuery;

    /**
     * 用户管辖区域
     */
    @ApiModelProperty("用户管辖区域")
    @Encode
    private List<String> areaIds;

    @ApiModelProperty("每页数据量")
    private int pageSize = 30;

    @ApiModelProperty("当前页")
    private int currentPage = 1;

    @Override
    public void validate() throws RequestParamValidateException {
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageOffset() {
        return (this.currentPage - 1) * this.pageSize;
    }

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }
}
