package com.antiy.asset.vo.response;

import java.util.List;

/**
 * <p> AssetReportResponse 资产报表表格返回对象 </p>
 *
 * @author zhangyajun
 * @since 2019-03-27
 */
public class AssetReportTableResponse extends BaseResponse {
    private String                formName;
    private List<ReportTableHead> children;
    private List<String>          rows;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<ReportTableHead> getChildren() {
        return children;
    }

    public void setChildren(List<ReportTableHead> children) {
        this.children = children;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }
}
