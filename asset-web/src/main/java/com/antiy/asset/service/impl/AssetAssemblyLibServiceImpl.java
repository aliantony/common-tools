package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.vo.enums.AssemblyTypeEnum;
import com.antiy.asset.vo.response.AssetAssemblyDetailResponse;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetAssemblyLib;
import com.antiy.asset.dao.AssetAssemblyLibDao;
import com.antiy.asset.service.IAssetAssemblyLibService;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.vo.query.AssetAssemblyLibQuery;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 组件表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetAssemblyLibServiceImpl extends BaseServiceImpl<AssetAssemblyLib> implements IAssetAssemblyLibService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetAssemblyLibDao                                       assetAssemblyLibDao;
    @Resource
    private BaseConverter<AssetAssemblyLibRequest, AssetAssemblyLib>  requestConverter;
    @Resource
    private BaseConverter<AssetAssemblyLib, AssetAssemblyLibResponse> responseConverter;
    @Resource
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>       assemblyResponseBaseConverter;


    @Override
    public List<AssetAssemblyResponse> queryAssemblyByHardSoftId(AssetAssemblyLibQuery query) {
        List<AssetAssemblyDetailResponse> assemblyDetailResponseList = Lists.newArrayList();
        List<AssetAssemblyResponse> assemblyResponseList = assemblyResponseBaseConverter
                .convert(assetAssemblyLibDao.queryAssemblyByHardSoftId(query.getBusinessId()), AssetAssemblyResponse.class);
       /* if (CollectionUtils.isNotEmpty(assemblyResponseList)) {
            Map<String, List<AssetAssemblyResponse>> map = assemblyResponseList.stream()
                    .collect(Collectors.groupingBy(AssetAssemblyResponse::getType));
            for (Map.Entry<String, List<AssetAssemblyResponse>> entryAssembly : map.entrySet()) {
                AssetAssemblyDetailResponse detailResponse = new AssetAssemblyDetailResponse();
                detailResponse.setAssemblyResponseList(entryAssembly.getValue());
                detailResponse.setCount(entryAssembly.getValue().size());
                detailResponse.setType(entryAssembly.getKey());
                detailResponse
                        .setTypeName(AssemblyTypeEnum.getNameByCode(entryAssembly.getKey()));
                assemblyDetailResponseList.add(detailResponse);
            }
        }*/
        return assemblyResponseList;
    }

}
