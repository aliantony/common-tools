package com.antiy.asset.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
public class AssetBaselineFileDaoTest {
	@MockBean
	private AssetBaselineFileDao dao;

	@Test
	public void queryBaselineIsExist() {
		System.out.println(dao.queryBaselineCheckFileIsExist("5"));
	}

	@Test
	public void queryBaselineValidateFileIsExist() {
		System.out.println(dao.queryBaselineValidateFileIsExist("5"));
	}
}
