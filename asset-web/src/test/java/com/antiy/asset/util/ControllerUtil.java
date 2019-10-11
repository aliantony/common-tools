package com.antiy.asset.util;

import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.JsonUtil;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControllerUtil {
    /**
     * 单元测试解析controller返回response
     * @param result controller 请求返回体
     * @param <T> 响应实体
     * @return
     * @throws UnsupportedEncodingException
     */
    public static <T> ActionResponse<T> getResponse(MvcResult result) throws UnsupportedEncodingException {
        MockHttpServletResponse sResponse = result.getResponse();
        String content = sResponse.getContentAsString();
        return JsonUtil.json2Object(content, ActionResponse.class);
    }

    /**
     * 单元测试解析controller返回实体
     * @param result controller 请求返回体
     * @param clazz 响应实体class
     * @param <T> 响应实体
     * @return
     * @throws UnsupportedEncodingException
     */
    public static <T> T getResponseBody(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        MockHttpServletResponse sResponse = result.getResponse();
        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String body = JsonUtil.object2Json(actionResponse.getBody());
        return JsonUtil.json2Object(body, clazz);
    }

    /**
     *
     * @param result mockMVC的结果
     * @param clazz 响应实体class
     * @param <T> 响应实体
     * @return .
     * @throws UnsupportedEncodingException .
     */
    public static <T> T getMapSingleResponseBody(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        MockHttpServletResponse sResponse = result.getResponse();
        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        // 获取到items中的所有数据 items是一个数组 但是我们默认mock数组长度为1 即只有一条数据
        Object items = ((HashMap) actionResponse.getBody()).get("items");
        // 拿到这一条数据
        Object data = ((ArrayList) items).get(0);
        return JsonUtil.json2Object(JsonUtil.object2Json(data), clazz);
    }

    /**
     *
     * @param result mockMVC的结果
     * @param clazz 响应实体class
     * @param <T> 响应实体
     * @return .
     * @throws UnsupportedEncodingException .
     */
    public static <T> T getListSingleResponseBody(MvcResult result,
                                                  Class<T> clazz) throws UnsupportedEncodingException {
        MockHttpServletResponse sResponse = result.getResponse();
        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        // 获取到items中的所有数据 items是一个数组 但是我们默认mock数组长度为1 即只有一条数据
        Object items = ((List) actionResponse.getBody());
        // 拿到这一条数据
        Object data = ((ArrayList) items).get(0);
        return JsonUtil.json2Object(JsonUtil.object2Json(data), clazz);
    }

}
