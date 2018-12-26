package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 软件信息表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetSoftwareServiceImpl extends BaseServiceImpl< AssetSoftware> implements IAssetSoftwareService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetSoftwareDao assetSoftwareMapper;



    }
