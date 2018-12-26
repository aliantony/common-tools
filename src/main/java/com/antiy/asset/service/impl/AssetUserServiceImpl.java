package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 资产用户信息 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetUserServiceImpl extends BaseServiceImpl< AssetUser> implements IAssetUserService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetUserDao assetUserMapper;



    }
