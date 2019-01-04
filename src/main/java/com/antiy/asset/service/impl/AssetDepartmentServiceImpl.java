package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
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
        List<AssetDepartment> assetDepartment = assetDepartmentDao.findListAssetDepartment(query);
        return convert(assetDepartment);
    }

    private List<AssetDepartmentResponse> convert(List<AssetDepartment> assetDepartments) {
        if (assetDepartments.size() > 0) {
            List assetDepartmentResponse = BeanConvert.convert(assetDepartments, AssetDepartmentResponse.class);
            setListID(assetDepartments, assetDepartmentResponse);
            return assetDepartmentResponse;
        } else
            return new ArrayList<>();
    }

    private void setListID(List<AssetDepartment> assetDepartments, List<AssetDepartmentResponse> assetDepartmentResponses) {
        for (int i = 0; i < assetDepartmentResponses.size(); i++) {
            setID(assetDepartments.get(i), assetDepartmentResponses.get(i));
        }
    }

    private void setID(AssetDepartment assetDepartment, AssetDepartmentResponse assetDepartmentResponse) {
        assetDepartmentResponse.setId(assetDepartment.getId());
    }

    public Integer findCountAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return assetDepartmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetDepartment(query), query.getCurrentPage(), this.findListAssetDepartment(query));
    }
}
