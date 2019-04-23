package com.antiy.asset.support;

import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.OperationLog;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhouye
 * @date 2019-04-10
 * 单元测试上下文
 * 1.提供springboot test环境
 * 2.提供mock 当前登陆用户信息
 * 3.提供异常类断言工具
 * 4. 操作日志上报 默认不会产生实际调用
 * 5.mock Redis具体行为自己when
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockContext {
    @MockBean
    protected RedisUtil redisUtil;
    @MockBean(name = "operationLogKafkaTemplate")
    protected KafkaTemplate<String, OperationLog> operationLogKafkaTemplate;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    /**
     * 执行完本test Class后清除用户上下文
     * 因每个test class mock出的用户信息执行mvn test的时候会互相影响
     * 注意：如果子类需要覆盖@After标识的方法，一定得调用super.tearDown()
     * 注意：对于每一个test method执行后都会执行本方法
     */
    @After
    public void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * @param loginUser 假用户信息
     * @author gemingrun
     * 提供的当前用户信息mock
     */
    protected void mockLoginUser(LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("username", loginUser.getUsername());
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("name", loginUser.getName());
        map.put("password", loginUser.getPassword());
        Set<String> set = new HashSet<>(1);
        set.add("none");
        OAuth2Request oAuth2Request = new OAuth2Request(map, "1", null, true, set, null, "", null, null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(map, "2333");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("principal", map);
        authentication.setDetails(map1);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);

    }
}
