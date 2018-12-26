package com.antiy.asset.service.impl;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.base.BaseServiceImpl;

import com.antiy.asset.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 安全设备详情表 服务实现类
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
@Service
@Transactional
public class AssetSafetyEquipmentServiceImpl extends BaseServiceImpl< AssetSafetyEquipment> implements IAssetSafetyEquipmentService {

        private static final Logger logger = LogUtils.get();

        @Autowired
        private AssetSafetyEquipmentDao assetSafetyEquipmentMapper;



    }
