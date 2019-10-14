package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetAssemblyLibQuery;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.entity.AssetAssemblyLib;

/**
 * <p> 组件表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetAssemblyLibService extends IBaseService<AssetAssemblyLib> {

    List<AssetAssemblyResponse> queryAssemblyByHardSoftId(QueryCondition query);

}
