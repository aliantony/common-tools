package com.antiy.asset.service;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p> CPE表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetHardSoftLibService extends IBaseService<AssetHardSoftLib> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetHardSoftLibResponse> queryListAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetHardSoftLibResponse queryAssetHardSoftLibById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetHardSoftLibById(BaseRequest baseRequest) throws Exception;

    /**
     * 分页查询资产关联的软件信息列表
     * @param queryCondition
     * @return
     */
    PageResult<SoftwareResponse> getPageSoftWareList(AssetSoftwareQuery queryCondition);

    /**
     * 操作系统（下拉项）
     *
     * @return
     */
    List<SelectResponse> pullDownOs(OsQuery query);

    /**
     * 查询下拉的厂商信息
     *
     * @return
     */
    List<String> pulldownSupplier(AssetPulldownQuery query) throws Exception;

    List<String> pulldownName(AssetPulldownQuery query);

    List<SelectResponse> pulldownVersion(AssetPulldownQuery query) throws UnsupportedEncodingException;

}
