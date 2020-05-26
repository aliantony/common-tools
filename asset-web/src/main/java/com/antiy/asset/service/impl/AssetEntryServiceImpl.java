package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetEntryDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.templet.EntryRestore;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetEnterStatusEnum;
import com.antiy.asset.vo.enums.AssetEntrySourceEnum;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.AssetEnum;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
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
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@Service
public class AssetEntryServiceImpl implements iAssetEntryService {
    private Logger logger = LogUtils.get(this.getClass());
    @Resource
    private AssetEntryDao assetEntryDao;
    @Value("${entry.system.token.url}")
    private String entrySystemTokenUrl;
    @Value("${forbidden.entry.url}")
    private String forbiddenEntryUrl;
    @Value("${allow.entry.url}")
    private String allowEntryUrl;
    @Resource
    private AssetDao assetDao;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysMessageSender messageSender;
    @Resource
    private AssetCategoryModelServiceImpl categoryModelService;
    @Resource
    private AesEncoder aesEncoder;
    @Resource
    private BaseClient client;
    @Resource
    private EntryRestore entryRestore;
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

        //获取网络设备和计算设备乃至下级节点的id
        List<String> categoryIds = getCategoryIdsOfComputerAndNet();
        query.setAssetCategorys(categoryIds);
        if (CollectionUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        }
        int count = assetEntryDao.findCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Collections.EMPTY_LIST);
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.queryList(query));
    }

    public List<String> getCategoryIdsOfComputerAndNet() throws Exception {
        AssetCategoryModelQuery modelQuery = new AssetCategoryModelQuery();
       return categoryModelService.queryCategoryWithOutRootNode(modelQuery)
                .stream().filter(v -> Objects.equals("计算设备", v.getName()) || Objects.equals("网络设备", v.getName())).collect(
                        LinkedList::new, (list, v) -> getCategoryIds(v, list), List::addAll);
    }

    /**
     * 获取某节点（包含该节点)下的所有子节点的资产类型id
     * @param nodes
     * @return
     */
    private void getCategoryIds(AssetCategoryModelNodeResponse nodes,List<String> result )  {
        result.add(nodes.getStringId());
        if (CollectionUtils.isNotEmpty(nodes.getChildrenNode())) {
            for (AssetCategoryModelNodeResponse node : nodes.getChildrenNode()) {
                getCategoryIds(node,result);
            }
        }
    }
    public List<AssetEntryResponse> queryList(AssetEntryQuery query) throws Exception {
        return assetEntryDao.findQuery(query);
    }

    @Override
    public String updateEntryStatus(AssetEntryRequest request, SecurityContext... context) throws Exception {
        if (!checkAssetComplience(request)) {
            return "资产应属于计算设备、网络设备、非借用设备、非孤岛设备";
        }
        //token设置
        if (ArrayUtils.isNotEmpty(context)) {
            SecurityContextHolder.setContext(context[0]);
        }
        //准入状态一致不用下发，过滤当前资产准入状态与更新状态不一致的资产
        List<ActivityHandleRequest> activityHandleRequests = request.getAssetActivityRequests().stream().filter(v -> {
            if (EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.UNKNOWN_ASSET_REGISTER)
                    || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.ASSET_ENTER_NET)
                    || !assetDao.getByAssetId(String.valueOf(v.getId())).getAdmittanceStatus().equals(Integer.valueOf(request.getUpdateStatus()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<String> assetIds = activityHandleRequests.stream()
                .map(v -> String.valueOf(v.getId())).collect(Collectors.toList());
        if (activityHandleRequests.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            assetIds.forEach(v->stringBuilder.append(v).append(" "));
            logger.info("资产id:{}",stringBuilder.toString());
            return "";
        }
        request.setAssetActivityRequests(activityHandleRequests);

        //获取登录用户
        int userId = 0;//0表示系统
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (!Objects.isNull(loginUser)) {
            userId = loginUser.getId();
        }
        //recordRequestList 准入历史
        List<AssetEntryRecordRequest> recordRequestList = new ArrayList<>();
        //如果来源为准入实施且准入禁止，则只记录准入历史记录并设置status为0，方便准入管理列表按准入操作排序
        if (EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.ASSET_ENTER_NET)
                && AssetEnterStatusEnum.NO_ENTER.getCode().equals(Integer.valueOf(request.getUpdateStatus()))) {
            int finalUserId = userId;
            assetIds.forEach(v -> {
                Asset asset = assetDao.getByAssetId(v);
                AssetEntryRecordRequest recordRequest = new AssetEntryRecordRequest(asset.getStringId(), request.getEntrySource().getCode(),
                        0, Integer.valueOf(request.getUpdateStatus()), System.currentTimeMillis(), finalUserId
                );
                recordRequest.setStatus(0);
                recordRequestList.add(recordRequest);
            });
            //保存历史记录
            assetEntryDao.insertRecordBatch(recordRequestList);
            return "";
        }

        //errno 失败的资产数量
        int errno = 0;
        //准入系统不支持批量操作
        for (String v : assetIds) {
            //todo 下发指令 判断第三方是全部成功还是部分成功
            List<String> macs=assetDao.findMacByAssetId(v);
            if (CollectionUtils.isEmpty(macs)) {
                continue;
            }
            List<String> ip = assetDao.findIpByAssetId(v);
            String ips = null;
            if (CollectionUtils.isNotEmpty(ip)) {
                ips = String.join(",", ip);
            }
//            boolean isSuccess = entryControl(macs, Integer.valueOf(request.getUpdateStatus()),request.getEntrySource().getMsg(),ips);
            boolean isSuccess = true;

            //操作日志-安全事件
            boolean isAccess = EnumUtil.equals(Integer.valueOf(request.getUpdateStatus()), AssetEnterStatusEnum.ENTERED);
            String incident = isAccess ? "允许入网" : "禁止入网";
            Asset asset = assetDao.getByAssetId(v);
            //记录操作日志
            LogUtils.recordOperLog(new BusinessData(incident, asset.getId(), asset.getNumber(), asset,
                    BusinessModuleEnum.ACCESS_MANAGEMENT, isAccess ? BusinessPhaseEnum.ACCESS_ALLOW : BusinessPhaseEnum.ACCESS_BAN, AssetEnum.IS_ASSET_NO));

            AssetEntryRecordRequest recordRequest = new AssetEntryRecordRequest(asset.getStringId(), request.getEntrySource().getCode(),
                    isSuccess ? 1 : 0, Integer.valueOf(request.getUpdateStatus()), System.currentTimeMillis(), userId
            );
            recordRequestList.add(recordRequest);

            //保存历史记录
            assetEntryDao.insertRecordBatch(recordRequestList);
            //第N次循环,清空历史记录
            recordRequestList.clear();
            //失败发送系统消息
            if (BooleanUtils.isFalse(isSuccess)) {
                errno++;
                sendMessage(assetIds, Integer.valueOf(request.getUpdateStatus()));
            } else {

                //更新资产准入状态
                assetEntryDao.updateEntryStatus(request);
                //如果来源是漏洞扫描，补丁安装，配置扫描，启动自动恢复机制
                if ((EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.CONFIG_SCAN)
                        || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.VUL_SCAN)
                        || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.PATCH_INSTALL)
                        || EnumUtil.equals(request.getEntrySource().getCode(), AssetEntrySourceEnum.ASSET_CHANGE))
                        && (EnumUtil.equals(Integer.valueOf(request.getUpdateStatus()), AssetEnterStatusEnum.NO_ENTER))) {
                    //自动恢复为准入允许
                    request.setUpdateStatus(String.valueOf(AssetEnterStatusEnum.ENTERED.getCode()));
                    entryRestore.initRestoreRequest(request);
                }
            }
        }

        //全部失败，给与提示
        if (errno >= assetIds.size()) {
            throw new BusinessException("准入状态变更失败!");
        }
        return "变更成功";
    }

    private boolean entryControl(List<String> macs, Integer updateStatus,String msg,String ip) {
        //根据mac对资产进行准入控制
        for (String mac : macs) {
            EntrySystemForbiddenRequest request = new EntrySystemForbiddenRequest();
            request.setMac(mac);
            boolean isAllow = EnumUtil.equals(updateStatus, AssetEnterStatusEnum.ENTERED);
            if (!isAllow) {
                request.setMsg(msg);
                request.setIp(ip);
            }
           boolean isSuccess=sendCommond(request, isAllow);
            if (isSuccess) {
                return true;
            }
        }
        return false;
    }



    public boolean checkAssetComplience(AssetEntryRequest request) throws Exception {

        List<Integer> assetIds = request.getAssetActivityRequests().stream().map(ActivityHandleRequest::getId).collect(Collectors.toList());
        List<String> categoryIds = getCategoryIdsOfComputerAndNet();
        return assetDao.findByIds(assetIds).stream().filter(asset ->{
            Integer notOrphan = 2;
            Integer notBorrow = 2;
            return (notOrphan.equals(asset.getIsOrphan()) || asset.getIsOrphan()==null)
                    && ( notBorrow.equals(asset.getIsBorrow()) || asset.getIsBorrow()==null)
                    && categoryIds.contains(asset.getCategoryModel())
                    ;
        }).collect(Collectors.toList()).isEmpty()? false:true ;

    }
    //找出已经漏洞修复完成、补丁安装完成、配置完成的资产集合 返回未完成的资产
    public AssetEntryRequest scanTask(AssetEntryRequest request) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //removedRequest 不满足自动恢复准入要求的资产
        AssetEntryRequest removedRequest = (AssetEntryRequest) BeanUtils.cloneBean(request);
        removedRequest.setAssetActivityRequests(new ArrayList<>());
        removedRequest.setUpdateStatus(request.getUpdateStatus());

        List<ActivityHandleRequest> assets = new ArrayList<>(request.getAssetActivityRequests());
        Map<String, Object> param = new HashMap<>();
        for (ActivityHandleRequest asset : assets) {

            String assetId = String.valueOf(asset.getId());
            boolean isSuccess = true;
            boolean isChecked = false;
            while (!isChecked) {
                isChecked = true;
                //查询漏洞是否修复完成
                param.put("stringId", assetId);
                ActionResponse<Integer> vulResponse = (ActionResponse<Integer>) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
                }, vulCompleteUrl);
                if (vulResponse == null || !vulResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    logger.info("获取资产漏洞修复完成情况失败");
                    isSuccess = false;
                    break;
                } else {
                    Integer vulCount = vulResponse.getBody();
                    if (vulCount > 0) {
                        isSuccess = false;
                        break;
                    }
                }
                //查询补丁是否安装完成
                ActionResponse<Integer> patchResponse = (ActionResponse<Integer>) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
                }, patchInstallCompleteUrl);
                if (patchResponse == null || !patchResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    logger.info("获取资产补丁安装完成情况失败");
                    isSuccess = false;
                    break;
                } else {
                    Integer patchCount = patchResponse.getBody();
                    if (patchCount > 0) {
                        isSuccess = false;
                        break;
                    }
                }
                //查询配置是否完成
                param.put("assetId", assetId);
                ActionResponse<Boolean> configResponse = (ActionResponse<Boolean>) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
                }, configCompleteUrl);
                if (configResponse == null || !configResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    logger.info("获取资产配置完成情况失败");
                    isSuccess = false;
                    break;
                } else {
                    boolean isConfigOver = configResponse.getBody();
                    if (!isConfigOver) {
                        isSuccess = false;
                        break;
                    }
                }
            }
            if (!isSuccess) {
                //保存没有成功的，待下一次尝试
                removedRequest.getAssetActivityRequests().add(asset);
                //移除没有成功的
                request.getAssetActivityRequests().remove(asset);
            }
        }
        return removedRequest;
    }

    private boolean sendCommond(EntrySystemRequest request,boolean isAllow) {
        //todo 测试是否可以下发指令通信
        int count = 0;
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

        EntrySystemResponse response = null;
        //errno 错误码,0表示没有错
        Integer errno = 0;
        //下发指令
        do {
            count++;
            try {
                //获取token
                EntrySystemResponse tokenResponse = restTemplate.getForObject(entrySystemTokenUrl, EntrySystemResponse.class);
                if (Objects.isNull(tokenResponse) || !Objects.equals(errno, tokenResponse.getErrno())) {
                    LogUtils.info(logger,"获取准入系统token失败:{}",tokenResponse);
                    continue;
                }
                request.setToken(tokenResponse.getToken());

                //准入允许
                if (isAllow) {
                    response=restTemplate.postForObject(allowEntryUrl, request, EntrySystemResponse.class);
                }else {
                    EntrySystemForbiddenRequest forbiddenRequest = (EntrySystemForbiddenRequest) request;
                    response=restTemplate.postForObject(forbiddenEntryUrl, forbiddenRequest, EntrySystemResponse.class);
                }

            } catch (Exception e) {
                logger.warn("指令第{}次下发失败，代码异常：{}", count, e.getMessage());
                continue;
            }
            //下发失败，继续下发指令
            if (response == null || !Objects.equals(errno, response.getErrno())) {
                logger.info("指令第{}次下发失败,response:{}", response);
                continue;
            }
                break;


        } while (count < 2);
        if (response == null || !Objects.equals(errno, response.getErrno())) {
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
            List<Asset> assets = assetDao.queryAssetByIds(ids.stream().map(v -> Integer.valueOf(v)).toArray(Integer[]::new));

            String entryTag = "asset:admittance";
            Map<String, String> param = new HashMap<>();
            param.put("tag", entryTag);
            for (Asset asset : assets) {
                String areaId = asset.getAreaId();
                if (StringUtils.isNotBlank(loginUser.getUsername())) {
                    param.put("areaId", aesEncoder.encode(areaId, loginUser.getUsername()));
                } else {
                    param.put("areaId",areaId);
                }

                //获取该资产区域下拥有准入权限的所有用户
                ActionResponse<List<Map<String, Object>>> response = (ActionResponse<List<Map<String, Object>>>) client.post(param, new ParameterizedTypeReference<ActionResponse>() {
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
                            .append("提交的[准入变更]任务失败，请尽快处理").toString());
                    request.setSendUserId(finalLoginUser.getId());
                    request.setOrigin(1);//资产管理来源
                    request.setReceiveUserId(Integer.valueOf(aesEncoder.decode((String) v.get("stringId"), finalLoginUser.getUsername())));
                    request.setOther("{\"id\":" + asset.getStringId() + "}");
                    msg.add(request);

                });
            }
            //发送消息
            ActionResponse response = messageSender.batchSendMessage(msg);
            if (response == null || !StringUtils.equals(response.getHead().getCode(), RespBasicCode.SUCCESS.getResultCode())) {
                logger.info("消息参数：{}", msg);
                throw new BusinessException("调用消息模块，发送准入变更失败消息异常");
            }
        }
    }

    @Override
    public List<AssetEntryStatusResponse> queryEntryStatus(List<String> assetIds) {
        return assetEntryDao.queryEntryStatus(assetIds);
    }

    @Override
    public boolean queryEntryOperation(AssetEntryQuery query) throws Exception {
        //网络设备和计算设备乃至下级节点的类型id
        List<String> categoryIds = getCategoryIdsOfComputerAndNet();
        List<Asset> assets= assetDao.findByIds(Stream.of(query.getAssetIds()).map(id-> DataTypeUtils.stringToInteger(id)).collect(Collectors.toList()));
        Integer notOrphan = 2;
        Integer notBorrow = 2;
        for (Asset asset : assets) {
            if (categoryIds.contains(asset.getCategoryModel())
                    && EnumUtil.equals(asset.getAdmittanceStatus(), AssetEnterStatusEnum.ENTERED)
                    && (notOrphan.equals(asset.getIsOrphan()) || asset.getIsOrphan() == null)
                    && (notBorrow.equals(asset.getIsBorrow()) || asset.getIsBorrow() == null)
            ) {
                return true;
            }
        }
        return false;
    }
}