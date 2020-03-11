package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.asset.vo.query.AssetRunRecordQuery;
import com.antiy.asset.vo.request.AssetRunRecordRequest;
import com.antiy.asset.vo.response.AssetRunRecordResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 系统运行记录表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-10
 */
public interface IAssetRunRecordService extends IBaseService<AssetRunRecord> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetRunRecord(AssetRunRecordRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetRunRecord(AssetRunRecordRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetRunRecordResponse> queryListAssetRunRecord(AssetRunRecordQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetRunRecordResponse> queryPageAssetRunRecord(AssetRunRecordQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetRunRecordResponse queryAssetRunRecordById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetRunRecordById(BaseRequest baseRequest) throws Exception;

}
