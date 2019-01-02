package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetDepartmentDTO;
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
 * @since 2018-12-29
 */
public interface AssetDepartmentDao extends IBaseDao<AssetDepartment> {

    List<AssetDepartmentDTO> findListAssetDepartment(AssetDepartmentQuery query) throws Exception;
}
