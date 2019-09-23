package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import org.apache.ibatis.annotations.Param;

/**
 * <p> CPE表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetHardSoftLibDao extends IBaseDao<AssetHardSoftLib> {

    List<String> pulldownSupplier(AssetPulldownQuery query);

    List<String> pulldownName(AssetPulldownQuery query);

    List<String> pulldownVersion(AssetPulldownQuery query);
}
