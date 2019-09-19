package com.antiy.asset.service.impl;

import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.support.MockContext;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;


public class AssetOperationRecordServiceImplTest extends MockContext {
	@Resource
	private IAssetOperationRecordService service;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void queryAssetAllStatusInfo() {
		service.queryAssetAllStatusInfo("1");
		System.out.println();
	}
}