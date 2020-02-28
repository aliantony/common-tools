package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetBaselineFileDao;
import com.antiy.asset.service.IAssetBaselineFileService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.response.AssetBaselineFileResponse;
import com.antiy.common.base.ActionResponse;

/**'资产-检查-结果验证流程关联资产是否已上传结果附件
 * @author zhouye
 */
@Service
public class IAssetBaselineFileServiceImpl implements IAssetBaselineFileService {
	@Resource
	private AssetBaselineFileDao dao;
	@Override
	public ActionResponse<List<AssetBaselineFileResponse>> queryBaselineFileIsExist(List<String> ids, AssetStatusEnum type) {
		ArrayList<AssetBaselineFileResponse> dataList = new ArrayList<>();
		for (String id :
				ids) {
			if (StringUtils.isEmpty(id)) {
				continue;
			}
			AssetBaselineFileResponse rowData = new AssetBaselineFileResponse();
			rowData.setAssetId(id);
			int fileSum =0;
            // TODO
            // if (AssetStatusEnum.WAIT_TEMPLATE_IMPL.equals(type)) {
            // fileSum = dao.queryBaselineCheckFileIsExist(id);
            // } else if (AssetStatusEnum.WAIT_VALIDATE.equals(type)) {
            // fileSum = dao.queryBaselineValidateFileIsExist(id);
            // }

			if (fileSum > 0) {
				rowData.setResult(true);
			}
			dataList.add(rowData);
		}
		return ActionResponse.success(dataList);
	}


}
