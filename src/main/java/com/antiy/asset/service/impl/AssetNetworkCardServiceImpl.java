package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.service.IAssetNetworkCardService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 网卡信息表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetNetworkCardServiceImpl extends BaseServiceImpl< AssetNetworkCard> implements IAssetNetworkCardService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetNetworkCardDao assetNetworkCardMapper;



    }
