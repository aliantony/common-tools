package com.antiy.asset.manage;

import com.antiy.common.base.ActionResponse;
import org.junit.Assert;
import org.springframework.stereotype.Component;

/**
 * @author zhouye
 * @date 2019-04-03 断言辅助工具
 */
@Component
public class AssertManager {
    public final static String EXPECTED_1 = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}";
    public final static String EXPECTED_2 = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":[]}";
    public final static String EXPECTED_3 = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":0}";

    /**
     * 判断service方法执行返回的ActionResopnse是否是成功状态，通过对比head的code
     *
     * @param message 提示消息
     * @param actual 实际结果
     */
    public void assertSuccResponse(String message, ActionResponse actual) {
        Assert.assertEquals(message, ActionResponse.success().getHead().getCode(), actual.getHead().getCode());
    }

    /**
     * 判断service方法执行返回的ActionResopnse是否是成功状态，通过对比head的code
     *
     * @param message 提示消息
     * @param actual 实际结果，字符串
     */
    public void assertSuccResponse(String message, String actual) {
        Assert.assertEquals(message, EXPECTED_2, actual);
    }
}
