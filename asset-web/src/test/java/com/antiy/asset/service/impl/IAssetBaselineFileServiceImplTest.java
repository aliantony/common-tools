package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetBaselineFileDao;
import com.antiy.asset.service.IAssetBaselineFileService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.response.AssetBaselineFileResponse;
import com.antiy.common.base.ActionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
/**'资产-检查-结果验证流程关联资产是否已上传结果附件 单元测试
 * @author zhouye
 */
public class IAssetBaselineFileServiceImplTest extends MockContext {
	@Resource
	private IAssetBaselineFileService service;
	@MockBean
	private AssetBaselineFileDao dao;
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * 测试db数据用
	 * @throws Exception 业务异常
	 */
	@Test
	public void queryBaselineFileIsExist() throws Exception{
		System.out.println(jsonSuperHero.write(service.queryBaselineFileIsExist(Arrays.asList("","5"), AssetStatusEnum.WAIT_VALIDATE)));
	}

	/**
	 * 测试资产实施流程 当assetid 为5的资产关联1个未处理附件
	 * 断言result：true
	 */
	@Test
	public void queryBaselineFileIsExist_1 ()throws Exception {
		List<String> list = Arrays.asList("5", "6");
		given(dao.queryBaselineCheckFileIsExist("5")).willReturn(1);
		given(dao.queryBaselineCheckFileIsExist("6")).willReturn(0);
		ActionResponse<List<AssetBaselineFileResponse>> response = service.queryBaselineFileIsExist(list, AssetStatusEnum.WAIT_TEMPLATE_IMPL);
		List<AssetBaselineFileResponse> result = response.getBody();
		Assert.assertEquals(result.size(),2);
		Assert.assertTrue(result.get(0).getResult());
		Assert.assertFalse(result.get(1).getResult());
	}
	/**
	 * 测试资产实施流程 当assetid 为5的资产关联1个未处理附件 流程节点为待登记
	 * 断言result：false
	 */
	@Test
	public void queryBaselineFileIsExist_2() {
		List<String> list = Arrays.asList("5", "6");
		given(dao.queryBaselineCheckFileIsExist("5")).willReturn(1);
		given(dao.queryBaselineCheckFileIsExist("6")).willReturn(0);
		ActionResponse<List<AssetBaselineFileResponse>> response = service.queryBaselineFileIsExist(list, AssetStatusEnum.WAIT_REGISTER);
		List<AssetBaselineFileResponse> result = response.getBody();
		Assert.assertEquals(result.size(),2);
		Assert.assertFalse(result.get(0).getResult());
		Assert.assertFalse(result.get(1).getResult());
	}
}