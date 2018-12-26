package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产组表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetGroupServiceImpl extends BaseServiceImpl< AssetGroup> implements IAssetGroupService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetGroupDao assetGroupMapper;



    }
