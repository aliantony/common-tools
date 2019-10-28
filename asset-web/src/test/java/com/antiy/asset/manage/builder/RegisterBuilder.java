package com.antiy.asset.manage.builder;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetProcessResultEnum;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class RegisterBuilder extends Builder {
	@Override
	public void builder() {
		List<AssetStatusDetail> assetChangeList = new ArrayList<>();
		AssetStatusDetail template = new AssetStatusDetail();
		template.setId("1");
		template.setAssetId("1");
		template.setOperateUserId(1);
		template.setOperateUserName("zzz");
		template.setProcessResult(AssetProcessResultEnum.PROCESS_ADOPT);
		template.setGmtCreate(1L);
		template.setNote("备注");
		template.setContent(AssetFlowEnum.REGISTER.getMsg());
		template.setFileInfo("[{&quot;fileName&quot;:&quot;工作簿1.xlsx&quot;,&quot;url&quot;:&quot;/asset/20190419/499e415c73714c978fd4770f6bef9245.xlsx&quot;},{&quot;fileName&quot;:&quot;工作簿1.xlsx&quot;,&quot;url&quot;:&quot;/asset/20190419/499e415c73714c978fd4770f6bef9245.xlsx&quot;}]");
		AssetStatusDetail registerProduce = new AssetStatusDetail();
		BeanUtils.copyProperties(template,registerProduce);
		registerProduce.setOriginStatus(AssetChangeDetailEnum.OPERATION_NO);
		assetChangeList.add(registerProduce);
		products.setProducts(assetChangeList);
	}
}
