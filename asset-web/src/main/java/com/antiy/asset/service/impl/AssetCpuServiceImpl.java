package com.antiy.asset.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCpuDao;
import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.asset.vo.response.AssetCpuResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

import static com.antiy.biz.file.FileHelper.logger;

/**
 * <p> 处理器表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetCpuServiceImpl extends BaseServiceImpl<AssetCpu> implements IAssetCpuService {

    @Resource
    private AssetCpuDao                               assetCpuDao;
    @Resource
    private BaseConverter<AssetCpuRequest, AssetCpu>  requestConverter;
    @Resource
    private BaseConverter<AssetCpu, AssetCpuResponse> responseConverter;

    @Override
    public Integer saveAssetCpu(AssetCpuRequest request) throws Exception {
        AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
        Integer result = assetCpuDao.insert(assetCpu);
        // 写入业务日志
        LogHandle.log(assetCpu.toString(), AssetEventEnum.ASSET_CPU_INSERT.getName(),
            AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_CPU_INSERT.getName() + " {}", assetCpu.toString());
        return assetCpu.getId();
    }

    @Override
    public Integer updateAssetCpu(AssetCpuRequest request) throws Exception {
        AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
        Integer result = assetCpuDao.update(assetCpu);
        // 写入业务日志
        LogHandle.log(assetCpu.toString(), AssetEventEnum.ASSET_CPU_UPDATE.getName(),
            AssetEventEnum.ASSET_CPU_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_CPU_UPDATE.getName() + " {}", assetCpu.toString());
        return assetCpuDao.update(assetCpu);
    }

    @Override
    public List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery query) throws Exception {
        List<AssetCpu> assetCpu = assetCpuDao.findListAssetCpu(query);
        List<AssetCpuResponse> assetCpuResponse = responseConverter.convert(assetCpu, AssetCpuResponse.class);
        return assetCpuResponse;
    }

    public Integer findCountAssetCpu(AssetCpuQuery query) throws Exception {
        return assetCpuDao.findCount(query);
    }

    @Override
    public PageResult<AssetCpuResponse> findPageAssetCpu(AssetCpuQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCpu(query), query.getCurrentPage(),
            this.findListAssetCpu(query));
    }
}
