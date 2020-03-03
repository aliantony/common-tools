package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetEntryDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetEnterStatusEnum;
import com.antiy.asset.vo.enums.AssetEntrySourceEnum;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.AssetEntryRecordRequest;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryRecordResponse;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@Service
public class AssetEntryServiceImpl implements iAssetEntryService {
    private Logger logger = LogUtils.get(this.getClass());
    @Resource
    private AssetEntryDao assetEntryDao;
    @Value("${sendEntryCommond}")
    private String entryCommondUrl;
    @Resource
    private AssetDao assetDao;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public PageResult<AssetEntryResponse> queryPage(AssetEntryQuery query) throws Exception {
        int count = assetEntryDao.findCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Collections.emptyList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.queryList(query));
    }

    public List<AssetEntryResponse> queryList(AssetEntryQuery query) throws Exception {
        return assetEntryDao.findQuery(query);
    }

    @Override
    public String updateEntryStatus(AssetEntryRequest request) {
        //准入状态一致不用下发，过滤当前资产准入状态与更新状态不一致的资产
        List<String> assetIds = request.getAssetIds().stream().filter(v -> {
            if (!assetDao.getByAssetId(v).getAdmittanceStatus().equals(request.getUpdateStatus())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (assetIds.isEmpty()) {
            throw new RequestParamValidateException("所选资产已经是" + AssetEnterStatusEnum.getAssetByCode(request.getUpdateStatus()).getMsg() + "状态,操作无效");
        }
        request.setAssetIds(assetIds);
        //todo 下发指令 判断第三方是全部成功还是部分成功
        boolean isSuccess = sendCommond(request);
        //操作日志-安全事件
        String incident = EnumUtil.equals(request.getUpdateStatus(), AssetEnterStatusEnum.ENTERED) ? "允许入网" : "禁止入网";
        List<AssetEntryRecordRequest> recordRequestList = new ArrayList<>();
        assetIds.forEach(v -> {
            Asset asset = assetDao.getByAssetId(v);
            //记录操作日志
            LogUtils.recordOperLog(new BusinessData(incident, asset.getStringId(), asset.getNumber(), asset,
                    BusinessModuleEnum.ACCESS_MANAGEMENT, BusinessPhaseEnum.NONE));

            int userId = EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.ASSET_RETIRE) ? 0 : LoginUserUtil.getLoginUser().getId();//0表示系统下发
            AssetEntryRecordRequest recordRequest = new AssetEntryRecordRequest(asset.getStringId(), request.getEntrySource().getCode(),
                    isSuccess ? 1 : 0, request.getUpdateStatus(), System.currentTimeMillis(), userId
            );
            recordRequestList.add(recordRequest);
        });
        //保存历史记录
        assetEntryDao.insertRecordBatch(recordRequestList);

        if (BooleanUtils.isFalse(isSuccess)) {
            //todo 发系统消息
            throw new BusinessException("准入状态变更失败！");
        } else {
            //更新资产准入状态
            assetEntryDao.updateEntryStatus(request);
        }

        return "变更成功";
    }

    private boolean sendCommond(AssetEntryRequest request) {
        //todo 测试是否可以下发指令通信
        int count = 0;
        ActionResponse response = null;
        //todo 下发参数设置
        MultiValueMap<String, Object> commondParam = new LinkedMultiValueMap<>();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        //获取可用连接的超时时间15秒
        requestFactory.setConnectionRequestTimeout(15_000);
        //建立连接前的超时时间15秒
        requestFactory.setConnectTimeout(15_000);
        //建立连接后获取响应的超时时间15秒
        requestFactory.setReadTimeout(15_000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(commondParam, headers);

        logger.info("请求地址：{}，参数：{}", entryCommondUrl, commondParam);
        //下发指令
        do {
            count++;
            ResponseEntity<ActionResponse> responseEntity = null;
            try {
                responseEntity = restTemplate.exchange(entryCommondUrl, HttpMethod.POST, entity, ActionResponse.class);
                response = responseEntity.getBody();
            } catch (Exception e) {
                logger.warn("指令第{}次下发失败，代码异常：{}", count
                        , e.getMessage());
                continue;
            }
            //下发失败，继续下发指令
            if (response == null || !StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("指令第{}次下发失败,response:{}", response);
                continue;
            }

        } while (count < 2);
        if (response == null || !StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
            return false;
        }
        return true;
    }

    private void sendMessage() {

    }

    @Override
    public List<AssetEntryRecordResponse> queryRecord(AssetEntryQuery query) {
        return assetEntryDao.queryEntryRecord(query).stream().map(v -> {
                    v.setCreateUserName(v.getCreateUser() == 0 ? "系统" : queryUserName(v.getCreateUser()));
                    return v;
                }
        ).collect(Collectors.toList());
    }

    private String queryUserName(int userId)  {
        //从redis上获取用户信息
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                userId);
        SysUser sysUser = null;
        try {
            sysUser = redisUtil.getObject(key, SysUser.class);
        } catch (Exception e) {
            logger.info("redis获取用户信息发生异常，用户id:{}",userId);
        }
        if (sysUser == null) {
            LogUtils.info(logger, "{}:该用户不存在",userId);
            return "";
        }
        return sysUser.getName();
    }
}
