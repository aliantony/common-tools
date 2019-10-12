package com.antiy.asset.manage.builder;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
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
	public List<AssetStatusDetail> next(Builder preBuilder, AssetChangeDetailEnum nextEnum,Boolean preResult,Boolean nextResult) {
		Director director = new Director(preBuilder);
		List<AssetStatusDetail> res = director.construct().getProducts();
		AssetStatusDetail template = res.get(0);
		AssetStatusDetail nextDetail = new AssetStatusDetail();
		BeanUtils.copyProperties(template, nextDetail);
		nextDetail.setOriginStatus(nextEnum);
		nextDetail.setGmtCreate(nextDetail.getGmtCreate()+100);
		template.setProcessResult(preResult ? "1" : "0");
		nextDetail.setProcessResult(nextResult ? "1" : "0");
		res.add(nextDetail);
		res.sort((o1,o2)-> o2.getGmtCreate().compareTo(o1.getGmtCreate()));
		return res;
	}
}
