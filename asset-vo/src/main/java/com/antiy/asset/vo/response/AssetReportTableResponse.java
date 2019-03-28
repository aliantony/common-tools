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

}
