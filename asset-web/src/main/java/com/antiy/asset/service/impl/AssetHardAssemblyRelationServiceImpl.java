package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetHardAssemblyRelation;
import com.antiy.asset.dao.AssetHardAssemblyRelationDao;
import com.antiy.asset.service.IAssetHardAssemblyRelationService;
import com.antiy.asset.vo.request.AssetHardAssemblyRelationRequest;
import com.antiy.asset.vo.response.AssetHardAssemblyRelationResponse;
import com.antiy.asset.vo.query.AssetHardAssemblyRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 硬件与组件关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetHardAssemblyRelationServiceImpl extends BaseServiceImpl<AssetHardAssemblyRelation>
                                                  implements IAssetHardAssemblyRelationService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetHardAssemblyRelationDao                                                assetHardAssemblyRelationDao;
    @Resource
    private BaseConverter<AssetHardAssemblyRelationRequest, AssetHardAssemblyRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetHardAssemblyRelation, AssetHardAssemblyRelationResponse> responseConverter;

}
