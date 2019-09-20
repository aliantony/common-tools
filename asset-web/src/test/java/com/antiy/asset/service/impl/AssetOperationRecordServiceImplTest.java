package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.entity.AssetStatusNote;
import com.antiy.asset.manage.builder.Director;
import com.antiy.asset.manage.builder.on.line.OnWaitCheckBuilder;
import com.antiy.asset.manage.builder.under.line.UnderInNetBuilder;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.support.MockContext;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;


public class AssetOperationRecordServiceImplTest extends MockContext {
	@Resource
	private IAssetOperationRecordService service;
	@MockBean
	private AssetOperationRecordDao dao;
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 线下登记资产正向流程
	 */
	@Test
	public void queryUnderLineAssetAllStatusInfo() {
		Director director = new Director(new UnderInNetBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		given(dao.queryAssetAllStatusInfo("1")).willReturn(statusDetails);
		ActionResponse response = service.queryAssetAllStatusInfo("1");
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}
	/**
	 * 线上登记资产正向流程
	 */
	@Test
	public void queryOnLineAslStatusInfo() {
		Director director = new Director(new OnWaitCheckBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		given(dao.queryAssetAllStatusInfo("1")).willReturn(statusDetails);
		ActionResponse response = service.queryAssetAllStatusInfo("1");
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}

	/**
	 * 上一步资产备注查询
	 */
	@Test
	public void batchQueryAssetPreStatusInfo() {
		Director director = new Director(new OnWaitCheckBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		given(dao.queryAssetPreStatusInfo(Arrays.asList("1", "2"))).willReturn(new ArrayList<>(statusDetails));
		ActionResponse response = service.batchQueryAssetPreStatusInfo(Arrays.asList("1", "2"));
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}
}