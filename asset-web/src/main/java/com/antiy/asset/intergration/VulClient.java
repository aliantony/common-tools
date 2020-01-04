package com.antiy.asset.intergration;

import com.antiy.asset.vo.request.AssetIdRequest;
import com.antiy.asset.vo.response.AssetVulPatchResponse;
import com.antiy.common.base.ActionResponse;

public interface VulClient {
    AssetVulPatchResponse getVulPatchCount(AssetIdRequest assetIdRequest);
}
