package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetIpMacQuery;
import com.antiy.asset.vo.request.AssetIpMacRequest;
import com.antiy.asset.vo.response.AssetIpMacResponse;
import com.antiy.asset.entity.AssetIpMac;

/**
 * <p> 资产-IP-MAC表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface IAssetIpMacService extends IBaseService<AssetIpMac> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetIpMac(AssetIpMacRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetIpMac(AssetIpMacRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetIpMacResponse> queryListAssetIpMac(AssetIpMacQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetIpMacResponse> queryPageAssetIpMac(AssetIpMacQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetIpMacResponse queryAssetIpMacById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetIpMacById(BaseRequest baseRequest) throws Exception;

}
