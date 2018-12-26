package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产与资产组关系表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetGroupRelationServiceImpl extends BaseServiceImpl< AssetGroupRelation> implements IAssetGroupRelationService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetGroupRelationDao assetGroupRelationMapper;



    }
