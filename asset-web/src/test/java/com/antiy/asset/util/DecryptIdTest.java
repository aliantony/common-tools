package com.antiy.asset.util;

import com.antiy.common.encoder.AesEncoder;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.HtmlUtils;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DecryptIdTest {
	@Autowired
	private AesEncoder encoder;
	@Autowired private StringEncryptor stringEncryptor;
	@Test
	public void decryptId() {
		System.out.println(encoder.encode("82","xiaohuilan"));
	}

	@Test
	public void decode() {
		System.out.println(encoder.decode("US5wuxRxS5OejgGSEiE+zg==","lijian2"));
	}

	@Test
	public void name() {

		//解密方法
		System.out.println(stringEncryptor.decrypt("eil3/6hXXkRtbnx8DwzN0SBkzdYcoA4l7rv479XG3Vk="));
	}

	@Test
	public void fileInfo() {
		String fileUrl="[{&quot;fileName&quot;:&quot;工作簿1.xlsx&quot;,&quot;url&quot;:&quot;/asset/20190419/499e415c73714c978fd4770f6bef9245.xlsx&quot;},{&quot;fileName&quot;:&quot;工作簿1.xlsx&quot;,&quot;url&quot;:&quot;/asset/20190419/499e415c73714c978fd4770f6bef9245.xlsx&quot;}]";
		System.out.println(HtmlUtils.htmlUnescape(fileUrl));
	}
}
