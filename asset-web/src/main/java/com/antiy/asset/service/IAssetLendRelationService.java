package com.antiy.asset.service;

import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.vo.query.ApproveListQuery;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
public interface IAssetLendRelationService extends IBaseService<AssetLendRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    String saveAssetLendRelation(AssetLendRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    String updateAssetLendRelation(AssetLendRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetLendRelationResponse> queryListAssetLendRelation(AssetLendRelationQuery query) throws Exception;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResult<AssetLendRelationResponse> queryPageAssetLendRelation(AssetLendRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return
     */
    AssetLendRelationResponse queryAssetLendRelationById(QueryCondition queryCondition) throws Exception;

    void exportData(AssetLendRelationQuery assetQuery, HttpServletResponse response,
                    HttpServletRequest request) throws Exception;

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return
     */
    String deleteAssetLendRelationById(BaseRequest baseRequest) throws Exception;

    /**
     * 查询详情
     *
     * @param uniqueId
     * @return
     */
    AssetLendRelationResponse queryInfo(String uniqueId);

    /**
     * 归还确认
     *
     * @param uniqueId
     * @return
     */
    Integer returnConfirm(AssetLendRelationRequest uniqueId);

    List<AssetLendRelationResponse> queryHistory(ObjectQuery uniqueId);

    /**
     * 保存出借信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    String saveLendInfo(AssetLendInfoRequest request) throws Exception;

    /**
     * 审批单综合查询
     *
     * @param query
     * @return
     */
    PageResult<ApproveListResponse> queryApproveList(ApproveListQuery query);

    /**
     * 申请人列表查询
     *
     * @return
     */
    List<UserListResponse> queryUserList(ApplicantRequest request);

    /**
     * 申请人信息查询
     *
     * @param request
     * @return
     */
    UserInfoResponse queryUserInfo(UserInfoRequest request);

    AssetResponse queryAssetInfo(Integer id);

    /**
     * 保存出借信息(批量)
     *
     * @param request
     * @return
     * @throws Exception
     */
    String saveLendInfos(AssetLendInfosRequest request) throws Exception;

    String queryByAssetId(QueryCondition queryCondition);
}

