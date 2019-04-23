package com.antiy.asset.manage;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author zhouye
 * @date 2019-04-03
 * 公用信息辅助管理
 */
@Component
public class CommonManager {
    private Logger logger = LogUtils.get(CommonManager.class);
    private Gson gson = new Gson();

    /**
     * 包装业务主键
     *
     * @param id 主键id
     * @return 业务对象
     */
    public BasicRequest initBasicRequest(String id) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId(id);
        return baseRequest;
    }

    /**
     * 包装主键查询pojo
     *
     * @param id 相关模块主键
     * @return 业务对象
     */
    public QueryCondition initQueryCondition(String id) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey(id);
        return queryCondition;
    }

    /**
     * 快捷构造id 列表
     *
     * @param ids id数组
     * @return 列表
     */
    public List<String> initIds(String... ids) {
        return Arrays.asList(ids);
    }

    /**
     * 快捷构造id 列表
     *
     * @param ids id数组
     * @return 列表
     */
    public List<Integer> initIds(Integer... ids) {
        return Arrays.asList(ids);
    }

    /**
     * 封装 requestbuilder
     * 请求方式: post
     * 内容为json字符串
     *
     * @param path 路径
     * @param json json 数据
     * @return post builder
     */
    public MockHttpServletRequestBuilder postAction(String path, String json) {
        MockHttpServletRequestBuilder post = post(path);
        post
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes());
        return post;
    }

    /**
     * 封装 requestbuilder
     * 请求方式: post
     * 内容为json字符串
     *
     * @param path   路径
     * @param params Object 对象数据
     * @return post builder
     */
    public MockHttpServletRequestBuilder postAction(String path, Object params) {
        return postAction(path, gson.toJson(params));
    }

    /**
     * 请求方式：get
     * 携带get请求参数
     *
     * @param path 路径
     * @param obj  表单数据 注意：如果无参数传null
     * @return get builder
     */
    public MockHttpServletRequestBuilder getAction(String path, Object obj) {
        MockHttpServletRequestBuilder get = get(path);
        initRequestBuilder(get, obj);
        return get;
    }

    /**
     * 请求方式：get
     * 无参数
     *
     * @param path 路径
     * @return get builder
     */
    public MockHttpServletRequestBuilder getAction(String path) {
        MockHttpServletRequestBuilder get = get(path);
        initRequestBuilder(get, null);
        return get;
    }

    /**
     * 请求方式：del
     * 携带del请求参数
     *
     * @param path 路径
     * @param obj  表单数据
     * @return delete builder
     */
    public MockHttpServletRequestBuilder delAction(String path, Object obj) {
        MockHttpServletRequestBuilder del = delete(path);
        initRequestBuilder(del, obj);
        return del;
    }

    /**
     * 初始化请求信息
     * 1.请求方式
     * 2.数据格式
     * 3.请求参数初始化
     *
     * @param builder 请求builder
     * @param obj     请求参数
     */
    public void initRequestBuilder(MockHttpServletRequestBuilder builder, Object obj) {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        try {
            paramsMap.setAll(BeanUtils.describe(obj));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对象转换map失败", e.getMessage());
        }
        builder
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .params(paramsMap);
    }

    /**
     * get请求封装
     * 期望1.请求返回正常响应状态，返回数据为json
     *
     * @param mvc     MockMvc
     * @param builder 请求 builder
     * @return 请求结果
     */
    public String getResult(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return mvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }


}
