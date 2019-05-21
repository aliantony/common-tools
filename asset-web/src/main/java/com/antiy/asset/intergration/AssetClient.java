package com.antiy.asset.intergration;

import com.antiy.asset.vo.request.AssetExternalRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.common.base.ActionResponse;

import java.util.List;

/**
 * 资产对外接口
 * @author lvliang
 * @date
 */
public interface AssetClient {

    /**
     * 下发资产数据至智甲
     * @param assetExternalRequestList
     * @return
     */
    ActionResponse issueAssetData(List<AssetExternalRequest> assetExternalRequestList);

    /**
     * 下发软件资产数据至智甲
     * @param assetSoftwareRequestList
     * @return
     */
    ActionResponse issueSoftData(List<AssetSoftwareRequest> assetSoftwareRequestList);
}
