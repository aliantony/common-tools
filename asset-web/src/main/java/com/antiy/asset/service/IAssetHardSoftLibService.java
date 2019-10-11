package com.antiy.asset.service;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;

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
     *
     * @param request
     * @return
     */
    String saveAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    String updateAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetHardSoftLibResponse> queryListAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return
     */
    AssetHardSoftLibResponse queryAssetHardSoftLibById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return
     */
    String deleteAssetHardSoftLibById(BaseRequest baseRequest) throws Exception;

    /**
     * 分页查询资产关联的软件信息列表
     *
     * @param queryCondition
     * @return
     */
    PageResult<SoftwareResponse> getPageSoftWareList(AssetSoftwareQuery queryCondition);

    /**
     * 操作系统（下拉项）
     *
     * @return
     */
    List<OsSelectResponse> pullDownOs(OsQuery query);

    /**
     * 查询下拉的厂商信息
     *
     * @return
     */
    List<String> pulldownSupplier(AssetPulldownQuery query) throws Exception;

    List<String> pulldownName(AssetPulldownQuery query);

    List<BusinessSelectResponse> pulldownVersion(AssetPulldownQuery query) throws UnsupportedEncodingException;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<AssetHardSoftLibResponse> queryHardSoftLibList(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    Integer queryHardSoftLibCount(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 软件列表分页（装机模板-添加软件）
     *
     * @param query
     * @return
     */
    public PageResult<AssetHardSoftLibResponse> queryPageSoft(AssetTemplateSoftwareRelationQuery query);

    /**
     * 软件列表（装机模板-关联软件）
     *
     * @return
     */
    public List<AssetHardSoftLibResponse> querySoftsRelations(String templateId);

    /**
     * cpe信息查询-软硬操作系统
     * @param query 查询条件vo
     * @return 分页信息
     */
    ActionResponse<PageResult<AssetAllTypeResponse>> queryAssetList(AssetHardSoftOperQuery query);
}
