package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.AssetStatusBarResponse;
import com.antiy.asset.vo.response.NameValueVo;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;

/**
 * <p> 资产操作记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetOperationRecordServiceImpl extends BaseServiceImpl<AssetOperationRecord>
                                             implements IAssetOperationRecordService {

    @Resource
    private AssetOperationRecordDao                                                   assetOperationRecordDao;
    @Resource
    private SchemeDao                                                                 schemeDao;
    @Resource
    private BaseConverter<AssetOperationRecordBarPO, AssetOperationRecordBarResponse> operationRecordBarPOToResponseConverter;

    @Override
    public List<NameValueVo> queryStatusBar(AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        List<NameValueVo> nameValueVoList = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();
        if (AssetOperationTableEnum.ASSET.getMsg().equals(assetOperationRecordQuery.getTargetType().getMsg())) {
            map.put("originStatus", AssetStatusEnum.WATI_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitRegist = new NameValueVo<>();
            waitRegist.setName(AssetStatusEnum.WATI_REGSIST.getMsg());
            waitRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitRegist);

            map.put("originStatus", AssetStatusEnum.NOT_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> notRegist = new NameValueVo<>();
            notRegist.setName(AssetStatusEnum.NOT_REGSIST.getMsg());
            notRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(notRegist);

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

            map.put("originStatus", AssetStatusEnum.WAIT_RETIRE.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitRetire = new NameValueVo<>();
            waitRetire.setName(AssetStatusEnum.WAIT_RETIRE.getMsg());
            waitRetire.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitRetire);

            map.put("originStatus", AssetStatusEnum.RETIRE.getCode());
            NameValueVo<AssetOperationRecordBarResponse> retire = new NameValueVo<>();
            retire.setName(AssetStatusEnum.RETIRE.getMsg());
            retire.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(retire);

        } else if (AssetOperationTableEnum.SOFTWARE.getMsg()
            .equals(assetOperationRecordQuery.getTargetType().getMsg())) {
            map.put("originStatus", SoftwareStatusEnum.WATI_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitRegist = new NameValueVo<>();
            waitRegist.setName(AssetStatusEnum.WATI_REGSIST.getMsg());
            waitRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitRegist);

            map.put("originStatus", SoftwareStatusEnum.NOT_REGSIST.getCode());
            NameValueVo<AssetOperationRecordBarResponse> notRegist = new NameValueVo<>();
            notRegist.setName(AssetStatusEnum.NOT_REGSIST.getMsg());
            notRegist.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(notRegist);

            map.put("originStatus", SoftwareStatusEnum.WAIT_ANALYZE.getCode());
            NameValueVo<AssetOperationRecordBarResponse> waitAnalyze = new NameValueVo<>();
            waitAnalyze.setName(SoftwareStatusEnum.WAIT_ANALYZE.getMsg());
            waitAnalyze.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(waitAnalyze);

            map.put("originStatus", SoftwareStatusEnum.ALLOW_INSTALL.getCode());
            NameValueVo<AssetOperationRecordBarResponse> allowInstall = new NameValueVo<>();
            allowInstall.setName(SoftwareStatusEnum.ALLOW_INSTALL.getMsg());
            allowInstall.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(allowInstall);

            map.put("originStatus", SoftwareStatusEnum.RETIRE.getCode());
            NameValueVo<AssetOperationRecordBarResponse> retire = new NameValueVo<>();
            retire.setName(SoftwareStatusEnum.ALLOW_INSTALL.getMsg());
            retire.setData(getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
            nameValueVoList.add(retire);

        } else {
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }

        return nameValueVoList;
    }

    private List<AssetOperationRecordBarResponse> getAssetOperationRecordBarResponses(HashMap<String, Object> map,
                                                                                      AssetOperationRecordQuery assetOperationRecordQuery) {
        assetOperationRecordQuery.setOriginStatus((Integer) map.get("originStatus"));

        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = assetOperationRecordDao
            .findAssetOperationRecordBarByAssetId(assetOperationRecordQuery);

        List<AssetOperationRecordBarResponse> assetOperationRecordBarResponseList = new ArrayList<>();
        for (AssetOperationRecordBarPO assetOperationRecordBarPO : assetOperationRecordBarPOList) {

            if (assetOperationRecordBarPO == null) {
                continue;
            } else {
                map.put("assetId", assetOperationRecordBarPO.getId());

                AssetOperationRecordBarResponse assetOperationRecordBarResponse = operationRecordBarPOToResponseConverter
                    .convert(assetOperationRecordBarPO, AssetOperationRecordBarResponse.class);

                List<Scheme> schemeList = schemeDao.findSchemeByAssetId(map);

                List<AssetStatusBarResponse> fileInfoList = new ArrayList<>();

                for (Scheme scheme : schemeList) {
                    AssetStatusBarResponse assetStatusBarResponse = new AssetStatusBarResponse();
                    assetStatusBarResponse.setFileInfo(scheme.getFileInfo());
                    assetStatusBarResponse.setMemo(scheme.getMemo());
                    fileInfoList.add(assetStatusBarResponse);
                }
                assetOperationRecordBarResponse.setFileInfos(fileInfoList);
                assetOperationRecordBarResponseList.add(assetOperationRecordBarResponse);
            }
        }

        return assetOperationRecordBarResponseList;
    }
}
