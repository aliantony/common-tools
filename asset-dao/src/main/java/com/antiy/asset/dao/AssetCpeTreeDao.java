package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetCpeTree;
import com.antiy.asset.vo.query.AssetCpeTreeCondition;
import com.antiy.common.base.IBaseDao;

/**
 * <p> CPE过滤表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-15
 */
public interface AssetCpeTreeDao extends IBaseDao<AssetCpeTree> {

    Integer countNextNode(AssetCpeTreeCondition condition);

    List<AssetCpeTree> findNextNode(AssetCpeTreeCondition condition);

    List<AssetCpeTree> findTopNode();

    List<AssetCpeTree> findOSTopNode();

    String getBusIdByNodeName(String title);
}
