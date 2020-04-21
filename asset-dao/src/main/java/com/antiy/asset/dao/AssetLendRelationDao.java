package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.vo.query.ApproveListQuery;
import com.antiy.asset.vo.request.AssetLendRelationRequest;
import com.antiy.asset.vo.request.UserInfoRequest;
import com.antiy.asset.vo.response.ApproveListResponse;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.vo.response.UserInfoResponse;
import com.antiy.asset.vo.response.UserListResponse;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.ObjectQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
public interface AssetLendRelationDao extends IBaseDao<AssetLendRelation> {
    /**
     * 查询详情
     * @param uniqueId
     * @return
     */
    AssetLendRelationResponse queryInfo(String uniqueId);

    Integer returnConfirm(AssetLendRelationRequest assetLendRelationRequest);

    List<AssetLendRelationResponse> queryHistory(ObjectQuery assetId);

    int countHistory(ObjectQuery objectQuery);

    List<AssetLendRelationResponse> queryHistory(String assetId);

    List<ApproveListResponse> queryApproveList(ApproveListQuery query);

    Integer queryApproveListCount(ApproveListQuery query);

    List<UserListResponse> queryUserList();

    UserInfoResponse queryUserInfo(UserInfoRequest request);

    String queryDepartment(String departmentId);

    String queryDepartmentParent(String departmentId);
}
