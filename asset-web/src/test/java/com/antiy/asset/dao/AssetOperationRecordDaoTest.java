package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetStatusDetail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 资产状态变更dao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class AssetOperationRecordDaoTest {
	@MockBean
	private AssetOperationRecordDao dao;

	@Test
	public void queryAssetAllStatusInfo() throws Exception{
		List<AssetStatusDetail> details = dao.queryAssetAllStatusInfo("143");

		details.stream().forEach(e->{
			System.out.println(e);
		});
	}

	@Test
	public void queryAssetPreStatusInfo() {
		System.out.println(dao.queryAssetPreStatusInfo(Arrays.asList("1")));
	}
}
