package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetEntryDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetEnterStatusEnum;
import com.antiy.asset.vo.enums.AssetEntrySourceEnum;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetEntryRecordRequest;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryRecordResponse;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.asset.vo.response.AssetEntryStatusResponse;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
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
    @Resource
    private SysMessageSender messageSender;
    @Resource
    private AesEncoder aesEncoder;
    @Resource
    private BaseClient client;
    @Value("${TypeAreaId}")
    private String getUesrByTagUrl;
    @Value("${queryVulCompleteUrl}")
    private String vulCompleteUrl;
    @Value("${queryPatchInstallCompleteUrl}")
    private String patchInstallCompleteUrl;
    @Value("${queryConfigCompleteUrl}")
    private String configCompleteUrl;

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
    public String updateEntryStatus(AssetEntryRequest request, SecurityContext... context) {
        //token设置
        if (ArrayUtils.isNotEmpty(context)) {
            SecurityContextHolder.setContext(context[0]);
        }
        //准入状态一致不用下发，过滤当前资产准入状态与更新状态不一致的资产
        List<ActivityHandleRequest> activityHandleRequests = request.getAssetActivityRequests().stream().filter(v -> {
            if (!assetDao.getByAssetId(v.getStringId()).getAdmittanceStatus().equals(request.getUpdateStatus())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (activityHandleRequests.isEmpty()) {
            throw new RequestParamValidateException("所选资产已经是" + AssetEnterStatusEnum.getAssetByCode(request.getUpdateStatus()).getMsg() + "状态,操作无效");
        }
        request.setAssetActivityRequests(activityHandleRequests);
        List<String> assetIds = activityHandleRequests.stream().map(ActivityHandleRequest::getStringId).collect(Collectors.toList());
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

        //失败发送系统消息
        if (BooleanUtils.isFalse(isSuccess)) {
            sendMessage(assetIds, request.getUpdateStatus());
            throw new BusinessException("准入状态变更失败！");
        } else {
            //如果来源是漏洞扫描，补丁安装，配置扫描，启动自动恢复机制
            if (EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.CONFIG_SCAN)
                    || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.VUL_SCAN)
                    || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.PATCH_INSTALL)
            ) {
                scanTask(assetIds, request.getEntrySource());
            }
            //更新资产准入状态
            assetEntryDao.updateEntryStatus(request);
        }

        return "变更成功";
    }

    //每隔12小时扫描一次
//    @Async
    public void scanTask(List<String> assetIds, AssetEntrySourceEnum sourceEnum) {
        //第一次进来休眠12小时
        try {
            Thread.currentThread().wait(12 * 60 * 60_000);
        } catch (InterruptedException e) {
            logger.info("准入管理-自动恢复入网任务执行完毕");
            e.printStackTrace();
        }
//        BlockingQueue
        //todo 查询验证 对接漏洞、补丁、配置
        Map<String, Object> param = new HashMap<>();
        for (String assetId : assetIds) {
            boolean isSuccess = true;
            //查询漏洞是否修复完成
            ActionResponse vulResponse = (ActionResponse) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
            }, vulCompleteUrl);
            if (vulResponse == null || !vulResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("获取资产漏洞修复完成情况失败");
                isSuccess = false;
            }
            //查询补丁是否安装完成
            ActionResponse patchResponse = (ActionResponse) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
            }, patchInstallCompleteUrl);
            if (patchResponse == null || !patchResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("获取资产补丁安装完成情况失败");
                isSuccess = false;
            }
            //查询配置是否完成
            ActionResponse configResponse = (ActionResponse) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
            }, configCompleteUrl);
            if (configResponse == null || !configResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("获取资产配置完成情况失败");
                isSuccess = false;
            }
            if (!isSuccess) {
                continue;
            } else {

            }
        }


    }

    private boolean sendCommond(AssetEntryRequest request) {
        //todo 测试是否可以下发指令通信
        List<String> assetIds = request.getAssetActivityRequests().stream().map(ActivityHandleRequest::getStringId).collect(Collectors.toList());
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
    public List<AssetEntryRecordResponse> queryRecord(AssetEntryQuery query) {
        List<AssetEntryRecordResponse> result = assetEntryDao.queryEntryRecord(query);
        return result.isEmpty() ? result : result.stream().map(v -> {
                    v.setCreateUserName(v.getCreateUser() == 0 ? "系统" : queryUserName(v.getCreateUser()));
                    return v;
                }
        ).collect(Collectors.toList());
    }

    private String queryUserName(int userId) {
        //从redis上获取用户信息
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                userId);
        SysUser sysUser = null;
        try {
            sysUser = redisUtil.getObject(key, SysUser.class);
        } catch (Exception e) {
            logger.info("redis获取用户信息发生异常，用户id:{}", userId);
        }
        if (sysUser == null) {
            LogUtils.info(logger, "{}:该用户不存在", userId);
            return "";
        }
        return sysUser.getName();
    }

    private void sendMessage(List<String> ids, int entryStatus) {

        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            loginUser = new LoginUser();
            loginUser.setId(0);
        }

        if (CollectionUtils.isNotEmpty(ids)) {
            LoginUser finalLoginUser = loginUser;
            List<SysMessageRequest> msg = new ArrayList<>();
            List<Asset> assets = assetDao.queryAssetByIds(ids.toArray(new Integer[ids.size()]));

            String entryTag = "asset:admittance:accessManagement";
            Map<String, String> param = new HashMap<>();
            param.put("tag", entryTag);
            for (Asset asset : assets) {
                String areaId = asset.getAreaId();
                if (StringUtils.isNotBlank(loginUser.getUsername())) {
                    //todo 如果用户不存在 希望远程接口干掉加密
                    param.put("areaId", aesEncoder.encode(areaId, loginUser.getUsername()));
                }
                //获取该资产区域下拥有准入权限的所有用户
                ActionResponse<List<Map<String, Object>>> response = (ActionResponse<List<Map<String, Object>>>) client.post(param, new ParameterizedTypeReference() {
                }, getUesrByTagUrl);
                if (response == null && !StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
                    logger.info("请求url:{},参数：{}", getUesrByTagUrl, param);
                    throw new BusinessException("调用用户模块获取准入管理权限用户信息失败");
                }
                response.getBody().forEach(v -> {
                    SysMessageRequest request = new SysMessageRequest();
                    request.setTopic("代办任务");
                    request.setSummary("准入管理");
                    request.setContent(new StringBuilder("您有一条由")
                            .append(StringUtils.isEmpty(finalLoginUser.getName()) ? "系统" : finalLoginUser.getName())
                            .append("提交的下发[")
                            .append(EnumUtil.equals(entryStatus, AssetEnterStatusEnum.ENTERED) ? "允许入网" : "阻断入网")
                            .append("]指令失败，请尽快处理").toString());
                    request.setSendUserId(finalLoginUser.getId());
                    request.setOrigin(1);//资产管理来源
                    request.setReceiveUserId(Integer.valueOf(aesEncoder.decode((String) v.get("stringId"), finalLoginUser.getUsername())));
                    request.setOther("{\"id\":" + asset.getStringId() + "}");
                    msg.add(request);

                });
            }
            //发送消息
            ActionResponse response = messageSender.batchSendMessage(msg);
            if (response == null || StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("消息参数：{}", msg);
                throw new BusinessException("发送消息提醒失败");
            }
        }
    }

    @Override
    public List<AssetEntryStatusResponse> queryEntryStatus(List<String> assetIds) {
        return assetEntryDao.queryEntryStatus(assetIds);
    }

}
