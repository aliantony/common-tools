package com.antiy.asset.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * 资产状态变更dao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetOperationRecordDaoTest {
	@Autowired AssetOperationRecordDao dao;

	@Test
	public void queryAssetAllStatusInfo() throws Exception{
//		dao.queryAssetAllStatusInfo("1").stream().forEach(e->{
//			System.out.println(e.getOriginStatus().describe(e.getProcessResult()));
//		});
		dao.queryAssetAllStatusInfo("1").stream().forEach(e->{
			System.out.println(e.getFileInfo());
		});
	}

	@Test
	public void queryAssetPreStatusInfo() {
		System.out.println(dao.queryAssetPreStatusInfo(Arrays.asList("1")));
	}
}
