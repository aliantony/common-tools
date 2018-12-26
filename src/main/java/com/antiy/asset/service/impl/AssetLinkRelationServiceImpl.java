package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 通联关系表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetLinkRelationServiceImpl extends BaseServiceImpl< AssetLinkRelation> implements IAssetLinkRelationService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetLinkRelationDao assetLinkRelationMapper;



    }
