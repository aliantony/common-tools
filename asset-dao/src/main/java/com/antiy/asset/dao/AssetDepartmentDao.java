package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产部门信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetDepartmentDao extends IBaseDao<AssetDepartment> {

    List<AssetDepartment> findListAssetDepartment(AssetDepartmentQuery query) throws Exception;

    Integer delete(List<AssetDepartment> list) throws Exception;

}
