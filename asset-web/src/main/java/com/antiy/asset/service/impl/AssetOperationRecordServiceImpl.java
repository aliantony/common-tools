package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import org.apache.hadoop.mapred.IFile;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.AssetStatusBarResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;

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
    public Map<String, List<AssetOperationRecordBarResponse>> queryStatusBar(AssetOperationRecordQuery assetOperationRecordQuery) throws Exception {
        Map<String, List<AssetOperationRecordBarResponse>> statusBarMap = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        if (AssetOperationTableEnum.ASSET.getMsg().equals(assetOperationRecordQuery.getTargetType().getMsg())){
            map.put("originStatus", AssetStatusEnum.WATI_REGSIST.getCode());
            statusBarMap.put(AssetStatusEnum.WATI_REGSIST.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.NOT_REGSIST.getCode());
            statusBarMap.put(AssetStatusEnum.NOT_REGSIST.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.WAIT_SETTING.getCode());
            statusBarMap.put(AssetStatusEnum.WAIT_SETTING.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.WAIT_VALIDATE.getCode());
            statusBarMap.put(AssetStatusEnum.WAIT_VALIDATE.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.WAIT_NET.getCode());
            statusBarMap.put(AssetStatusEnum.WAIT_NET.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.WAIT_CHECK.getCode());
            statusBarMap.put(AssetStatusEnum.WAIT_CHECK.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.NET_IN.getCode());
            statusBarMap.put(AssetStatusEnum.NET_IN.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.WAIT_RETIRE.getCode());
            statusBarMap.put(AssetStatusEnum.WAIT_RETIRE.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", AssetStatusEnum.RETIRE.getCode());
            statusBarMap.put(AssetStatusEnum.RETIRE.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
        }else if (AssetOperationTableEnum.SOFTWARE.getMsg().equals(assetOperationRecordQuery.getTargetType().getMsg())){
            map.put("originStatus", SoftwareStatusEnum.WATI_REGSIST.getCode());
            statusBarMap.put(SoftwareStatusEnum.WATI_REGSIST.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", SoftwareStatusEnum.NOT_REGSIST.getCode());
            statusBarMap.put(SoftwareStatusEnum.NOT_REGSIST.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", SoftwareStatusEnum.WAIT_ANALYZE.getCode());
            statusBarMap.put(SoftwareStatusEnum.WAIT_ANALYZE.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", SoftwareStatusEnum.ALLOW_INSTALL.getCode());
            statusBarMap.put(SoftwareStatusEnum.ALLOW_INSTALL.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));

            map.put("originStatus", SoftwareStatusEnum.RETIRE.getCode());
            statusBarMap.put(SoftwareStatusEnum.RETIRE.getMsg(),
                    getAssetOperationRecordBarResponses(map, assetOperationRecordQuery));
        }else {
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }

        return statusBarMap;
    }

    private List<AssetOperationRecordBarResponse> getAssetOperationRecordBarResponses(HashMap<String, Object> map,
                                                                                      AssetOperationRecordQuery assetOperationRecordQuery) {
        assetOperationRecordQuery.setOriginStatus((Integer) map.get("originStatus"));

        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = assetOperationRecordDao
            .findAssetOperationRecordBarByAssetId(assetOperationRecordQuery);

        List<AssetOperationRecordBarResponse> assetOperationRecordBarResponseList = new ArrayList<>();
        for (AssetOperationRecordBarPO assetOperationRecordBarPO : assetOperationRecordBarPOList) {

            if (assetOperationRecordBarPO == null){
                continue;
            }else {
                map.put("assetId", assetOperationRecordBarPO.getId());

                AssetOperationRecordBarResponse assetOperationRecordBarResponse = operationRecordBarPOToResponseConverter.convert(assetOperationRecordBarPO,AssetOperationRecordBarResponse.class);

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
