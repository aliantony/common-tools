package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetInstallTemplateCheck;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p> 装机模板审核表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface AssetInstallTemplateCheckDao extends IBaseDao<AssetInstallTemplateCheck> {

    /**
     * 通过装机模板ID查询
     * @param templateId
     * @return
     * @throws Exception
     */
    List<AssetInstallTemplateCheck> queryTemplateCheckByTemplateId(String templateId) throws Exception;
}
