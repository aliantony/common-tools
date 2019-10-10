package com.antiy.asset.dao;

import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetHardSoftOperQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhouye
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HardSoftLibDaoTest {
	@Resource
	private AssetHardSoftLibDao dao;

	@Test
	public void queryHardSoftLibList() {
		AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
		query.setProductName("1024");
		System.out.println(dao.queryHardSoftLibList(query));
	}

	@Test
	public void queryAssetList() {
		AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
		query.setProductName("1024");
		System.out.println(dao.queryAssetList(query).toString());
		System.out.println(dao.queryAssetList(query).size());

	}
	@Test
	public void queryAssetListCount() {
		AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
		query.setProductName("1024");
		System.out.println(dao.queryAssetListCount(query));

	}
}
