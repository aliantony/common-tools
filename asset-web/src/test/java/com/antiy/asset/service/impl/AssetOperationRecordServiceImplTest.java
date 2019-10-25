package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Director;
import com.antiy.asset.manage.builder.RegisterBuilder;
import com.antiy.asset.manage.builder.on.line.OnCheckBuilder;
import com.antiy.asset.manage.builder.on.line.OnRetireBuilder;
import com.antiy.asset.manage.builder.under.line.UnderRetireBuilder;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.query.AssetHardSoftOperQuery;
import com.antiy.asset.vo.response.AssetAllTypeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.RespBasicCode;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author zhouye
 * 资产状态变更/cpe信息相关单元测试
 */
public class AssetOperationRecordServiceImplTest extends MockContext {
	@Resource
	private IAssetOperationRecordService recordService;
	@Resource
	private IAssetHardSoftLibService hardSoftLibService;
	@MockBean
	private AssetOperationRecordDao recordDao;
	@MockBean
	private AssetHardSoftLibDao hardSoftLibDao;
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 线下登记资产正向流程
	 */
	@Test()
	public void queryUnderLineAssetAllStatusInfo() throws Exception {
		Director director = new Director(new UnderRetireBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		statusDetails.get(0).setFileInfo("");
		given(recordDao.queryAssetAllStatusInfo("1")).willReturn(statusDetails);
		ActionResponse response = recordService.queryAssetAllStatusInfo("1");
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(jsonSuperHero.write(response).toString());
	}

	/**
	 * 线上登记资产正向流程
	 */
	@Test
	public void queryOnLineAslStatusInfo() {
		Director director = new Director(new OnRetireBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		given(recordDao.queryAssetAllStatusInfo("1")).willReturn(statusDetails);
		ActionResponse response = recordService.queryAssetAllStatusInfo("1");
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(response.getBody());
	}

	/**
	 * 上一步资产备注查询
	 */
	@Test
	public void batchQueryAssetPreStatusInfo() {
		Director director = new Director(new OnCheckBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		statusDetails.get(0).setFileInfo("");
		given(recordDao.queryAssetPreStatusInfo(Arrays.asList("1", "2"))).willReturn(new ArrayList<>(statusDetails));
		ActionResponse response = recordService.batchQueryAssetPreStatusInfo(Arrays.asList("1", "2"));
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}

	/**
	 * cpe信息查询-软硬操作系统
	 */
	@Test
	public void queryAssetList() throws Exception{
		AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
		query.setSupplier("antiy");
		//query.setProductName("ie");
		AssetHardSoftLib rowData = new AssetHardSoftLib();
		rowData.setBusinessId(RandomStringUtils.random(18));
		rowData.setNumber(1);
		rowData.setType(RandomStringUtils.random(10));
		rowData.setProductName(RandomStringUtils.random(18));
		rowData.setHardPlatform(RandomStringUtils.random(18));
		rowData.setLanguage(RandomStringUtils.random(18));
		rowData.setOther(RandomStringUtils.random(18));
		rowData.setSoftPlatform(RandomStringUtils.random(18));
		rowData.setSoftVersion(RandomStringUtils.random(18));
		rowData.setSupplier(RandomStringUtils.random(18));
		rowData.setUpgradeMsg(RandomStringUtils.random(18));
		rowData.setSysVersion(RandomStringUtils.random(18));
		ArrayList<AssetHardSoftLib> list = new ArrayList<>();
		list.add(rowData);
		given(hardSoftLibDao.queryAssetList(query)).willReturn(list);
		given(hardSoftLibDao.queryAssetListCount(query)).willReturn(1);
		ActionResponse<PageResult<AssetAllTypeResponse>> response = hardSoftLibService.queryAssetList(query);

		System.out.println(jsonSuperHero.write(response).toString());
	}

	/**
	 *
	 * 测试真实db数据-资产动态数量
	 * @throws Exception 业务异常
	 */
	@Test
	@Ignore
	public void queryDbAssetListCount() throws Exception{
		AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
		query.setProductName("1024");
		System.out.println(jsonSuperHero.write(hardSoftLibService.queryAssetList(query)).toString());
	}

	/**
	 *
	 * 测试真实db数据-资产动态
	 * @throws Exception 业务异常
	 */
	@Test
	@Ignore
	public void queryDbAssetList() {
		ActionResponse response = recordService.queryAssetAllStatusInfo("938");
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(response.getBody());
	}

	/**
	 * 测试真是db数据-上一步资产动态
	 */
	@Test
	public void queryDbPreList() {
		ActionResponse response = recordService.batchQueryAssetPreStatusInfo(Arrays.asList("148","143","142"));
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}
	/**
	 * 上一步资产备注查询-数据库中有数据未查出
	 */
	@Test
	public void batchQueryAssetPreStatusInfo_1() {
		Director director = new Director(new RegisterBuilder());
		List<AssetStatusDetail> statusDetails = director.construct().getProducts();
		statusDetails.addAll(director.construct().getProducts());
		statusDetails.get(0).setFileInfo("");
		statusDetails.get(0).setAssetId("2");
		given(recordDao.queryAssetPreStatusInfo(Arrays.asList("1", "2"))).willReturn(new ArrayList<>(statusDetails));
		ActionResponse response = recordService.batchQueryAssetPreStatusInfo(Arrays.asList("1", "2"));
		assertThat(response.getHead().getCode()).isEqualTo(RespBasicCode.SUCCESS.getResultCode());
		System.out.println(gson.toJson(response.getBody()));
	}
}