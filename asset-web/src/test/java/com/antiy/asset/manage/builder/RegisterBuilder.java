package com.antiy.asset.manage.builder;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
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
		template.setProcessResult("1");
		template.setGmtCreate(1L);
		template.setNote("备注");
		AssetStatusDetail registerProduce = new AssetStatusDetail();
		BeanUtils.copyProperties(template,registerProduce);
		registerProduce.setOriginStatus(AssetChangeDetailEnum.OPERATION_HARDWARE_REGISTER);
		assetChangeList.add(registerProduce);
		products.setProducts(assetChangeList);
	}
}
