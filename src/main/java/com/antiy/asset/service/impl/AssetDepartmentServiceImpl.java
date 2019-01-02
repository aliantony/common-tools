package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dto.AssetDepartmentDTO;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资产部门信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class AssetDepartmentServiceImpl extends BaseServiceImpl<AssetDepartment> implements IAssetDepartmentService {


    @Resource
    private AssetDepartmentDao assetDepartmentDao;
    @Resource
    private BaseConverter<AssetDepartmentRequest, AssetDepartment> requestConverter;
    @Resource
    private BaseConverter<AssetDepartment, AssetDepartmentResponse> responseConverter;

    @Override
    public Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        return assetDepartmentDao.insert(assetDepartment);
    }

    @Override
    public Integer updateAssetDepartment(AssetDepartmentRequest request) throws Exception {
        AssetDepartment assetDepartment = requestConverter.convert(request, AssetDepartment.class);
        return assetDepartmentDao.update(assetDepartment);
    }

    @Override
    public List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception {
        List<AssetDepartmentDTO> assetDepartmentDTO = assetDepartmentDao.findListAssetDepartment(query);
        //TODO
        //需要将assetDepartmentDTO转达成AssetDepartmentResponse
        List<AssetDepartmentResponse> assetDepartmentResponse = new ArrayList<AssetDepartmentResponse>();
        return assetDepartmentResponse;
    }

    public Integer findCountAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return assetDepartmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetDepartment(query), query.getCurrentPage(), this.findListAssetDepartment(query));
    }
}
