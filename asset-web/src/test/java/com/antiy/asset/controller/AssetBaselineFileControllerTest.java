package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetBaselineFileService;
import com.antiy.asset.vo.query.AssetBaselineFileQuery;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**'资产-检查-结果验证流程关联资产是否已上传结果附件 单元测试
 * @author zhouye
 */
public class AssetBaselineFileControllerTest {
	private final static String       URL_PREFIX = "/api/v1/asset/baselineFile";
	@Resource
	private AssetBaselineFileController controller;
	@MockBean
	private IAssetBaselineFileService service;
	/*@Resource
	private SoftwareControllerManager controllerManager;*/
	/*@Resource
	private CommonManager commonManager;*/
	private MockMvc mvc;
	@Resource
	private AesEncoder encoder;
	private LoginUser loginUser;
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		loginUser = new LoginUser();
		loginUser.setUsername("test");
		loginUser.setPassword("test");
		//mockLoginUser(loginUser);
	}

	@Test
	public void queryBaselineFileIsExist() throws Exception{
		AssetBaselineFileQuery query = new AssetBaselineFileQuery();
		List<String> ids = new ArrayList<>();
		for (String id : Arrays.asList("5", "6")) {
			ids.add(encoder.encode(id, loginUser.getPassword()));
		}
		query.setIds(ids);
		/*query.setType(AssetStatusEnum.WAIT_TEMPLATE_IMPL);
		String result = commonManager.getResult(mvc, commonManager.postAction(URL_PREFIX + "/query", query));
		System.out.println(result);*/
	}
}