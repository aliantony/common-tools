package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.dao.AssetLableDao;
import com.antiy.asset.service.IAssetLableService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetLableServiceImpl extends BaseServiceImpl< AssetLable> implements IAssetLableService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetLableDao assetLableMapper;



    }
