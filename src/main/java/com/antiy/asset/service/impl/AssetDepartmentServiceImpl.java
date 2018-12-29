package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.entity.vo.request.AssetDepartmentRequest;
import com.antiy.asset.entity.vo.response.AssetDepartmentResponse;
import com.antiy.asset.entity.vo.query.AssetDepartmentQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产部门信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetDepartmentServiceImpl extends BaseServiceImpl<AssetDepartment> implements IAssetDepartmentService{


        @Resource
        private AssetDepartmentDao assetDepartmentDao;

        private BaseConverter<AssetDepartmentRequest, AssetDepartment>  requestConverter;
        
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
            return assetDepartmentDao.findListAssetDepartment(query);
        }

        public Integer findCountAssetDepartment(AssetDepartmentQuery query) throws Exception {
            return assetDepartmentDao.findCount(query);
        }

        @Override
        public PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetDepartment(query),query.getCurrentPage(), this.findListAssetDepartment(query));
        }
}
