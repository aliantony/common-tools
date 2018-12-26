package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产部门信息 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetDepartmentServiceImpl extends BaseServiceImpl< AssetDepartment> implements IAssetDepartmentService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetDepartmentDao assetDepartmentMapper;



    }
