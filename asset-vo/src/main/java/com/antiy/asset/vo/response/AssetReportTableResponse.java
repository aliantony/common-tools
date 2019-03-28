package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

/**
 * <p> AssetReportResponse 资产报表表格返回对象 </p>
 *
 * @author zhangyajun
 * @since 2019-03-27
 */
public class AssetReportTableResponse extends BaseResponse {
    private String                    formName;
    private List<ReportTableHead>     children;
    private List<Map<String, String>> rows;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<ReportTableHead> getChildren() {
        return children;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }

    public void setChildren(List<ReportTableHead> children) {
        this.children = children;
    }
}
