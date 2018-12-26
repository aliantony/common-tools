package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产软件关系信息 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl< AssetSoftwareRelation> implements IAssetSoftwareRelationService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetSoftwareRelationDao assetSoftwareRelationMapper;



    }
