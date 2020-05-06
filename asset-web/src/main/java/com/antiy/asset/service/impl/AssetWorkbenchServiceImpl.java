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
        workOaQueryDto.setOrderType(AssetOaOrderTypeEnum.SCRAP.getCode());
        response.setScapeApplyNum(oaOrderDao.getNumByCondition(workOaQueryDto));

        AssetWorkQueryDto dto = new AssetWorkQueryDto();

        //退回执行 资产状态为带退回
        dto.setAreaId(Collections.singletonList(LoginUserUtil.getLoginUser().getAreaId()));
        dto.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        response.setBackExeNum(assetDao.getWorkNumByCondition(dto));
        //报废执行 资产状态为待报废
        dto.setAssetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        response.setScapeExeNum(assetDao.getWorkNumByCondition(dto));
        //整改处理 有整改详细的按钮的资产（整改中，已入网）
        dto.setAssetStatus(AssetStatusEnum.CORRECTING.getCode());
        response.setChangeHandleNum(assetDao.getWorkNumByCondition(dto));
        //准入实施 （代准入状态）
        dto.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
        response.setAccessDoNum(assetDao.getWorkNumByCondition(dto));
        //安全整改 已跳过安全整改的已入网资产
        dto.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        dto.setRectification(1);
        response.setSafeChangeNum(assetDao.getWorkNumByCondition(dto));
        //代理上报 （未知资产代理上报）
        dto.setAssetStatus(null);
        dto.setRectification(null);
        dto.setAssetSource(AssetSourceEnum.AGENCY_REPORT.getCode());
        response.setProxyUpNum(assetDao.getWorkNumByCondition(dto));
        return response;
    }
}
