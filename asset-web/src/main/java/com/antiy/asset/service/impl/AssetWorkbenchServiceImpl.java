package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOaOrderDao;
import com.antiy.asset.dto.AssetWorkQueryDto;
import com.antiy.asset.dto.WorkOaQueryDto;
import com.antiy.asset.service.IAssetWorkbenchService;
import com.antiy.asset.vo.enums.AssetOaOrderStatusEnum;
import com.antiy.asset.vo.enums.AssetOaOrderTypeEnum;
import com.antiy.asset.vo.enums.AssetSourceEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.response.AssetWorkBenchResponse;
import com.antiy.common.utils.LoginUserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ygh
 * @Description 资产工作台服务类
 * @Date 2020/4/22
 */
@Service
public class AssetWorkbenchServiceImpl implements IAssetWorkbenchService {

    @Resource
    private AssetOaOrderDao oaOrderDao;

    @Resource
    private AssetDao assetDao;

    @Override
    public AssetWorkBenchResponse queryWorkBench() {
        AssetWorkBenchResponse response = new AssetWorkBenchResponse();
        WorkOaQueryDto workOaQueryDto = new WorkOaQueryDto();
        //退回申请 oa订单钟待处理
        workOaQueryDto.setOrderStatus(AssetOaOrderStatusEnum.WAIT_HANDLE.getCode());
        workOaQueryDto.setOrderType(AssetOaOrderTypeEnum.BACK.getCode());
        response.setBackApplyNum(oaOrderDao.getNumByCondition(workOaQueryDto));
        //报废申请 oa订单钟报废待处理
        workOaQueryDto.setOrderStatus(AssetOaOrderTypeEnum.SCRAP.getCode());
        response.setScapeApplyNum(oaOrderDao.getNumByCondition(workOaQueryDto));

        AssetWorkQueryDto dto = new AssetWorkQueryDto();

        //退回执行 资产状态为带退回
        dto.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        response.setBackExeNum(assetDao.getWorkNumByCondition(dto));
        //报废执行 资产状态为待报废
        dto.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        response.setScapeExeNum(assetDao.getWorkNumByCondition(dto));

        dto.setAreaId(Collections.singletonList(LoginUserUtil.getLoginUser().getAreaId()));
        dto.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        response.setScapeExeNum(assetDao.getWorkNumByCondition(dto));
        //安全整改 暂时不懂
        //dto.setAssetStatus(AssetStatusEnum.W.getCode());
        dto.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        //整改处理
        dto.setAssetStatus(AssetStatusEnum.CORRECTING.getCode());
        response.setChangeHandleNum(assetDao.getWorkNumByCondition(dto));
        //准入实施
        dto.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        response.setAccessDoNum(assetDao.getWorkNumByCondition(dto));
        //代理上报
        dto.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        dto.setAssetSource(AssetSourceEnum.AGENCY_REPORT.getCode());
        response.setProxyUpNum(assetDao.getWorkNumByCondition(dto));
        return response;
    }
}
