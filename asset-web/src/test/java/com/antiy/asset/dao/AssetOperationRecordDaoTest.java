package com.antiy.asset.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * 资产状态变更dao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetOperationRecordDaoTest {
	@MockBean
	AssetOperationRecordDao dao;

	@Test
	public void queryAssetAllStatusInfo() throws Exception{

		dao.queryAssetAllStatusInfo("1").stream().forEach(e->{
			System.out.println(e);
		});
	}

	@Test
	public void queryAssetPreStatusInfo() {
		System.out.println(dao.queryAssetPreStatusInfo(Arrays.asList("1")));
	}
}
