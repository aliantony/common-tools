package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetCpeTree;
import com.antiy.asset.vo.query.AssetCpeTreeCondition;
import com.antiy.asset.vo.query.AssetCpeTreeQuery;
import com.antiy.asset.vo.request.AssetCpeTreeRequest;
import com.antiy.asset.vo.response.AssetCpeTreeResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> CPE过滤表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-15
 */
public interface IAssetCpeTreeService extends IBaseService<AssetCpeTree> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetCpeTree(AssetCpeTreeRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetCpeTree(AssetCpeTreeRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetCpeTreeResponse> queryListAssetCpeTree(AssetCpeTreeQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetCpeTreeResponse> queryPageAssetCpeTree(AssetCpeTreeQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetCpeTreeResponse queryAssetCpeTreeById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetCpeTreeById(BaseRequest baseRequest) throws Exception;

    /**
     * 通过ID查询子当前节点和节点数据
     * @param queryCondition
     * @return
     */
    List<AssetCpeTreeResponse> querySubTreeById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID查询子当前节点和节点数据
     * @param queryCondition
     * @return
     */
    List<AssetCpeTreeResponse> queryNextNodeById(AssetCpeTreeCondition queryCondition) throws Exception;

    /**
     * 查询所有的树形分类数据
     * @return
     */
    List<AssetCpeTreeResponse> queryTree() throws Exception;

    /**
     * 根据节点名获取对应的BusinessId
     *
     * @param request
     * @return
     * @throws Exception
     */
    String queryUniqueIdByNodeName(AssetCpeTreeRequest request)  throws Exception;

    List<String> queryOsNameList() throws Exception;
}
