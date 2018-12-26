package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.dao.AssetPortProtocolDao;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 端口协议 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetPortProtocolServiceImpl extends BaseServiceImpl< AssetPortProtocol> implements IAssetPortProtocolService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetPortProtocolDao assetPortProtocolMapper;



    }
