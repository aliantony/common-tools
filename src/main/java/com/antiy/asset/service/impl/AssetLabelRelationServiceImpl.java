package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.dao.AssetLabelRelationDao;
import com.antiy.asset.service.IAssetLabelRelationService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产标签关系表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetLabelRelationServiceImpl extends BaseServiceImpl< AssetLabelRelation> implements IAssetLabelRelationService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetLabelRelationDao assetLabelRelationMapper;



    }
