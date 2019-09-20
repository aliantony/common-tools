package com.antiy.asset.util;

import com.antiy.common.encoder.AesEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DecryptIdTest {
	@Autowired
	private AesEncoder encoder;
	@Test
	public void decryptId() {
		System.out.println(encoder.encode("1","xiaohuilan"));
	}
}
