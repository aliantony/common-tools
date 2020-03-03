package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetEntryDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetEntryRecord;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetEnterStatusEnum;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
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
import java.util.Collections;
import java.util.List;

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
        boolean isSuccess = sendCommond(request);
        String incident = EnumUtil.equals(request.getUpdateStatus(), AssetEnterStatusEnum.ENTERED) ? "允许入网" : "禁止入网";

        request.getAssetIds().forEach(v -> {
            Asset asset = assetDao.getByAssetId(v);
            //记录操作日志
            LogUtils.recordOperLog(new BusinessData(incident, asset.getStringId(), asset.getNumber(), asset,
                    BusinessModuleEnum.ACCESS_MANAGEMENT, BusinessPhaseEnum.NONE));
            //保存历史记录


        });
        if (BooleanUtils.isFalse(isSuccess)) {
            throw new BusinessException("准入状态变更失败！");
        } else {
            //更新资产准入状态
            assetEntryDao.updateEntryStatus(request);
        }

        return null;
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


    @Override
    public List<AssetEntryRecord> queryRecord(AssetEntryQuery query) {
        return null;
    }
}
