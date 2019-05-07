package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.impl.SysRoleClientImpl;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.AssetStatusBarResponse;
import com.antiy.asset.vo.response.NameValueVo;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 资产操作记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetOperationRecordServiceImpl extends BaseServiceImpl<AssetOperationRecord>
                                             implements IAssetOperationRecordService {
    private Logger                                                                    logger = LogUtils
        .get(this.getClass());
    @Resource
    private AssetOperationRecordDao                                                   assetOperationRecordDao;
    @Resource
    private SchemeDao                                                                 schemeDao;
    @Resource
    private BaseConverter<AssetOperationRecordBarPO, AssetOperationRecordBarResponse> operationRecordBarPOToResponseConverter;
    @Resource
    private RedisUtil                                                                 redisUtil;
    @Resource
    SysRoleClientImpl                                                                 roleClient;

    @Override
    public List<NameValueVo> queryStatusBar(AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {

        List<NameValueVo> nameValueVoList = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();
        if (AssetOperationTableEnum.ASSET.getMsg().equals(assetOperationRecordQuery.getTargetType().getMsg())) {
            map.put("originStatus", AssetStatusEnum.WATI_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitRegist = new NameValueVo<>();
            waitRegist.setName(AssetStatusEnum.WATI_REGSIST.getMsg());
            waitRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitRegist);

            map.put("originStatus", AssetStatusEnum.WAIT_SETTING.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitSetting = new NameValueVo<>();
            waitSetting.setName(AssetStatusEnum.WAIT_SETTING.getMsg());
            waitSetting.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitSetting);

            map.put("originStatus", AssetStatusEnum.WAIT_VALIDATE.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitVAlidate = new NameValueVo<>();
            waitVAlidate.setName(AssetStatusEnum.WAIT_VALIDATE.getMsg());
            waitVAlidate.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitVAlidate);

            map.put("originStatus", AssetStatusEnum.WAIT_NET.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitNet = new NameValueVo<>();
            waitNet.setName(AssetStatusEnum.WAIT_NET.getMsg());
            waitNet.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitNet);

            map.put("originStatus", AssetStatusEnum.WAIT_CHECK.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitCheck = new NameValueVo<>();
            waitCheck.setName(AssetStatusEnum.WAIT_CHECK.getMsg());
            waitCheck.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitCheck);

            map.put("originStatus", AssetStatusEnum.NET_IN.getCode());
            NameValueVo<AssetOperationRecordBarResponse> netIn = new NameValueVo<>();
            netIn.setName(AssetStatusEnum.NET_IN.getMsg());
            netIn.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(netIn);

            map.put("originStatus", AssetStatusEnum.NOT_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> notRegist = new NameValueVo<>();
            notRegist.setName(AssetStatusEnum.NOT_REGSIST.getMsg());
            notRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(notRegist);

        } else if (AssetOperationTableEnum.SOFTWARE.getMsg()
            .equals(assetOperationRecordQuery.getTargetType().getMsg())) {
            map.put("originStatus", SoftwareStatusEnum.WATI_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitRegist = new NameValueVo<>();
            waitRegist.setName(AssetStatusEnum.WATI_REGSIST.getMsg());
            waitRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitRegist);

            map.put("originStatus", SoftwareStatusEnum.ALLOW_INSTALL.getCode());
            NameValueVo<AssetOperationRecordBarResponse> allowInstall = new NameValueVo<>();
            allowInstall.setName(SoftwareStatusEnum.ALLOW_INSTALL.getMsg());
            allowInstall.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(allowInstall);

            map.put("originStatus", SoftwareStatusEnum.NOT_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> notRegist = new NameValueVo<>();
            notRegist.setName(AssetStatusEnum.NOT_REGSIST.getMsg());
            notRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(notRegist);

        } else {
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }

        return nameValueVoList;
    }

    /**
     * 获取每个状态对应的操作记录
     * @param map
     * @param assetOperationRecordQuery
     * @return
     */
    private List<AssetOperationRecordBarResponse> getAssetOperationRecordBarResponses(HashMap<String, Object> map,
                                                                                      AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {
        assetOperationRecordQuery
            .setOriginStatus(map.get("originStatus") != null ? map.get("originStatus").toString() : null);
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = assetOperationRecordDao
            .findAssetOperationRecordBarByAssetId(assetOperationRecordQuery);

        List<AssetOperationRecordBarResponse> assetOperationRecordBarResponseList = new ArrayList<>();
        buidOperationRecordBarResponse(map, assetOperationRecordBarPOList, assetOperationRecordBarResponseList);

        return assetOperationRecordBarResponseList;
    }

    private void buidOperationRecordBarResponse(HashMap<String, Object> map,
                                                List<AssetOperationRecordBarPO> assetOperationRecordBarPOList,
                                                List<AssetOperationRecordBarResponse> assetOperationRecordBarResponseList) {

        assetOperationRecordBarPOList.forEach(operationRecordBarPo -> {
            // 获取操作人员角色信息
            // List<LinkedHashMap> linkedHashMapList =
            // (List<LinkedHashMap>)roleClient.getInvokeResult(operationRecordBarPo.getOperateUserId().toString());
            // if (linkedHashMapList != null && linkedHashMapList.size() > 0){
            // operationRecordBarPo.setRoleName(linkedHashMapList.get(0).get("name").toString());
            // }

        });

        for (AssetOperationRecordBarPO assetOperationRecordBarPO : assetOperationRecordBarPOList) {
            if (assetOperationRecordBarPO != null) {
                map.put("assetId", assetOperationRecordBarPO.getId());
                map.put("gmtCreateTime", assetOperationRecordBarPO.getGmtCreate());

                AssetOperationRecordBarResponse assetOperationRecordBarResponse = operationRecordBarPOToResponseConverter
                    .convert(assetOperationRecordBarPO, AssetOperationRecordBarResponse.class);
                String processResult = assetOperationRecordBarResponse.getProcessResult().equals(1) ? "通过" : "不通过";
                assetOperationRecordBarResponse
                    .setContent(assetOperationRecordBarResponse.getContent() + "验证情况：" + processResult);

                List<Scheme> schemeList = schemeDao.findSchemeByAssetIdAndGmtCreateTime(map);

                List<AssetStatusBarResponse> fileInfoList = new ArrayList<>();

                for (Scheme scheme : schemeList) {
                    AssetStatusBarResponse assetStatusBarResponse = new AssetStatusBarResponse();
                    if (scheme.getFileInfo() != null && scheme.getFileInfo().length() > 0) {
                        JSONObject.parse(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
                        assetStatusBarResponse.setFileInfo(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
                    }
                    assetStatusBarResponse.setMemo(scheme.getMemo());
                    fileInfoList.add(assetStatusBarResponse);
                }
                assetOperationRecordBarResponse.setFileInfos(fileInfoList);
                assetOperationRecordBarResponseList.add(assetOperationRecordBarResponse);
            }
        }
    }

    @Override
    public List<AssetOperationRecordBarResponse> queryStatusBarOrderByTime(AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = assetOperationRecordDao
            .findAssetOperationRecordBarByAssetId(assetOperationRecordQuery);
        List<AssetOperationRecordBarResponse> assetOperationRecordBarResponseList = new ArrayList<>();
        buidOperationRecordBarResponse(map, assetOperationRecordBarPOList, assetOperationRecordBarResponseList);
        return assetOperationRecordBarResponseList;
    }
}
