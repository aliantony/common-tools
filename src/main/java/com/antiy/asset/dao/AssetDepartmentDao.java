package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.vo.query.AssetDepartmentQuery;
import com.antiy.asset.entity.vo.response.AssetDepartmentResponse;

/**
 * <p>
 * 资产部门信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetDepartmentDao extends IBaseDao<AssetDepartment> {

    List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception;
}
