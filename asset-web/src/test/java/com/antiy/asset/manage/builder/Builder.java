package com.antiy.asset.manage.builder;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetProcessResultEnum;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zhouye
 * 资产动态构造器
 */
public abstract class Builder {
	protected Product products = new Product();

	/**
	 * 构造资产动态
	 */
	public abstract void builder();
	/**
	 * 返回产品对象
	 */
	public Product getProducts() {
		return products;
	}

	/**
	 * 构造下一个节点
	 */
	public List<AssetStatusDetail> next(Builder preBuilder, AssetChangeDetailEnum preEnum,Boolean preResult,Boolean nextResult,String content) {
		Director director = new Director(preBuilder);
		List<AssetStatusDetail> res = director.construct().getProducts();
		AssetStatusDetail template = res.get(0);
		AssetStatusDetail nextDetail = new AssetStatusDetail();
		BeanUtils.copyProperties(template, nextDetail);
		nextDetail.setOriginStatus(preEnum);
		nextDetail.setGmtCreate(nextDetail.getGmtCreate()+100);
		template.setProcessResult(preResult ? AssetProcessResultEnum.PROCESS_ADOPT : AssetProcessResultEnum.PROCESS_NOT_PASS);
		nextDetail.setProcessResult(nextResult ? AssetProcessResultEnum.PROCESS_ADOPT : AssetProcessResultEnum.PROCESS_NOT_PASS);
		nextDetail.setContent(content);
		res.add(nextDetail);
		res.sort((o1,o2)-> o2.getGmtCreate().compareTo(o1.getGmtCreate()));
		return res;
	}
}
